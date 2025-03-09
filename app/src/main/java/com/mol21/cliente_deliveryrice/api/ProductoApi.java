package com.mol21.cliente_deliveryrice.api;

import com.mol21.cliente_deliveryrice.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoApi {
    //Ruta del controlador del Producto
    String apiProducto = "api/producto";

    @GET(apiProducto + "/categoria/{categoria}")
    Call<GenericResponse<List<ProductoDTO>>> obtenerProducto(@Path("categoria")CategoriaProducto categoriaProducto);
}
