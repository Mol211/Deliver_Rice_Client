package com.mol21.cliente_deliveryrice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.databinding.ItemPedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.ui.listener.OnItemClickListener;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    private final List<PedidoDTO> listaPedidos;
    private OnItemClickListener<PedidoDTO> clickListener;

    public PedidoAdapter(List<PedidoDTO> listaPedidos, OnItemClickListener<PedidoDTO> listener) {
        this.listaPedidos = listaPedidos;
        this.clickListener = listener;
    }

    public void updateList(List<PedidoDTO> nuevaLista){
        listaPedidos.clear();
        listaPedidos.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPedidoBinding binding = ItemPedidoBinding.inflate(LayoutInflater.
                        from(parent.getContext()), parent, false);
        return new PedidoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        PedidoDTO pedido = listaPedidos.get(position);
        holder.render(pedido);

        //Obtenemos el Click en el holder y guardamos el Id del Pedido.
        holder.itemView.setOnClickListener(v->{
            clickListener.onItemClick(pedido);
        });
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }
}
