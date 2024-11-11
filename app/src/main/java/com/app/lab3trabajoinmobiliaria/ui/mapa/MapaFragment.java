package com.app.lab3trabajoinmobiliaria.ui.mapa;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.app.lab3trabajoinmobiliaria.R;
import com.google.android.gms.maps.SupportMapFragment;


public class MapaFragment extends Fragment {
    private MapaViewModel vm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(MapaViewModel.class);
        vm.getMMap().observe(getViewLifecycleOwner(), new Observer<MapaViewModel.Mapa>() {
            @Override
            public void onChanged(MapaViewModel.Mapa mapa) {
                ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapa);
//                ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapa);
            }
        });
        vm.obtenerMapa();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}