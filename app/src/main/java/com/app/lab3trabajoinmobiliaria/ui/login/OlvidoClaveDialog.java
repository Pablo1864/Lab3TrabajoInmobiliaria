package com.app.lab3trabajoinmobiliaria.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.databinding.DialogOlvidoClaveBinding;


public class OlvidoClaveDialog extends DialogFragment {
    private DialogOlvidoClaveBinding binding;
    private OlvidoClaveViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DialogOlvidoClaveBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(OlvidoClaveViewModel.class);

        binding.btnConfirmarOlvidoClave.setOnClickListener(v -> {
            String email = binding.etCorreoOlvidoClave.getText().toString();

            vm.recuperarClave(email);
        });

        return binding.getRoot();
    }
}
