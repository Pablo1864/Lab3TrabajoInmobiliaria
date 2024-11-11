package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.lab3trabajoinmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.request.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observa los cambios en el objeto Inmueble en el ViewModel
        mViewModel.getMInmueble().observe(getViewLifecycleOwner(), inmueble -> {

                binding.tvDireccion.setText(inmueble.getDireccion());
                binding.tvPrecio.setText(String.valueOf(inmueble.getPrecio()));

                binding.tvUso.setText(String.valueOf(inmueble.getUso()));
                binding.tvAmbientes.setText(String.valueOf(inmueble.getAmbientes()));

                Glide.with(getActivity())
                        .load(ApiClient.URLBASEIMG + inmueble.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .override(210, 238)
                        .into(binding.ivImagenInmueble);

        });

        // Observa los cambios en el estado del CheckBox
        mViewModel.getEstadoCheck().observe(getViewLifecycleOwner(), isChecked -> {
            binding.cbEstado.setChecked(isChecked);
        });

        // Llama a recibeInmueble para pasar el argumento al ViewModel
        mViewModel.recibeInmueble(getArguments());

        // Escucha cambios en el CheckBox y delega la lógica al ViewModel
        binding.cbEstado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mViewModel.onEstadoCheckChanged(isChecked);
        });

        return root;
    }
}
