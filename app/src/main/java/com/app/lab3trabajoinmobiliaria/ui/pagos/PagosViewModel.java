package com.app.lab3trabajoinmobiliaria.ui.pagos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.model.Contrato;
import com.app.lab3trabajoinmobiliaria.model.Pagos;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<Pagos>> mListaPagos = new MutableLiveData<>();
    private MutableLiveData<Boolean> hayError = new MutableLiveData<>(false);

    public PagosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Pagos>> getListaPagos() {
        return mListaPagos;
    }

    public LiveData<Boolean> getHayError() {
        return hayError;
    }

    public void cargarPagos(Bundle bundle) {
        if (bundle != null) {
            int idContrato = bundle.getInt("idContrato", -1);
            Log.d("PagosViewModel", "ID de contrato recibido: " + idContrato);

            if (idContrato != -1) {
                ApiClient.InmobiliariaService api = ApiClient.getApi();
                Call<ArrayList<Pagos>> call = api.obtenerPagos(TokenShared.obtenerToken(context), idContrato);

                Log.d("PagosViewModel", "Llamando a la API para obtener pagos del contrato con id: " + idContrato);

                call.enqueue(new Callback<ArrayList<Pagos>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Pagos>> call, Response<ArrayList<Pagos>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            mListaPagos.setValue(response.body());
                            // No hay error, se encontraron pagos
                        } else {
                            Log.e("PagosViewModel", "Error en la respuesta de la API o lista de pagos vacía: " + response.message());
                            Toast.makeText(context, "No hay pagos realizados", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Pagos>> call, Throwable t) {
                        Log.e("PagosViewModel", "Error de red al cargar los datos de pagos", t);
                        hayError.setValue(true);
                    }
                });
            }
        }
    }
}


