package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemMetodoPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.ui.listener.OnItemClickListener;

import java.util.List;

public class MetodoPagoAdapter extends RecyclerView.Adapter<MetodoPagoViewHolder> {
    private final List<MetodoPago> listaMetodos;
    private OnItemClickListener<MetodoPago> clickListener;
    public interface OnMetodoClickListener{
        void onMetodoClick(MetodoPago metodo);
    }

    public MetodoPagoAdapter(List<MetodoPago>listaMetodos, OnItemClickListener<MetodoPago> listener) {
        this.listaMetodos = listaMetodos;
        this.clickListener = listener;
    }


    @NonNull
    @Override
    public MetodoPagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMetodoPagoBinding binding = ItemMetodoPagoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new MetodoPagoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MetodoPagoViewHolder holder, int position) {
        MetodoPago metodo = listaMetodos.get(position);
        holder.render(metodo);
        holder.itemView.setOnClickListener(v->{
            if(clickListener != null) {
                clickListener.onItemClick(metodo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaMetodos.size();
    }


}
