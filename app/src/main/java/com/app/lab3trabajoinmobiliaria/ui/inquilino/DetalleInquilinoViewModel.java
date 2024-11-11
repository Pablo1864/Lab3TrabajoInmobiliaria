package com.app.lab3trabajoinmobiliaria.ui.inquilino;

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
import com.app.lab3trabajoinmobiliaria.model.Inquilino;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DetalleInquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilino;
    private Context context;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        inquilino = new MutableLiveData<>();
    }

    public LiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    // Método para cargar el inquilino usando el idInmueble
    public void cargarInquilinoPorInmueble(int idInmueble) {
        // Log para verificar el idInmueble que se está enviando
        Log.d("DetalleInquilinoVM", "Cargando inquilino para el inmueble con ID: " + idInmueble);

        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Inquilino> call = api.getInquilino(TokenShared.obtenerToken(context), idInmueble);

        // Log para verificar que la llamada a la API se hizo correctamente
        Log.d("DetalleInquilinoVM", "Realizando la llamada a la API para obtener inquilino...");

        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                // Log para la respuesta del servidor
                Log.d("DetalleInquilinoVM", "Respuesta del servidor: " + response.code() + " - " + response.message());

                if (response.isSuccessful()) {
                    Log.d("DetalleInquilinoVM", "Respuesta exitosa: " + response.body());
                    inquilino.setValue(response.body());
                    Toast.makeText(context, "Inquilino cargado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Log para la respuesta de error del servidor
                    Log.e("DetalleInquilinoVM", "Error al cargar el inquilino: " + response.message());
                    Log.e("DetalleInquilinoVM", "Código de estado: " + response.code());
                    try {
                        Log.e("DetalleInquilinoVM", "Respuesta de error: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("DetalleInquilinoVM", "Error al leer el cuerpo del error", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                // Log para el fallo de la conexión
                Log.e("DetalleInquilinoVM", "Error al cargar los datos del inquilino", t);
            }
        });
    }
}





