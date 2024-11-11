package com.app.lab3trabajoinmobiliaria.ui.pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentContratoBinding;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentPagosBinding;
import com.app.lab3trabajoinmobiliaria.ui.contrato.ContratoViewModel;

public class PagosFragment extends Fragment {

    private PagosViewModel vm;
    private FragmentPagosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        vm = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        vm.getListaPagos().observe(getViewLifecycleOwner(), pagos -> {
                PagosAdapter adapter = new PagosAdapter(pagos, getLayoutInflater(), getContext());
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
                binding.listaPagos.setAdapter(adapter);
                binding.listaPagos.setLayoutManager(grid);

        });
        vm.cargarPagos(getArguments());

        return root;
    }
}






