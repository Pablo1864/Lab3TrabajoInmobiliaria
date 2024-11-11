package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Inmueble>> mLista;
    private Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mLista = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Inmueble>> getMLista() {
        if (mLista.getValue() == null) {
            cargarInmuebles();
        }
        return mLista;
    }


    public void recargarInmuebles() {
        cargarInmuebles();
    }

    private void cargarInmuebles() {
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<ArrayList<Inmueble>> call = api.getMisInmuebles(TokenShared.obtenerToken(context));
        call.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()) {
                    mLista.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Inmueble>> call, Throwable t) {
                Log.e("InmueblesViewModel", "Error al cargar inmuebles", t);
            }
        });
    }
}

