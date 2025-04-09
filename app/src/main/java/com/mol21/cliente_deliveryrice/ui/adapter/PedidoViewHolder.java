package com.mol21.cliente_deliveryrice.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ItemPedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PedidoViewHolder extends RecyclerView.ViewHolder {
    private final ItemPedidoBinding binding;
    public PedidoViewHolder(@NonNull ItemPedidoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public void render(PedidoDTO p){
        binding.tvDireccionPedido.setText(
                p.getDireccion().getCalle()+ " "+
                p.getDireccion().getNumero()+", "+
                p.getDireccion().getCodPostal());

        EstadoPedido estadoP= p.getEstadoPedido();
        switch(estadoP){
            case CANCELADO:
                binding.ivEstadoPedido.setImageResource(R.drawable.baseline_circle_24_error);
                binding.tvEstadoPedido.setText("Cancelado");
                break;
            case ENTREGADO:
                binding.ivEstadoPedido.setImageResource(R.drawable.baseline_circle_24_exit);
                binding.tvEstadoPedido.setText("Completado");
                break;
            case EN_PROCESO:
                binding.ivEstadoPedido.setImageResource(R.drawable.baseline_circle_24_wait);
                binding.tvEstadoPedido.setText("En proceso");
                break;
            case PENDIENTE:
                binding.ivEstadoPedido.setImageResource(R.drawable.baseline_circle_24_wait);
                binding.tvEstadoPedido.setText("Pendiente");
                break;
            case ENVIADO:
                binding.ivEstadoPedido.setImageResource(R.drawable.baseline_circle_24_wait);
                binding.tvEstadoPedido.setText("Enviado");
                break;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d MMMM, yyyy | HH:mm",
                 new Locale("es","ES"));
        LocalDateTime fecha = p.getFechaCreacion();
        binding.tvFechaPedido.setText(fecha.format(formatter));

        binding.tvNumProductos.setText(p.getTotalProductos()+" productos");
        binding.tvTotalPedido.setText(p.getPrecioTotal()+"€");

    }
}
