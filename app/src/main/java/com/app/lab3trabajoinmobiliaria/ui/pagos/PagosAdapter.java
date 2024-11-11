package com.app.lab3trabajoinmobiliaria.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.lab3trabajoinmobiliaria.R;
import com.app.lab3trabajoinmobiliaria.model.Pagos;

import java.util.ArrayList;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolderPagos> {
    private ArrayList<Pagos> listaDePagos;
    private LayoutInflater inflater;
    private Context context;

    public PagosAdapter(ArrayList<Pagos> listaDePagos, LayoutInflater inflater, Context context) {
        this.listaDePagos = listaDePagos;
        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPagos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tarjeta_pagos, parent, false);
        return new ViewHolderPagos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPagos holder, int position) {
        Pagos pagos = listaDePagos.get(position);

        holder.tvCodigoPago.setText(String.valueOf(pagos.getId()));
        holder.tvImportePago.setText(String.valueOf(pagos.getImporte()));
        holder.tvNroPago.setText(String.valueOf(pagos.getNroPago()));
        holder.tvDetallePago.setText(String.valueOf(pagos.getDetallePago()));

        // Usar la fecha formateada
        String fechaPagoFormateada = pagos.getFechaPagoFormateada();
        if (fechaPagoFormateada != null && !fechaPagoFormateada.isEmpty()) {
            holder.tvFechaPago.setText(fechaPagoFormateada);
        } else {
            holder.tvFechaPago.setText("Fecha no disponible");
        }
    }

    @Override
    public int getItemCount() {
        return listaDePagos.size();
    }

    public class ViewHolderPagos extends RecyclerView.ViewHolder {
        private TextView tvCodigoPago;
        private TextView tvImportePago;
        private TextView tvFechaPago;
        private TextView tvNroPago;
        private TextView tvDetallePago;

        public ViewHolderPagos(@NonNull View itemView) {
            super(itemView);


            tvCodigoPago = itemView.findViewById(R.id.tvCodigo);
            tvImportePago = itemView.findViewById(R.id.tvImporte);
            tvFechaPago = itemView.findViewById(R.id.tvFecha);
            tvNroPago = itemView.findViewById(R.id.tvNumero);
            tvDetallePago = itemView.findViewById(R.id.tvDetalleContrato);
        }
    }
}
