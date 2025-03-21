package com.mol21.cliente_deliveryrice.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemProductBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoViewHolder> {
    private final LifecycleOwner lifecycleOwner;
    final CarritoViewModel carritoViewModel;
    List<ProductoDTO> listaProductos;
    final long carritoId;

    public ProductoAdapter(LifecycleOwner lifecycleOwner, List<ProductoDTO> listaProductos, CarritoViewModel carritoViewModel, long carritoId) {
        this.lifecycleOwner = lifecycleOwner;
        this.listaProductos = listaProductos;
        this.carritoViewModel = carritoViewModel;
        this.carritoId = carritoId;
        Log.d("CarritoID",String.valueOf(carritoId));
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ProductoViewHolder(this, carritoViewModel, lifecycleOwner,binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoDTO item = listaProductos.get(position);
        holder.render(item,carritoId);
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }
    public void updateList(List<ProductoDTO> nuevaLista) {
        listaProductos.clear();
        listaProductos.addAll(nuevaLista);
        notifyDataSetChanged();
    }
}
