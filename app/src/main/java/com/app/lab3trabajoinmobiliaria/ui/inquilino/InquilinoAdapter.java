package com.app.lab3trabajoinmobiliaria.ui.inquilino;

import static com.app.lab3trabajoinmobiliaria.request.ApiClient.URLBASEIMG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolderInquilino> {
    private ArrayList<Inmueble> listaDeInmueblesAlquilados;
    private LayoutInflater inflater;
    private Context context;

    public InquilinoAdapter(ArrayList<Inmueble> listaDeInmueblesAlquilados, LayoutInflater inflater, Context context) {
        this.listaDeInmueblesAlquilados = listaDeInmueblesAlquilados;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderInquilino onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta, parent, false);
        return new ViewHolderInquilino(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInquilino holder, int position) {
        Inmueble inmueble = listaDeInmueblesAlquilados.get(position);

        // Verifica si el inmueble no es nulo
        if (inmueble != null) {
            holder.tvDireccion.setText(inmueble.getDireccion());
            holder.tvPrecio.setText(String.valueOf(inmueble.getPrecio()));

            // Carga la imagen del inmueble
            if (inmueble.getImg() != null && !inmueble.getImg().isEmpty()) {
                Glide.with(context)
                        .load(URLBASEIMG + inmueble.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .override(210, 238)
                        .into(holder.ivFoto);
            } else {
                holder.ivFoto.setImageResource(R.drawable.casita);
            }

            // Configuración del click listener
            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt("idInmueble", inmueble.getIdInmueble());
                Log.d("InquilinoAdapter", "Cargando inquilino para el inmueble con ID: " + inmueble.getIdInmueble());

                // Navega al fragmento de detalles del inquilino
                Navigation.findNavController(view).navigate(R.id.detalleInquilinoFragment, bundle);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaDeInmueblesAlquilados.size();
    }

    public static class ViewHolderInquilino extends RecyclerView.ViewHolder {
        private TextView tvDireccion, tvPrecio;

        private ImageView ivFoto;

        public ViewHolderInquilino(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);

            ivFoto = itemView.findViewById(R.id.ivFoto);
        }
    }
}
