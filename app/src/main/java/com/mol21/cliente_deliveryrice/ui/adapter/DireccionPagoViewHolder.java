package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;

public class DireccionPagoViewHolder extends RecyclerView.ViewHolder {
    private final ItemDireccionBinding binding;
    public DireccionPagoViewHolder(@NonNull ItemDireccionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void render(DireccionDTO direccion){
        binding.calle.setText(direccion.getCalle()+", "+direccion.getCodPostal());
        binding.numero.setText(direccion.getNumero());
    }
}
