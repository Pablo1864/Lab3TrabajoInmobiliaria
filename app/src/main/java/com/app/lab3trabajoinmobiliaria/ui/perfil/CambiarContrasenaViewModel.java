package com.app.lab3trabajoinmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.lab3trabajoinmobiliaria.model.CambiarClave;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarContrasenaViewModel extends AndroidViewModel {
    Context context = getApplication().getApplicationContext();



    public CambiarContrasenaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }



    public void cambiarContraseña(String claveActual, String claveNueva) {
        // Validar que las contraseñas no estén vacías
        if (claveActual.isEmpty() || claveNueva.isEmpty()) {
            Toast.makeText(getApplication(), "complete todos los campos", Toast.LENGTH_SHORT).show();

        }



        // Crear el servicio de Retrofit
        ApiClient.InmobiliariaService apiService = ApiClient.getApi();

        // Crear el objeto con la información de la solicitud
        CambiarClave cambiarContrasena = new CambiarClave(claveActual, claveNueva);

        // Hacer la llamada a la API
        Call<ResponseBody> call = apiService.cambiarClave(TokenShared.obtenerToken(context), cambiarContrasena);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "la contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


