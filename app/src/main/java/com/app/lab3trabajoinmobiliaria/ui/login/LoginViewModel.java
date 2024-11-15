package com.app.lab3trabajoinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.app.lab3trabajoinmobiliaria.MainActivity;
import com.app.lab3trabajoinmobiliaria.model.Login;
import com.app.lab3trabajoinmobiliaria.model.LoginResponse;
import com.app.lab3trabajoinmobiliaria.model.Propietario;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.app.lab3trabajoinmobiliaria.request.TokenShared;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private Context context;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 1000;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void llamarlogin(String email, String password) {
        Login login = new Login(email, password);
        ApiClient.InmobiliariaService api = ApiClient.getApi();
        Call<LoginResponse> call = api.login(login);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        TokenShared.limpiarDatos(getApplication());
                        String token = loginResponse.getToken();
                        Log.d("salida", "Token: " + token);
                        TokenShared.guardar(context, token);
                        Propietario propietario = loginResponse.getPropietario();
                        TokenShared.guardarPropietario(context, propietario);
                        Log.d("salida", "Propietario: " + propietario.toString());

                        // Redirigir a MainActivity
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Agrega esta línea
                        getApplication().startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "Contraseña/Correo incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("salida", t.getMessage());
                Toast.makeText(context, "Servidor desconectado", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void HacerLlamada(SensorEvent sensor) {
        // Verifica que el tipo de sensor sea un acelerómetro antes de proceder.
        if (sensor.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // Obtiene los valores de aceleración en los ejes X, Y, y Z.
            float x = sensor.values[0];
            float y = sensor.values[1];
            float z = sensor.values[2];

            // Guarda el tiempo actual en milisegundos.
            long curTime = System.currentTimeMillis();

            // Comprueba si han pasado al menos 100 ms desde la última actualización
            // para evitar ejecutar el código demasiado rápido.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate); // Calcula el tiempo transcurrido.
                lastUpdate = curTime; // Actualiza el último tiempo de verificación.

                // Calcula la velocidad de cambio de posición dividiendo la diferencia en los ejes entre el tiempo transcurrido.
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                // Verifica si la velocidad excede el umbral de agitación.
                if (speed > SHAKE_THRESHOLD) {
                    // Número de teléfono a llamar cuando se detecta la agitación.
                    String TelefonoI = "2657302496";

                    // Crea un Intent para realizar una llamada.
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    // Define el número de teléfono en el Intent.
                    intent.setData(Uri.parse("tel:" + TelefonoI));

                    // Añade una bandera para iniciar la actividad de la llamada en una nueva tarea.
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Inicia la llamada a través del contexto.
                    context.startActivity(intent);
                }

                // Actualiza los valores anteriores de aceleración para el próximo cálculo.
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
}