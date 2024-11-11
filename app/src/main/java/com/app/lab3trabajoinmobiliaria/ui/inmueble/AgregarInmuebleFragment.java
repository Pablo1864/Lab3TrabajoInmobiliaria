package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentInmuebleBinding;

import java.util.Arrays;
import java.util.List;

public class AgregarInmuebleFragment extends Fragment {

    private AgregarInmuebleViewModel vm;
    private FragmentAgregarInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        configurarLanzadorGaleria();


        List<String> tiposInmueble = Arrays.asList("Departamento", "Casa", "Depósito", "Local");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tiposInmueble);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipoUso.setAdapter(adapter);



        vm.getUriMutable().observe(getViewLifecycleOwner(), uri -> binding.ImagenInmueble.setImageURI(uri));

        vm.getMResult().observe(getViewLifecycleOwner(), result -> {


                NavController navController = NavHostFragment.findNavController(AgregarInmuebleFragment.this);
                navController.navigate(R.id.nav_home);


        });

        binding.btnAgregarInmueble.setOnClickListener(view -> {
            String direccion = binding.etDireccion.getText().toString();
            String ambientes = binding.etAmbientes.getText().toString();
            int tipoIndice = binding.spinnerTipoUso.getSelectedItemPosition();
            double precio = Double.parseDouble(binding.etPrecio.getText().toString());

            vm.agregarInmueble(direccion, ambientes, tipoIndice, precio);
        });

        binding.btnSubirFoto.setOnClickListener(v -> checkStoragePermission());

        return binding.getRoot();
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            abrirGaleria();
        }
    }

    private void configurarLanzadorGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            vm.recibirFoto(result);
        });
    }

    private void abrirGaleria() {
        arl.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                Toast.makeText(requireContext(), "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}










