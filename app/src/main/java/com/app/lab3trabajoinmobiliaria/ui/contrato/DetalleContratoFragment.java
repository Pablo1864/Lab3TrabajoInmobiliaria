package com.app.lab3trabajoinmobiliaria.ui.contrato;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.databinding.FragmentDetalleContratoBinding;

public class DetalleContratoFragment extends Fragment {
    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;
    private int contratoId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener el contratoId desde el Bundle
        contratoId = getArguments().getInt("contratoId");


        // Configura el ViewModel
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        // Carga los datos del contrato usando el contratoId
        Log.d("DetalleContratoFragment", "Cargando contrato con contratoId: " + contratoId);
        vm.cargarContratoDetalle(contratoId);

        // Observa los cambios en el LiveData
        vm.getContrato().observe(getViewLifecycleOwner(), contrato -> {

                Log.d("DetalleContratoFragment", "Datos del contrato: " + contrato.toString());
                binding.tvCodigoContrato.setText(String.valueOf(contrato.getContratoId()));
                binding.tvFechaInicio.setText(contrato.getFechaDesde());
                binding.tvFechaFin.setText(contrato.getFechaHasta());
                binding.tvMontoAqluiler.setText(String.valueOf(contrato.getMonto()));



                // Muestra detalles del inmueble e inquilino si están disponibles
                binding.tvInmueble.setText(contrato.getInmueble().getDireccion());
                binding.tvInquilino.setText(contrato.getInquilino().getNombre());

        });

        binding.btnPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.verPagos(v);
            }
        });

        return root;
    }
}

