package com.app.lab3trabajoinmobiliaria.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.app.lab3trabajoinmobiliaria.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements SensorEventListener {

    private LoginViewModel vm;
    private ActivityLoginBinding binding;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        setContentView(binding.getRoot());
        solicitarPermisos();

        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.llamarlogin(binding.etUsuario.getText().toString(), binding.etClave.getText().toString());
            }
        });

        binding.btnOlvideClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OlvidoClaveDialog dialog = new OlvidoClaveDialog();
                dialog.show(getSupportFragmentManager(), "OlvidoClaveDialog");
            }
        });
    }

    private void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> listaPermisos = new ArrayList<>();

            // Si estamos en Android 13 o superior
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    listaPermisos.add(Manifest.permission.READ_MEDIA_IMAGES);
                }
            } else {
                // Para Android 12 y anteriores, usa READ_EXTERNAL_STORAGE
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    listaPermisos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }

            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                listaPermisos.add(Manifest.permission.CALL_PHONE);
            }

            if (!listaPermisos.isEmpty()) {
                ActivityCompat.requestPermissions(this, listaPermisos.toArray(new String[0]), 100);
            } else {
                Toast.makeText(this, "Todos los permisos concedidos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No es necesario solicitar permisos adicionales", Toast.LENGTH_SHORT).show();
        }

        // Inicializa el SensorManager para gestionar sensores del dispositivo.
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Llama al método `HacerLlamada` del ViewModel y le pasa el evento del sensor.
        vm.HacerLlamada(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
