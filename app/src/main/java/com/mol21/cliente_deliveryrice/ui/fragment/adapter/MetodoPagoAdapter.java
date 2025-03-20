package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ItemItemPagoBinding;
import com.mol21.cliente_deliveryrice.databinding.ItemMetodoPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;

import java.util.List;

public class MetodoPagoAdapter extends RecyclerView.Adapter<MetodoPagoViewHolder> {
    private final List<MetodoPago> listaMetodos;
    private OnMetodoClickListener clickListener;
    public interface OnMetodoClickListener{
        void onMetodoClick(MetodoPago metodo);
    }

    public MetodoPagoAdapter(List<MetodoPago>listaMetodos, OnMetodoClickListener listener) {
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
                clickListener.onMetodoClick(metodo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaMetodos.size();
    }


}
