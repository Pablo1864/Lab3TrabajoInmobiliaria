package com.app.lab3trabajoinmobiliaria.ui.perfil;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.databinding.FragmentPerfilBinding;


public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener los datos del propietario
        vm.getDatos();

        // Observa los datos del propietario
        vm.getMPropietario().observe(getViewLifecycleOwner(), propietario -> {

                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etEmail.setText(propietario.getEmail());
                binding.etDireccion.setText(propietario.getDomicilio());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etDni.setText(String.valueOf(propietario.getDni()));

        });

        // Observa si los campos están habilitados
        vm.getCamposHabilitados().observe(getViewLifecycleOwner(), habilitados -> {
            binding.etNombre.setEnabled(habilitados);
            binding.etApellido.setEnabled(habilitados);
            binding.etEmail.setEnabled(habilitados);
            binding.etDireccion.setEnabled(habilitados);
            binding.etTelefono.setEnabled(habilitados);
            binding.etDni.setEnabled(false);
        });

        // Observa el texto del botón
        vm.getTextoBoton().observe(getViewLifecycleOwner(), texto -> {
            binding.btnEditar.setText(texto);
        });

        // Manejo del click del botón
        binding.btnEditar.setOnClickListener(v -> {
            String nombre = binding.etNombre.getText().toString();
            String apellido = binding.etApellido.getText().toString();
            String email = binding.etEmail.getText().toString();
            String dirrecion = binding.etDireccion.getText().toString();
            String telefono = binding.etTelefono.getText().toString();
            String dni = binding.etDni.getText().toString();

            vm.onBotonClick(nombre, apellido, email, dirrecion,telefono,dni);  // Pasamos los valores actuales al ViewModel
        });
        binding.btnCambiarClave.setOnClickListener(v -> {
            CambiarContraseñaDialog dialog = new CambiarContraseñaDialog();
            dialog.show(getParentFragmentManager(), "CambiarContraseñaDialog");
        });

        return root;
    }
}

