package com.mol21.cliente_deliveryrice.mvvm.api;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.CarritoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CarritoApi {
    String apiCarrito = "api/carrito";
    String apiItem = "api/item";

    @GET(apiCarrito + "/{idUsuario}")
    Call<GenericResponse<CarritoDTO>> obtenerCarrito(
            @Path("idUsuario") long idUsuario);

    @POST(apiCarrito + "/procesar/{idCarrito}")
    Call<GenericResponse<Object>> procesarCarrito(
            @Path("idCarrito") long idCarrito,
            @Query("metodoPago") MetodoPago metodoPago,
            @Query("idDireccion") long idDireccion);

    @POST(apiCarrito + "/usuario/{idUsuario}")
    Call<GenericResponse<CarritoDTO>> nuevoCarrito(
            @Path("idUsuario")long idUsuario);
    @POST(apiCarrito + "/vaciar-carrito/usuario/{idUsuario}")
    Call<GenericResponse<CarritoDTO>> vaciarCarrito(
            @Path("idUsuario")long idUsuario);
    @POST(apiItem + "/agregar")
    Call<GenericResponse<ItemDTO>>addItem(
            @Query("idCarrito") long idCarrito,
            @Query("idProducto") long idProducto,
            @Query("cantidad") int cantidad);
    @PUT(apiItem + "/modificar/{itemId}")
    Call<GenericResponse<ItemDTO>>modificarItem(
            @Path("itemId") long itemId,
            @Query("cantidad") int cantidad
    );

    @GET(apiItem + "/listaItems/{carritoId}")
    Call<GenericResponse<List<ItemDTO>>>listaItems(@Path("carritoId") long carritoId);

    @DELETE(apiItem + "/eliminar-item/{itemId}")
    Call<GenericResponse<ItemDTO>>deleteItem(@Path("itemId") long itemId);

}
