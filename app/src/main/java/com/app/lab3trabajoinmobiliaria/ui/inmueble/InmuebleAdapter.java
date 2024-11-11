package com.app.lab3trabajoinmobiliaria.ui.inmueble;

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

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {
    private ArrayList<Inmueble> listaDeInmuebles;
    private LayoutInflater inflater;

    private Context context;

    public InmuebleAdapter(ArrayList<Inmueble> listaDeInmuebles, LayoutInflater inflater, Context context) {
        this.listaDeInmuebles = listaDeInmuebles;
        this.inflater = inflater;

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta, parent, false);
        return new ViewHolderInmueble(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmueble = listaDeInmuebles.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvPrecio.setText(String.valueOf(inmueble.getPrecio()));

        // Agregar log para verificar el ID del inmueble
        Log.d("InmuebleAdapter", "Inmueble ID: " + inmueble.getIdInmueble());

        if (inmueble.getImg() != null && !inmueble.getImg().isEmpty()) {
            try {
                Glide.with(context)
                        .load(URLBASEIMG + listaDeInmuebles.get(position).getImg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .override(210, 238)
                        .into(holder.ivFoto);
                Log.d("URL", URLBASEIMG + listaDeInmuebles.get(position).getImg());
            } catch (NumberFormatException e) {
                Glide.with(context).load(inmueble.getImg()).into(holder.ivFoto);
            }
        } else {
            holder.ivFoto.setImageResource(R.drawable.casita);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log para verificar el ID antes de enviar el Bundle
                Log.d("InmuebleAdapter", "Inmueble seleccionado para detalles, ID: " + inmueble.getIdInmueble());

                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);

                // Navega al fragmento de detalles
                Navigation.findNavController(view).navigate(R.id.detalleInmuebleFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeInmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder {
        private TextView tvDireccion, tvPrecio;
        private ImageView ivFoto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);

            ivFoto = itemView.findViewById(R.id.ivFoto);
        }
    }
}

