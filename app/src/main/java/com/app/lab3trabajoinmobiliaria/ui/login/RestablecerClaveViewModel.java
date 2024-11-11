package com.app.lab3trabajoinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.lab3trabajoinmobiliaria.model.RecuperarContrasena;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerClaveViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private String token;
    private Context context;

    public RestablecerClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void restablecerContrasena(String nuevaContrasena, String confirmarContrasena) {
        if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            mensaje.setValue("Por favor, complete todos los campos.");
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            mensaje.setValue("Las contraseñas no coinciden.");
            return;
        }


        RecuperarContrasena request = new RecuperarContrasena(token, nuevaContrasena, confirmarContrasena);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<ResponseBody> call = api.restablecerContrasena(request);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Contraseña restablecida con éxito.");
                } else {
                    mensaje.setValue("Error al restablecer la contraseña.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mensaje.setValue("Error: " + t.getMessage());
            }
        });
    }
}

