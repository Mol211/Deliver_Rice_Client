package com.mol21.cliente_deliveryrice.ui.adapter;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mol21.cliente_deliveryrice.databinding.ItemDetallePedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DetalleDTO;

public class DetallePedidoViewHolder extends RecyclerView.ViewHolder {
    private final ItemDetallePedidoBinding binding;
    public DetallePedidoViewHolder(@NonNull ItemDetallePedidoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void render(DetalleDTO detalle){
        Log.d("DESCRIPCION", "Descripcion: "+detalle.getDescripcionProducto());
        Log.d("IMAGEN", "imagen: "+detalle.getImagen());
        Log.d("NOMBRE", "NOMBRE: "+detalle.getNombreProducto());
        binding.tvCantDetalle.setText("x"+detalle.getCantidad());
        binding.tvDescDetalle.setText(detalle.getDescripcionProducto());
        binding.tvNombreDetalle.setText(detalle.getNombreProducto());
        binding.tvSubtotalDetalle.setText(detalle.getSubTotal()+"€");

        Glide.with(binding.ivDetalle.getContext())
                .load(detalle.getImagen())
                .centerCrop()
                .into(binding.ivDetalle);
    }
}
