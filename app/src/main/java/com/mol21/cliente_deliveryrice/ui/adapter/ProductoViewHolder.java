package com.mol21.cliente_deliveryrice.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mol21.cliente_deliveryrice.databinding.ItemProductBinding;
import com.mol21.cliente_deliveryrice.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.model.Producto;

public class ProductoViewHolder extends RecyclerView.ViewHolder {
    private final ItemProductBinding binding;
    public ProductoViewHolder( ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void render(ProductoDTO item) {
        Glide.with(binding.ivProduct.getContext())
                .load(item.getImagenURL())
                .into(binding.ivProduct);
        binding.tvPrecio.setText(item.getPrecio()+"€");
        binding.tvTitulo.setText(item.getNombre());
        binding.tvDescripciN.setText(item.getDescripcion());
    }
}
