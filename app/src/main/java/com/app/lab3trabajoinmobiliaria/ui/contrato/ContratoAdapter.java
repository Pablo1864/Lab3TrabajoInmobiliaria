package com.app.lab3trabajoinmobiliaria.ui.contrato;

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
import com.app.lab3trabajoinmobiliaria.model.Contrato;
import com.app.lab3trabajoinmobiliaria.model.Inmueble;
import com.app.lab3trabajoinmobiliaria.ui.inquilino.InquilinoAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderContrato> {
    private ArrayList<Contrato> listaDeContratos;
    private LayoutInflater inflater;
    private Context context;

    public ContratoAdapter(ArrayList<Contrato> listaDeContratos, LayoutInflater inflater, Context context) {
        this.listaDeContratos = listaDeContratos;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta, parent, false);
        return new ViewHolderContrato(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContrato holder, int position) {
        Contrato contrato = listaDeContratos.get(position);

        // Configura los datos en la tarjeta usando el contrato y su inmueble
        if (contrato.getInmueble() != null) {
            holder.tvDireccion.setText(contrato.getInmueble().getDireccion());
            holder.tvPrecio.setText(String.valueOf(contrato.getMonto()));

            // Configura la imagen del inmueble
            if (contrato.getInmueble().getImg() != null && !contrato.getInmueble().getImg().isEmpty()) {
                Glide.with(context)
                        .load(URLBASEIMG + contrato.getInmueble().getImg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .override(210, 238)
                        .into(holder.ivFoto);
            } else {
                holder.ivFoto.setImageResource(R.drawable.casita);
            }
        }

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contratoId", contrato.getContratoId());
            // Navega al fragmento de detalles del contrato
            Navigation.findNavController(view).navigate(R.id.detalleContratoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaDeContratos.size();
    }

    public class ViewHolderContrato extends RecyclerView.ViewHolder {
        private TextView tvDireccion, tvPrecio;
        private ImageView ivFoto;

        public ViewHolderContrato(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivFoto = itemView.findViewById(R.id.ivFoto);
        }
    }
}


