package com.app.lab3trabajoinmobiliaria.ui.inquilino;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.app.lab3trabajoinmobiliaria.databinding.FragmentDetalleInquilinoBinding;


public class DetalleInquilinoFragment extends Fragment {
    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;
    private int idInmueble;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtén el idInmueble desde el Bundle
        idInmueble = getArguments().getInt("idInmueble");
        Log.d("DetalleInquilinoFragment", "idInmueble recibido: " + idInmueble);


        // Configura el ViewModel
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        // Carga los datos del inquilino usando el id del inmueble
        Log.d("DetalleInquilinoFragment", "Cargando inquilino con idInmueble: " + idInmueble);
        vm.cargarInquilinoPorInmueble(idInmueble);

        // Observa los cambios en el LiveData
        vm.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {

                Log.d("DetalleInquilinoFragment", "Datos del inquilino: " + inquilino.toString());
                binding.tvDni.setText(String.valueOf(inquilino.getDni()));
                binding.tvNombre.setText(String.valueOf(inquilino.getNombre()));
                binding.tvApellido.setText(inquilino.getApellido());
                binding.tvDireccion.setText(inquilino.getDomicilio());
                binding.tvEmail.setText(inquilino.getEmail());

                binding.tvTelefono.setText(inquilino.getTelefono());

        });

        return root;
    }
}


