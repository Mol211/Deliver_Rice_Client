package com.mol21.cliente_deliveryrice.ui.fragment.adapter;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mol21.cliente_deliveryrice.databinding.ItemProductBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

public class ProductoViewHolder extends RecyclerView.ViewHolder {
    private final ProductoAdapter adapter;
    private final CarritoViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;
    private final ItemProductBinding binding;
    public ProductoViewHolder(ProductoAdapter adapter, CarritoViewModel viewModel, LifecycleOwner lifecycleOwner, ItemProductBinding binding) {
        super(binding.getRoot());
        this.adapter = adapter;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.binding = binding;
    }

    public void render(ProductoDTO item,long carritoId) {
        Glide.with(binding.ivProduct.getContext())
                .load(item.getImagenURL())
                .centerCrop()
                .into(binding.ivProduct);
        binding.tvPrecio.setText(item.getPrecio()+"€");
        binding.tvTitulo.setText(item.getNombre());
        binding.tvDescripciN.setText(item.getDescripcion());

        viewModel.listaItems(carritoId).observe(lifecycleOwner,respuesta->{
            if(respuesta.getRpta()==1){
                for(ItemDTO i : respuesta.getBody()){
                    if(i.getIdProducto()==item.getId()){
                        binding.tvCant.setText("x"+i.getCantidad());
                        return;
                    }
                }
            }
        });


        binding.btnAdd.setOnClickListener(v->{
            viewModel.addItem(carritoId,item.getId(),1).observe(lifecycleOwner,response-> {
                if (response.getRpta() == 1) {
                    viewModel.listaItems(carritoId).observe(lifecycleOwner,respuesta->{
                        if(respuesta.getRpta()==1){
                            viewModel.actualizarTotales(respuesta.getBody());
                            for(ItemDTO i : respuesta.getBody()){
                                Log.d("LISTA",i.toString());
                            }
                        } else{
                        Log.e("Error",respuesta.getMessage());
                        }
                    });
                    //Cada vez que se pulsa el botón de añadir al carrito se actualiza el viewModel

                    //viewModel.aumentar();
                    //Siempre va a dar Response 1 porque no existe botón de restar producto del item
                    Toast.makeText(itemView.getContext(), "Se ha añadido el producto al carrito", Toast.LENGTH_SHORT).show();
                    binding.tvCant.setText("x"+String.valueOf(response.getBody().getCantidad()));
                } else {
                    Log.d("carritoID: ",String.valueOf(carritoId));
                    Log.e("ERROR", response.getMessage());
                }
            });

            });
    }
}
