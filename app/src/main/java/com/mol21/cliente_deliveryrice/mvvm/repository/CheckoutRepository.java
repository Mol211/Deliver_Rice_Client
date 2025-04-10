package com.mol21.cliente_deliveryrice.mvvm.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mol21.cliente_deliveryrice.mvvm.api.CheckoutApi;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutRepository {

    private final CheckoutApi checkoutApi;

    @Inject
    public CheckoutRepository(CheckoutApi checkoutApi) {
        this.checkoutApi = checkoutApi;
    }
    public LiveData<GenericResponse<List<PedidoDTO>>> obtenerPedidos(Long idUsuario, Long idRepartidor, EstadoPedido estado) {
        final MutableLiveData<GenericResponse<List<PedidoDTO>>> respuesta = new MutableLiveData<>();

        Map<String, Object> params = new HashMap<>();

        if(idUsuario!=null) params.put("idUsuario", idUsuario);
        if(idRepartidor!=null) params.put("idRepartidor",idRepartidor);
        if(estado!=null) params.put("estado", estado);


        this.checkoutApi.obtenerPedidos(params).enqueue(new Callback<GenericResponse<List<PedidoDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<PedidoDTO>>> call, Response<GenericResponse<List<PedidoDTO>>> response) {
                List<PedidoDTO> pedidosCompletados = new ArrayList<>();
                List<PedidoDTO> pedidosEnProceso = new ArrayList<>();
                List<PedidoDTO> pedidosEnviados = new ArrayList<>();
                List<PedidoDTO> pedidosCancelados= new ArrayList<>();
                List<PedidoDTO> pedidosPendientes = new ArrayList<>();
                for(PedidoDTO pedido: response.body().getBody()) {
                    if(pedido.getEstadoPedido().equals(EstadoPedido.ENTREGADO)) {
                        pedidosCompletados.add(pedido);
                    } else if(pedido.getEstadoPedido().equals(EstadoPedido.CANCELADO)){
                        pedidosCancelados.add(pedido);
                    } else if(pedido.getEstadoPedido().equals(EstadoPedido.ENVIADO)){
                        pedidosEnviados.add(pedido);
                    } else if(pedido.getEstadoPedido().equals(EstadoPedido.EN_PROCESO)){
                        pedidosEnProceso.add(pedido);
                    } else{
                        pedidosPendientes.add(pedido);
                    }
                }
                List<PedidoDTO>pedidosOrdenados = new ArrayList<>();
                pedidosOrdenados.addAll(pedidosCompletados);
                pedidosOrdenados.addAll(pedidosEnviados);
                pedidosOrdenados.addAll(pedidosEnProceso);
                pedidosOrdenados.addAll(pedidosPendientes);
                pedidosOrdenados.addAll(pedidosCancelados);

                response.body().setBody(pedidosOrdenados);
                respuesta.setValue(response.body());

            }

            @Override
            public void onFailure(Call<GenericResponse<List<PedidoDTO>>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en CheckoutRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<PedidoDTO>> obtenerPedido(long idPedido) {
        final MutableLiveData<GenericResponse<PedidoDTO>> respuesta = new MutableLiveData<>();
        this.checkoutApi.obtenerPedido(idPedido).enqueue(new Callback<GenericResponse<PedidoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<PedidoDTO>> call, Response<GenericResponse<PedidoDTO>>response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<PedidoDTO>>call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en CheckoutRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<PedidoDTO>> cambiarEstado(long idPedido, String accion, long idRepartidor) {
        final MutableLiveData<GenericResponse<PedidoDTO>> respuesta = new MutableLiveData<>();
        this.checkoutApi.cambiarEstado(idPedido, accion, idRepartidor).enqueue(new Callback<GenericResponse<PedidoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<PedidoDTO>> call, Response<GenericResponse<PedidoDTO>>response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<PedidoDTO>>call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en CheckoutRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }




}
