package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemItemCarritoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import java.util.List;

public class ItemCarritoAdapter extends RecyclerView.Adapter<ItemCarritoViewHolder> {
    private final CarritoViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;
    List<ItemDTO> listaItems;
    public ItemCarritoAdapter(List<ItemDTO> listaItems, CarritoViewModel viewModel, LifecycleOwner lifecycleOwner){
        this.listaItems = listaItems;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
    }
    public void updateList(List<ItemDTO> nuevaLista){
        listaItems.clear();
        listaItems.addAll(nuevaLista);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemCarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemItemCarritoBinding binding = ItemItemCarritoBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ItemCarritoViewHolder(
                binding, viewModel, listaItems, lifecycleOwner, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCarritoViewHolder holder, int position) {
        ItemDTO item = listaItems.get(position);
        holder.render(item, position);
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }
}
