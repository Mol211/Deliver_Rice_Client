package com.mol21.cliente_deliveryrice.mvvm.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mol21.cliente_deliveryrice.mvvm.api.ProductoApi;
import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Producto;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductoRepository {
    private final ProductoApi productoApi;
    @Inject
    public ProductoRepository(ProductoApi productoApi) {
        this.productoApi = productoApi;
    }

    public LiveData<GenericResponse<List<ProductoDTO>>> obtenerProducto(CategoriaProducto categoria) {
        final MutableLiveData<GenericResponse<List<ProductoDTO>>> respuesta = new MutableLiveData<>();
        this.productoApi.obtenerProducto(categoria).enqueue(new Callback<GenericResponse<List<ProductoDTO>>>() {

            @Override
            public void onResponse(Call<GenericResponse<List<ProductoDTO>>> call, Response<GenericResponse<List<ProductoDTO>>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<ProductoDTO>>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en UsuarioRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<ProductoDTO>> registrarProducto(Producto p) {
        final MutableLiveData<GenericResponse<ProductoDTO>> respuesta = new MutableLiveData<>();
        this.productoApi.crearProducto(p).enqueue(new Callback<GenericResponse<ProductoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<ProductoDTO>> call, Response<GenericResponse<ProductoDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<ProductoDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en UsuarioRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
}