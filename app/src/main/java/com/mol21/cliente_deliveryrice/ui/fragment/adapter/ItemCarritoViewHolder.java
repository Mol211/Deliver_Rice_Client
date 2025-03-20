package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mol21.cliente_deliveryrice.databinding.ItemItemCarritoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import java.util.List;

public class ItemCarritoViewHolder extends RecyclerView.ViewHolder {
    private final CarritoViewModel viewModel;
    private final List<ItemDTO> items;
    private final LifecycleOwner lifecycleOwner;
    private final ItemCarritoAdapter adapter;

    private final ItemItemCarritoBinding binding;
    public ItemCarritoViewHolder(
            ItemItemCarritoBinding binding,
            CarritoViewModel viewModel,
            List<ItemDTO> items, LifecycleOwner lifecycleOwner,
            ItemCarritoAdapter adapter) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        this.items = items;
        this.lifecycleOwner = lifecycleOwner;
        this.adapter = adapter;
    }
    public void render(ItemDTO item, int position) {
        Glide.with(binding.ivProduct.getContext())
                .load(item.getImagenUrl())
                .centerCrop()
                .into(binding.ivProduct);
        binding.tvPrecio.setText(item.getSubTotal()+"€");
        binding.txtCantidad.setText(String.valueOf(item.getCantidad()));
        binding.tvTitulo.setText(item.getNombreProducto());
        //Botón restar 1





        binding.btnEliminar.setOnClickListener(view -> {
            viewModel.modificarItem(item.getId(),-1)
                    .observe(lifecycleOwner, response->{
                    if(response.getRpta()==1){
                        //Obtenemos la nueva cantidad y modificamos la cantidad Y el subtotal del item.
                        int nuevaCantidad = response.getBody().getCantidad();
                        item.setCantidad(nuevaCantidad);


                        binding.txtCantidad.setText(String.valueOf(nuevaCantidad));
                        binding.tvPrecio.setText(item.getSubTotal()+"€");
                        viewModel.actualizarTotales(items);
                    } else if (response.getRpta()==0) {
                        items.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position,items.size());
                        viewModel.actualizarTotales(items);
                    }else{
                        Log.e("ERROR",response.getMessage());
                    }
                    });
        });



        binding.btnAdd.setOnClickListener(view -> {
            viewModel.modificarItem(item.getId(),1)
                    .observe(lifecycleOwner, response->{
                        if(response.getRpta()==1){
                            int nuevaCantidad = response.getBody().getCantidad();
                            item.setCantidad(nuevaCantidad);

                            binding.txtCantidad.setText(String.valueOf(nuevaCantidad));
                            binding.tvPrecio.setText(item.getSubTotal()+"€");
                            viewModel.actualizarTotales(items);
                        }
                        else{
                            Log.e("ERROR", response.getMessage());
                        }
                    });
        });
    }


}
