package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemItemPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;

import java.util.List;

public class ItemPagoAdapter extends RecyclerView.Adapter<ItemPagoViewHolder> {
    private final List<ItemDTO> listItem;
    public ItemPagoAdapter(List<ItemDTO> listItem) {
        this.listItem = listItem;
    }
    public void updateList(List<ItemDTO> nuevaLista){
        listItem.clear();
        listItem.addAll(nuevaLista);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemPagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemItemPagoBinding binding = ItemItemPagoBinding.
                inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ItemPagoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPagoViewHolder holder, int position) {
        ItemDTO item = listItem.get(position);
        holder.render(item);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
