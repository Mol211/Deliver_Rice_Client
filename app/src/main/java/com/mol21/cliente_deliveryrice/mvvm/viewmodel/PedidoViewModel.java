package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTOCliente;
import com.mol21.cliente_deliveryrice.mvvm.repository.PedidoRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class PedidoViewModel extends ViewModel {

    private final PedidoRepository pedidoRepository;
    private final MutableLiveData<Long> idPedido = new MutableLiveData<>();
    public void guardarId(long idPedido){
        this.idPedido.postValue(idPedido);
    }
    public MutableLiveData<Long> getIdPedido(){
        return idPedido;
    }

    @Inject
    public PedidoViewModel(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    public LiveData<GenericResponse<List<PedidoDTOCliente>>> obtenerListPedido(long idCliente){
        return pedidoRepository.obtenerListaPedidos(idCliente);
    }
    public LiveData<GenericResponse<PedidoDTOCliente>> obtenerPedido(long idPedido){
        return pedidoRepository.obtenerPedido(idPedido);
    }

}