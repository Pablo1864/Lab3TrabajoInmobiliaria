package com.app.lab3trabajoinmobiliaria.ui.salir;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentSalirBinding;

public class SalirFragment extends Fragment {
    private SalirViewModel mViewModel;
    private FragmentSalirBinding binding;

    public static SalirFragment newInstance() {
        return new SalirFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(SalirViewModel.class);
        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.Salir(getActivity());



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SalirViewModel.class);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}