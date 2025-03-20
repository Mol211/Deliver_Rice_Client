package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.BottomSheetDireccion;
import com.mol21.cliente_deliveryrice.databinding.ItemDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;

import java.util.List;

public class DireccionPagoAdapter extends RecyclerView.Adapter<DireccionPagoViewHolder> {
    private final List<DireccionDTO> listaDirecciones;
    private OnDireccionClickListener clickListener;
    /*Esta interface detecta los clicks en los elementos del Recycler
    Se usa dentro del ViewHolder para capturar los clicks en cada item*/

    public interface OnDireccionClickListener{
        void onDireccionClick(DireccionDTO direccion);
    }

    public DireccionPagoAdapter(List<DireccionDTO> listaDirecciones, OnDireccionClickListener clickListener) {
        this.listaDirecciones = listaDirecciones;
        this.clickListener = clickListener;
    }
    public void actualizarLista(List<DireccionDTO> nuevaListaDirecciones){
        listaDirecciones.clear();
        listaDirecciones.addAll(nuevaListaDirecciones);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DireccionPagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDireccionBinding binding = ItemDireccionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new DireccionPagoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DireccionPagoViewHolder holder, int position) {
        DireccionDTO d = listaDirecciones.get(position);
        holder.render(d);

        holder.itemView.setOnClickListener(v->{
            if(clickListener != null) {
                clickListener.onDireccionClick(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDirecciones.size();
    }
}
