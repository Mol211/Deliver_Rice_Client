package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Producto;
import com.mol21.cliente_deliveryrice.mvvm.repository.ProductoRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductoViewModel extends ViewModel {
    private final ProductoRepository productoRepository;
    @Inject
    public ProductoViewModel(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    public LiveData<GenericResponse<List<ProductoDTO>>> obtenerProductos(CategoriaProducto categoria){
        return this.productoRepository.obtenerProducto(categoria);
    }
    public LiveData<GenericResponse<ProductoDTO>> crearProducto(Producto p){
        return this.productoRepository.registrarProducto(p);
    }
}
