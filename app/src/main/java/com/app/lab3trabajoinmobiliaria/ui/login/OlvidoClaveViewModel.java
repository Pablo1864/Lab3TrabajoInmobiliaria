package com.app.lab3trabajoinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.app.lab3trabajoinmobiliaria.request.ApiClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlvidoClaveViewModel extends AndroidViewModel {
    private Context context ;
    public OlvidoClaveViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public void recuperarClave(String email) {
        if (email.isEmpty()) {
            Toast.makeText(context, "Debe ingresar un email", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<ResponseBody> call = api.enviarCorreoRecuperacion(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Instrucciones enviadas al correo " + email, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Error al enviar correo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
