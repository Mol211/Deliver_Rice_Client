package com.mol21.cliente_deliveryrice.mvvm.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.mol21.cliente_deliveryrice.mvvm.api.PedidoApi;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTOCliente;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepository {
    private final PedidoApi pedidoApi;

    @Inject
    public PedidoRepository(PedidoApi pedidoApi) {
        this.pedidoApi = pedidoApi;
    }
    public LiveData<GenericResponse<List<PedidoDTOCliente>>> obtenerListaPedidos(long idUsuario){
        final MutableLiveData<GenericResponse<List<PedidoDTOCliente>>> respuesta = new MutableLiveData<>();
        this.pedidoApi.obtenerListPedidos(idUsuario).enqueue(new Callback<GenericResponse<List<PedidoDTOCliente>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<PedidoDTOCliente>>> call, Response<GenericResponse<List<PedidoDTOCliente>>> response) {
                respuesta.setValue(response.body());
                Log.d("PEDIDOREPOSITORY", "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<PedidoDTOCliente>>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor "+ t.getMessage(),
                        null
                ));
                Log.d("ERROR-PEDIDOREPOSITORY", "onResponse: "+t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<PedidoDTOCliente>> obtenerPedido(long idPedido){
        final MutableLiveData<GenericResponse<PedidoDTOCliente>> respuesta = new MutableLiveData<>();
        this.pedidoApi.obtenerPedido(idPedido).enqueue(new Callback<GenericResponse<PedidoDTOCliente>>() {
            @Override
            public void onResponse(Call<GenericResponse<PedidoDTOCliente>> call, Response<GenericResponse<PedidoDTOCliente>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<PedidoDTOCliente>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor "+ t.getMessage(),
                        null
                ));
            }
        });
        return respuesta;
    }
}
