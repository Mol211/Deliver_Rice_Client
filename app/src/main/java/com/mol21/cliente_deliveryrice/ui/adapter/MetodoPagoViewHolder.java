package com.mol21.cliente_deliveryrice.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ItemMetodoPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;

public class MetodoPagoViewHolder extends RecyclerView.ViewHolder {
    private final ItemMetodoPagoBinding binding;
    public MetodoPagoViewHolder(@NonNull ItemMetodoPagoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void render(MetodoPago metodo){
        switch (metodo){
            case EFECTIVO:
                binding.tvMetodoPago.setText("Efectivo");
                binding.ivMetodoPago.setImageResource(R.drawable.ic_cash);
                break;

            case TARJETA:
                binding.tvMetodoPago.setText("Tarjeta");
                binding.ivMetodoPago.setImageResource(R.drawable.baseline_payment_24);
                break;

            case BIZUM:
                binding.tvMetodoPago.setText("Bizum");
                binding.ivMetodoPago.setImageResource(R.drawable.ic_smartphone);
                break;
        }
    }
}
