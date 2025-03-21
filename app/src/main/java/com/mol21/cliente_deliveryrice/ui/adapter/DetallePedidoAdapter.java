package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemDetallePedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DetalleDTO;

import java.util.List;

public class DetallePedidoAdapter extends  RecyclerView.Adapter<DetallePedidoViewHolder> {
    private final List<DetalleDTO> lista;

    public DetallePedidoAdapter(List<DetalleDTO> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public DetallePedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetallePedidoBinding binding = ItemDetallePedidoBinding.
                inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
        return new DetallePedidoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetallePedidoViewHolder holder, int position) {
        DetalleDTO detalle = lista.get(position);
        holder.render(detalle);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
