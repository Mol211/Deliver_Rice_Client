package com.mol21.cliente_deliveryrice.mvvm.api;

import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Producto;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoApi {
    //Ruta del controlador del Producto
    String apiProducto = "api/producto";

    @GET(apiProducto + "/categoria/{categoria}")
    Call<GenericResponse<List<ProductoDTO>>> obtenerProducto(@Path("categoria")CategoriaProducto categoriaProducto);

    @POST(apiProducto)
    Call<GenericResponse<ProductoDTO>> crearProducto(@Body Producto p);
}
