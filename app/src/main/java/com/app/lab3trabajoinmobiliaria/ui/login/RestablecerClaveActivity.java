package com.app.lab3trabajoinmobiliaria.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.ResetPasswordActivityBinding;

public class RestablecerClaveActivity extends AppCompatActivity {
    private RestablecerClaveViewModel vm;
    private ResetPasswordActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar el binding
        binding = ResetPasswordActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializar el ViewModel
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RestablecerClaveViewModel.class);;

        // Obtener el token del enlace y establecerlo en el ViewModel
        Intent intent = getIntent();
        Uri data = intent.getData();

            vm.setToken(data.getQueryParameter("token"));


        // Observa el mensaje para mostrar feedback al usuario
        vm.getMensaje().observe(this, mensaje -> {

                ;
                finish(); // Cierra la actividad tras éxito

        });

        // Manejar clic en el botón de restablecer contraseña usando View Binding
        binding.btnRestablecer.setOnClickListener(v -> {
            String nuevaContrasena = binding.etNuevaContrasena.getText().toString();
            String confirmarContrasena = binding.etConfirmarContrasena.getText().toString();
            vm.restablecerContrasena(nuevaContrasena, confirmarContrasena);
        });
    }
}



