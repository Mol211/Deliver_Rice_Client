package com.mol21.cliente_deliveryrice.mvvm.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mol21.cliente_deliveryrice.mvvm.api.CarritoApi;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.CarritoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoRepository {
    private final CarritoApi carritoApi;
    @Inject
    public CarritoRepository(CarritoApi carritoApi) {
        this.carritoApi = carritoApi;
    }

    public LiveData<GenericResponse<CarritoDTO>> obtenerCarrito(long idUsuario) {
        final MutableLiveData<GenericResponse<CarritoDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.obtenerCarrito(idUsuario).enqueue(new Callback<GenericResponse<CarritoDTO>>(){

            @Override
            public void onResponse(Call<GenericResponse<CarritoDTO>> call, Response<GenericResponse<CarritoDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<CarritoDTO>> call, Throwable t) {
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

    public LiveData<GenericResponse<Object>> procesarCarrito(long idCarrito, MetodoPago metodoPago, long idDireccion) {
        final MutableLiveData<GenericResponse<Object>> respuesta = new MutableLiveData<>();
        this.carritoApi.procesarCarrito(idCarrito, metodoPago, idDireccion).enqueue(new Callback<GenericResponse<Object>>() {

            @Override
            public void onResponse(Call<GenericResponse<Object>> call, Response<GenericResponse<Object>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Object>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<CarritoDTO>> nuevoCarrito(long idUsuario) {
        final MutableLiveData<GenericResponse<CarritoDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.nuevoCarrito(idUsuario).enqueue(new Callback<GenericResponse<CarritoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<CarritoDTO>> call, Response<GenericResponse<CarritoDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<CarritoDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                        null
                ));
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<CarritoDTO>> vaciarCarrito(long idUsuario) {
        final MutableLiveData<GenericResponse<CarritoDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.vaciarCarrito(idUsuario).enqueue(new Callback<GenericResponse<CarritoDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<CarritoDTO>> call, Response<GenericResponse<CarritoDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d("RespuestaServidor", "Respuesta exitosa: " + response.body());
                    respuesta.setValue(response.body());
                } else {
                    Log.d("RespuestaServidor", "Error en la respuesta: " + response.errorBody());
                    respuesta.setValue(new GenericResponse<>(Global.TIPO_EX, Global.RPTA_ERROR, "Se ha producido un error en la respuesta", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<CarritoDTO>> call, Throwable t) {
                Log.d("RespuestaServidor", "Error en la conexión: " + t.getMessage());
                t.printStackTrace();
                if (t instanceof IOException) {
                    // Error de red (por ejemplo, sin conexión a Internet)
                    Log.d("RespuestaServidor", "Error de red: " + t.getMessage());
                } else {
                    // Otros errores
                    Log.d("RespuestaServidor", "Error desconocido: " + t.getMessage());
                    respuesta.setValue(new GenericResponse<>(
                            Global.TIPO_EX,
                            Global.RPTA_ERROR,
                            "Se ha producido un error al conectar con el servidor " + t.getMessage(),
                            null
                    ));
                }
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<List<ItemDTO>>>listaItem(long idCarrito){
        final MutableLiveData<GenericResponse<List<ItemDTO>>> respuesta = new MutableLiveData<>();
        this.carritoApi.listaItems(idCarrito).enqueue(new Callback<GenericResponse<List<ItemDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<ItemDTO>>> call, Response<GenericResponse<List<ItemDTO>>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<ItemDTO>>> call, Throwable t) {
                Log.d("RespuestaServidor", "Error en la conexión: " + t.getMessage());
                t.printStackTrace();
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

    public LiveData<GenericResponse<ItemDTO>>modificarItem(long idItem, int cantidad){
        final MutableLiveData<GenericResponse<ItemDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.modificarItem(idItem, cantidad).enqueue(new Callback<GenericResponse<ItemDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<ItemDTO>> call, Response<GenericResponse<ItemDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<ItemDTO>> call, Throwable t) {
                Log.d("RespuestaServidor", "Error en la conexión: " + t.getMessage());
                t.printStackTrace();
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
    public LiveData<GenericResponse<ItemDTO>>addItem(long idCarrito, long idProducto, int cantidad){
        final MutableLiveData<GenericResponse<ItemDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.addItem(idCarrito, idProducto, cantidad).enqueue(new Callback<GenericResponse<ItemDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<ItemDTO>> call, Response<GenericResponse<ItemDTO>> response) {
                respuesta.setValue(response.body());
                Log.d("Respuesta",response.message());
            }

            @Override
            public void onFailure(Call<GenericResponse<ItemDTO>> call, Throwable t) {
                Log.d("RespuestaServidor", "Error en la conexión: " + t.getMessage());
                t.printStackTrace();
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
    public LiveData<GenericResponse<ItemDTO>>deleteItem(long itemId){
        final MutableLiveData<GenericResponse<ItemDTO>> respuesta = new MutableLiveData<>();
        this.carritoApi.deleteItem(itemId).enqueue(new Callback<GenericResponse<ItemDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<ItemDTO>> call, Response<GenericResponse<ItemDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<ItemDTO>> call, Throwable t) {
                Log.d("RespuestaServidor", "Error en la conexión: " + t.getMessage());
                t.printStackTrace();
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
