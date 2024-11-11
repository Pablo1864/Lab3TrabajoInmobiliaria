package com.app.lab3trabajoinmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.model.Propietario;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Propietario> MPropietario;
    private MutableLiveData<Boolean> camposHabilitados;
    private MutableLiveData<String> textoBoton;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        MPropietario = new MutableLiveData<>();
        camposHabilitados = new MutableLiveData<>(false);  // Campos deshabilitados por defecto
        textoBoton = new MutableLiveData<>("Editar perfil"); // Botón con texto inicial
        context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        return MPropietario;
    }

    public LiveData<Boolean> getCamposHabilitados() {
        return camposHabilitados;
    }

    public LiveData<String> getTextoBoton() {
        return textoBoton;
    }

    public void getDatos() {
        Propietario propietario = TokenShared.leerPropietario(context);

        if (propietario != null) {
            MPropietario.setValue(propietario);
        } else {
            Toast.makeText(context, "No se pudo recupear el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para actualizar el propietario en SharedPreferences
    public void onBotonClick(String nombre, String apellido, String email, String direccion, String telefono, String dni) {
        Boolean habilitado = camposHabilitados.getValue();
        ApiClient.InmobiliariaService api = ApiClient.getApi();


        if (habilitado != null && habilitado) {

            Propietario propietarioActual = MPropietario.getValue(); // Guardar los datos (cuando el botón muestra "Guardar")
            if (propietarioActual != null) {
                // Actualizamos los datos del propietario localmente
                propietarioActual.setNombre(nombre);
                propietarioActual.setApellido(apellido);
                propietarioActual.setEmail(email);
                propietarioActual.setDomicilio(direccion);
                propietarioActual.setTelefono(telefono);
                propietarioActual.setDni(dni);


                Call<Propietario> call = api.updatePropietario(TokenShared.obtenerToken(context), propietarioActual);
                call.enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                        if (response.isSuccessful()) {

                            TokenShared.guardarPropietario(context, propietarioActual);
                            MPropietario.setValue(response.body());


                            Toast.makeText(context, "Propietario actualizado con éxito", Toast.LENGTH_SHORT).show();

                            // Cambia el texto del boton y deshabilitar los campos
                            textoBoton.setValue("Editar perfil");
                            camposHabilitados.setValue(false);
                            getDatos();
                        } else {
                            // Manejo del error en la respuesta
                            Toast.makeText(context, "Error al actualizar el propietario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        // Error de red o fallo en la solicitud
                        Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            // Habilitar los campos (cuando el botón muestra "Editar perfil")
            textoBoton.setValue("Guardar");
            camposHabilitados.setValue(true);
        }
    }
}

