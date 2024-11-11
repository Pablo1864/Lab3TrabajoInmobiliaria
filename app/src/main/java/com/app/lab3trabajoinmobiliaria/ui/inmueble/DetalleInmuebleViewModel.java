package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import static java.security.AccessController.getContext;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<Boolean> estadoCheck;
    private Context context;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        mInmueble = new MutableLiveData<>();
        estadoCheck = new MutableLiveData<>();
        context = application.getApplicationContext();
        Log.d("DetalleInmuebleViewModel", "ViewModel initialized");
    }

    public LiveData<Inmueble> getMInmueble() {
        return mInmueble;
    }

    public LiveData<Boolean> getEstadoCheck() {
        return estadoCheck;
    }

    public void recibeInmueble(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) {
                mInmueble.setValue(inmueble);
                estadoCheck.setValue("disponible".equalsIgnoreCase(inmueble.getEstado()));
                Log.d("DetalleInmuebleViewModel", "Inmueble recibido: " + inmueble.toString());
            } else {
                Log.e("DetalleInmuebleViewModel", "El objeto inmueble es nulo");
            }
        } else {
            Log.e("DetalleInmuebleViewModel", "El bundle recibido es nulo");
        }
    }


    public void actualizarEstadoInmueble(int idInmueble, boolean isChecked) {
        String nuevoEstado = isChecked ? "disponible" : "no disponible";
        ApiClient.InmobiliariaService api = ApiClient.getApi();

        // Agrega log para confirmar idInmueble y estado antes de la llamada
        Log.d("DetalleInmuebleViewModel", "Actualizando estado para idInmueble: " + idInmueble + ", nuevo estado: " + nuevoEstado);

        // Agrega log para confirmar el token utilizado
        String token = TokenShared.obtenerToken(context);
        Log.d("DetalleInmuebleViewModel", "Token utilizado: " + token);

        Call<ResponseBody> call = api.actualizarEstado(token, idInmueble, nuevoEstado);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Estado actualizado correctamente", Toast.LENGTH_SHORT).show();
                    if (mInmueble.getValue() != null) {
                        mInmueble.getValue().setEstado(nuevoEstado);
                        Log.d("DetalleInmuebleViewModel", "Estado del inmueble actualizado a: " + nuevoEstado);
                    }
                } else {
                    // Agrega log para identificar el código de error recibido
                    Log.e("DetalleInmuebleViewModel", "Error al actualizar el estado: " + response.code());
                    Toast.makeText(context, "Error al actualizar el estado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("DetalleInmuebleViewModel", "Fallo en la conexión al intentar actualizar el estado", t);
                Toast.makeText(context, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onEstadoCheckChanged(boolean isChecked) {
        Inmueble inmueble = mInmueble.getValue();
        if (inmueble != null) {
            boolean estadoActual = "disponible".equalsIgnoreCase(inmueble.getEstado());
            Log.d("DetalleInmuebleViewModel", "Estado actual: " + estadoActual + ", nuevo estado: " + isChecked);
            if (estadoActual != isChecked) {
                actualizarEstadoInmueble(inmueble.getIdInmueble(), isChecked);
            } else {
                Log.d("DetalleInmuebleViewModel", "No se requiere actualización, el estado es el mismo");
            }
        } else {
            Log.e("DetalleInmuebleViewModel", "El inmueble es nulo en onEstadoCheckChanged");
        }
    }
}




