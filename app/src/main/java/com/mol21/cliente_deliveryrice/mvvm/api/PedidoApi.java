package com.mol21.cliente_deliveryrice.mvvm.api;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PedidoApi {
    String apiPedido = "api/pedido";
    @GET(apiPedido +"/cliente/{usuarioId}")
    Call<GenericResponse<List<PedidoDTO>>> obtenerListPedidos(
            @Path("usuarioId")long usuarioId);

    @GET(apiPedido + "/{pedidoId}")
    Call<GenericResponse<PedidoDTO>> obtenerPedido(
            @Path("pedidoId") long pedidoId);
}
