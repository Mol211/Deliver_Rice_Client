package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemDireccionDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.ui.listener.OnDireccionClickListener;

import java.util.List;

public class DireccionAdapter extends RecyclerView.Adapter<DireccionViewHolder> {
    private final List<DireccionDTO> listDireccion;
    private final OnDireccionClickListener listener;

    public DireccionAdapter(List<DireccionDTO> listDireccion, OnDireccionClickListener listener) {
        this.listDireccion = listDireccion;
        this.listener = listener;
    }
    public void updateList(List<DireccionDTO> nuevaLista){
        listDireccion.clear();
        listDireccion.addAll(nuevaLista);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DireccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDireccionDireccionBinding binding = ItemDireccionDireccionBinding.inflate(LayoutInflater.
                from(parent.getContext()), parent, false);
        return new DireccionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DireccionViewHolder holder, int position) {
        DireccionDTO d = listDireccion.get(position);
        holder.render(d,listener);
    }

    @Override
    public int getItemCount() {
        return listDireccion.size();
    }
}
