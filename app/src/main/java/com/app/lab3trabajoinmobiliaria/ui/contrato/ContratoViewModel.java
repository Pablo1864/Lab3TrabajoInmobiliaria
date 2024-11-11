package com.app.lab3trabajoinmobiliaria.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.model.Contrato;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<Contrato>> mListaAlquilados = new MutableLiveData<>();

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Contrato>> getListaAlquilados() {
        if (mListaAlquilados.getValue() == null) {
            cargarInmueblesAlquilados();
        }
        return mListaAlquilados;
    }

    public void recargarInmueblesAlquilados() {
        cargarInmueblesAlquilados();
    }

    private void cargarInmueblesAlquilados() {
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<ArrayList<Contrato>> call = api.obtenerInmueblesAlquilados(TokenShared.obtenerToken(context));
        call.enqueue(new Callback<ArrayList<Contrato>>() {
            @Override
            public void onResponse(Call<ArrayList<Contrato>> call, Response<ArrayList<Contrato>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mListaAlquilados.setValue(response.body());
                } else {
                    Log.e("ContratoViewModel", "Error al cargar contratos");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Contrato>> call, Throwable t) {
                Log.e("ContratoViewModel", "Error de red", t);
            }
        });
    }
}

