package com.app.lab3trabajoinmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.DialogCambiarContrasenaBinding;

public class CambiarContraseñaDialog extends DialogFragment {
    private DialogCambiarContrasenaBinding binding;
    private CambiarContrasenaViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DialogCambiarContrasenaBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(CambiarContrasenaViewModel.class);

        binding.btnConfirmarClave.setOnClickListener(v -> {
            String claveActual = binding.etClaveActual.getText().toString();
            String claveNueva = binding.etClaveNueva.getText().toString();


            vm.cambiarContraseña(claveActual, claveNueva);
        });

        return binding.getRoot();
    }
}


