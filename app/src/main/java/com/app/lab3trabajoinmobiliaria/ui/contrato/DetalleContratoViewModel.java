package com.app.lab3trabajoinmobiliaria.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.model.Contrato;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> Mcontrato;
    private Context context;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        Mcontrato = new MutableLiveData<>();
    }

    public LiveData<Contrato> getContrato() {
        return Mcontrato;
    }


    public void cargarContratoDetalle(int contratoId) {
        Log.d("DetalleContratoVM", "Cargando contrato para el contratoId: " + contratoId);

        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<Contrato> call = api.obtenerContratoDetalle(TokenShared.obtenerToken(context), contratoId);

        Log.d("DetalleContratoVM", "Realizando la llamada a la API para obtener contrato detalle...");

        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {


                if (response.isSuccessful()) {
                    Log.d("DetalleContratoVM", "Respuesta exitosa: " + response.body());
                    Mcontrato.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("DetalleContratoVM", "Error al cargar los datos del contrato", t);
            }
        });
    }


    public void verPagos(View view) {
        Bundle bundle = new Bundle();
        Contrato contrato = Mcontrato.getValue();
        if (contrato != null) {
            bundle.putInt("idContrato", contrato.getContratoId());
            Navigation.findNavController(view).navigate(R.id.pagosFragment, bundle);
        }
    }

}

