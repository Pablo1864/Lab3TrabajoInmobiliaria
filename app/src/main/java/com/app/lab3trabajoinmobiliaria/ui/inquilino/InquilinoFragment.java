package com.app.lab3trabajoinmobiliaria.ui.inquilino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;



import com.app.lab3trabajoinmobiliaria.databinding.FragmentInquilinoBinding;

public class InquilinoFragment extends Fragment {
    private InquilinoViewModel mViewModel;
    private FragmentInquilinoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Observa la lista de inmuebles alquilados para actualizarlos en la UI
        mViewModel.getListaAlquilados().observe(getViewLifecycleOwner(), inmuebles -> {
            InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, getLayoutInflater(), getContext());
            GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
            binding.listaInmueblesAlquilados.setAdapter(adapter);
            binding.listaInmueblesAlquilados.setLayoutManager(grid);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Llama a recargarInmueblesAlquilados() cada vez que el fragmento vuelve a ser visible
        mViewModel.recargarInmueblesAlquilados();
    }
}
