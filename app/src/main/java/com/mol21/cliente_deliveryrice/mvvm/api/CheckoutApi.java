package com.mol21.cliente_deliveryrice.mvvm.api;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CheckoutApi {

    String apiCheckout = "api/checkout";

    @GET(apiCheckout)
    Call<GenericResponse<List<PedidoDTO>>> obtenerPedidos(
            @QueryMap Map<String, Object> params);

    @GET(apiCheckout + "/pedido/{idPedido}")
    Call<GenericResponse<PedidoDTO>> obtenerPedido(@Path("idPedido")Long idPedido);

    @PUT(apiCheckout + "/cambio-estado/{idPedido}")
    Call<GenericResponse<PedidoDTO>>cambiarEstado(
            @Path("idPedido")Long idPedido,
            @Query("accion") String accion,
            @Query("idRepartidor") Long idRepartidor);
}
