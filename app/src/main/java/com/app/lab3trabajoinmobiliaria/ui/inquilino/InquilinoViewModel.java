package com.app.lab3trabajoinmobiliaria.ui.inquilino;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Inmueble>> mListaAlquilados;
    private Context context;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mListaAlquilados = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Inmueble>> getListaAlquilados() {
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
        Call<ArrayList<Inmueble>> call = api.getInmueblesAlquilados(TokenShared.obtenerToken(context));
        call.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Inmueble> inmuebles = response.body();
                    if (inmuebles != null) {
                        for (Inmueble inmueble : inmuebles) {
                            Log.d("InmuebleViewModel", "Inmueble ID: " + inmueble.getIdInmueble());
                        }
                        mListaAlquilados.setValue(inmuebles);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Inmueble>> call, Throwable t) {
                Log.e("InmuebleViewModel", "Error al cargar inmuebles alquilados", t);
            }
        });
    }
}
