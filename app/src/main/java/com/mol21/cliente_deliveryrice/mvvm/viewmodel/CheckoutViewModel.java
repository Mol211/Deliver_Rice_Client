package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.mvvm.repository.CheckoutRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class CheckoutViewModel extends ViewModel {
    private final CheckoutRepository CHECKOUTREPOSITORY;

    @Inject
    public CheckoutViewModel(CheckoutRepository checkoutrepository) {
        CHECKOUTREPOSITORY = checkoutrepository;
    }
    public LiveData<GenericResponse<List<PedidoDTO>>> obtenerPedidos(Long idUsuario, long idRepartidor, EstadoPedido estado){
        return this.CHECKOUTREPOSITORY.obtenerPedidos(idUsuario, idRepartidor, estado);
    }
    public LiveData<GenericResponse<PedidoDTO>> obtenerPedido(long idPedido){
        return this.CHECKOUTREPOSITORY.obtenerPedido(idPedido);
    }
    public LiveData<GenericResponse<PedidoDTO>> cambiarEstado(long idPedido, String accion, long idRepartidor){
        return this.CHECKOUTREPOSITORY.cambiarEstado(idPedido, accion, idRepartidor);
    }


}
