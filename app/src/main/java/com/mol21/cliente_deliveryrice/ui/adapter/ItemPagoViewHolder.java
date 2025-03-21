package com.mol21.cliente_deliveryrice.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemItemPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;

import java.util.List;

public class ItemPagoViewHolder extends RecyclerView.ViewHolder {
    private final ItemItemPagoBinding binding;
    public ItemPagoViewHolder(@NonNull ItemItemPagoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void render(ItemDTO item) {
        binding.tvCantItem.setText("x"+item.getCantidad());
        binding.tvNombreItem.setText(item.getNombreProducto());
        binding.tvPrecio.setText(item.getSubTotal()+"€");
    }
}
