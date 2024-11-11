package com.app.lab3trabajoinmobiliaria.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.databinding.FragmentInmuebleBinding;

public class InmuebleFragment extends Fragment {
    private InmuebleViewModel mViewModel;
    private FragmentInmuebleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configura el botón para navegar a AgregarInmuebleFragment
        binding.btnAgregar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.agregarInmuebleFragment);
        });

        // Observa la lista de inmuebles para actualizarlos en la UI
        mViewModel.getMLista().observe(getViewLifecycleOwner(), inmuebles -> {
            InmuebleAdapter adapter = new InmuebleAdapter(inmuebles,getLayoutInflater(),getContext());
            GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
            binding.listaInmuebles.setAdapter(adapter);
            binding.listaInmuebles.setLayoutManager(grid);
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Llama a recargarInmuebles() cada vez que el fragmento vuelve a ser visible
        mViewModel.recargarInmuebles();
    }
}


