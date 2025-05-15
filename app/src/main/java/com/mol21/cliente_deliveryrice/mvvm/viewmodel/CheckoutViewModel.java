package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.mvvm.repository.CheckoutRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class CheckoutViewModel extends ViewModel {
    private final CheckoutRepository CHECKOUTREPOSITORY;
    List<ItemDTO>pedidosEnProceso = new ArrayList<>();
    List<ItemDTO>pedidosEnviados = new ArrayList<>();
    List<ItemDTO>pedidosPentiente = new ArrayList<>();

    @Inject
    public CheckoutViewModel(CheckoutRepository checkoutrepository) {
        CHECKOUTREPOSITORY = checkoutrepository;
    }
    public LiveData<GenericResponse<List<PedidoDTO>>> obtenerPedidos(Long idUsuario, Long idRepartidor, EstadoPedido estado){

        return this.CHECKOUTREPOSITORY.obtenerPedidos(idUsuario, idRepartidor, estado);
    }
    public LiveData<GenericResponse<PedidoDTO>> obtenerPedido(long idPedido){
        return this.CHECKOUTREPOSITORY.obtenerPedido(idPedido);
    }
    public LiveData<GenericResponse<PedidoDTO>> cambiarEstado(long idPedido, String accion, long idRepartidor){
        return this.CHECKOUTREPOSITORY.cambiarEstado(idPedido, accion, idRepartidor);
    }


    public List<LiveData<GenericResponse<List<PedidoDTO>>>> obtenerPedidosEnProceso() {
        LiveData<GenericResponse<List<PedidoDTO>>> pedidosEnProceso = obtenerPedidos(null,null,EstadoPedido.EN_PROCESO);
        LiveData<GenericResponse<List<PedidoDTO>>> pedidosEnviados = obtenerPedidos(null,null,EstadoPedido.ENVIADO);
        LiveData<GenericResponse<List<PedidoDTO>>> pedidosPendiente = obtenerPedidos(null,null,EstadoPedido.PENDIENTE);
        List<LiveData<GenericResponse<List<PedidoDTO>>>> listaDeListas = new ArrayList<>();
        listaDeListas.add(pedidosPendiente);
        listaDeListas.add(pedidosEnviados);
        listaDeListas.add(pedidosEnProceso);

        return listaDeListas;
    }
}
