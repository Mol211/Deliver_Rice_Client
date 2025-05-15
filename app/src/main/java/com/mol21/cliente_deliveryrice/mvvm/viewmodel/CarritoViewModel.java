package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.CarritoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.mvvm.repository.CarritoRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CarritoViewModel extends ViewModel {
    private final CarritoRepository carritoRepository;
    private final MutableLiveData<List<ItemDTO>> listaProductos = new MutableLiveData<>();
    //Total de productos y precio con getter para obtenerlos.
    private final MutableLiveData<Integer> totalProductos = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> totalPrecio = new MutableLiveData<>();
    public MutableLiveData<List<ItemDTO>> getListaProductos() {
        return listaProductos;
    }
    public MutableLiveData<Integer> getTotalProductos() {
        return totalProductos;
    }
    public MutableLiveData<BigDecimal> getTotalPrecio() {
        return totalPrecio;
    }
    //Actualizar precio y productos del carrito
    public void actualizarTotales(List<ItemDTO> items){
        int total = 0;
        BigDecimal precio = BigDecimal.ZERO;
        List<ItemDTO>lista = Collections.emptyList();

        for(ItemDTO item : items){
            total += item.getCantidad();
            precio = precio.add(item.getSubTotal());
        }
        totalProductos.postValue(total);
        totalPrecio.postValue(precio);

    }

    @Inject
    public CarritoViewModel( CarritoRepository repository) {
        this.carritoRepository = repository;
    }
    public LiveData<GenericResponse<CarritoDTO>> obtenerCarrito(long idUsuario){
        return this.carritoRepository.obtenerCarrito(idUsuario);
    }
    public LiveData<GenericResponse<Object>> procesarCarrito(long idCarrito, MetodoPago metodoPago, long idDireccion ){
        return this.carritoRepository.procesarCarrito(idCarrito, metodoPago, idDireccion);
    }
    public LiveData<GenericResponse<CarritoDTO>> nuevoCarrito(long idUsuario){
        return this.carritoRepository.nuevoCarrito(idUsuario);
    }
    public LiveData<GenericResponse<CarritoDTO>> vaciarCarrito(long idUsuario){
        return this.carritoRepository.vaciarCarrito(idUsuario);
    }
    public LiveData<GenericResponse<ItemDTO>>addItem(long idCarrito, long idProducto, int cantidad){
        return this.carritoRepository.addItem(idCarrito,idProducto,cantidad);
    }

    public LiveData<GenericResponse<ItemDTO>>modificarItem(long idItem, int cantidad){
        return this.carritoRepository.modificarItem(idItem, cantidad);
    }
    public LiveData<GenericResponse<List<ItemDTO>>>listaItems(long idCarrito){
        return this.carritoRepository.listaItem(idCarrito);
    }

    public LiveData<GenericResponse<ItemDTO>>deleteItem(long itemId){
        return this.carritoRepository.deleteItem(itemId);
    }


    //Total Precio y Productos

}
