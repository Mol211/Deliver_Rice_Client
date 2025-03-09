package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemProductBinding;
import com.mol21.cliente_deliveryrice.model.DTO.ProductoDTO;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoViewHolder> {

    List<ProductoDTO> listaProductos;

    public ProductoAdapter(List<ProductoDTO> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ProductoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoDTO item = listaProductos.get(position);
        holder.render(item);
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }
    public void updateList(List<ProductoDTO> nuevaLista) {
        listaProductos.clear();
        listaProductos.addAll(nuevaLista);
        notifyDataSetChanged();
    }
}
