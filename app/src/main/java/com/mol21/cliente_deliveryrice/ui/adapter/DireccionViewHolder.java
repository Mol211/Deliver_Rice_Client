package com.mol21.cliente_deliveryrice.ui.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemDireccionDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.ui.listener.OnDireccionClickListener;

public class DireccionViewHolder extends RecyclerView.ViewHolder {
    ItemDireccionDireccionBinding binding;

    public DireccionViewHolder(@NonNull ItemDireccionDireccionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void render(DireccionDTO d, OnDireccionClickListener listener) {

        binding.tvCalle.setText(d.getCalle() + ", " + d.getNumero());

        binding.tvCodPostal.setText(d.getCodPostal());

        binding.tvCiudad.setText(d.getCiudad());

        if (d.isEsPrincipal()){
            binding.tvDireccPrinc.setVisibility(VISIBLE);
            binding.btnPrinc.setEnabled(false);
        }
        else {
            binding.tvDireccPrinc.setVisibility(GONE);
            binding.btnPrinc.setEnabled(true);
        }
        binding.btnEditar.setOnClickListener(v->
                listener.onEditarClick(d));
        binding.btnEliminar.setOnClickListener(v->
                listener.onEliminarClick(d));
        binding.btnPrinc.setOnClickListener(v->
                listener.onMarcarComoPrincipalClick(d));
    }
}
