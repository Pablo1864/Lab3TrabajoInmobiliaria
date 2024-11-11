package com.app.lab3trabajoinmobiliaria.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lab3trabajoinmobiliaria.databinding.FragmentContratoBinding;


public class ContratoFragment extends Fragment {
    private ContratoViewModel mViewModel;
    private FragmentContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observa la lista de contratos y actualiza la UI
        mViewModel.getListaAlquilados().observe(getViewLifecycleOwner(), contratos -> {
            ContratoAdapter adapter = new ContratoAdapter(contratos, getLayoutInflater(), getContext());
            GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
            binding.listaContratos.setAdapter(adapter);
            binding.listaContratos.setLayoutManager(grid);
        });




        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Llama a cargar los inmuebles alquilados cada vez que el fragmento se hace visible
        mViewModel.recargarInmueblesAlquilados();
    }
}

