package com.mol21.cliente_deliveryrice.mvvm.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mol21.cliente_deliveryrice.mvvm.api.DireccionApi;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DireccionRepository {
    private final DireccionApi direccionApi;
    @Inject
    public DireccionRepository(DireccionApi direccionApi) {
        this.direccionApi = direccionApi;
    }
    public LiveData<GenericResponse<List<DireccionDTO>>> obtenerDirecciones(long idUsuario){
        final MutableLiveData<GenericResponse<List<DireccionDTO>>> respuesta = new MutableLiveData<>();
        this.direccionApi.obtenerDirecciones(idUsuario).enqueue(new Callback<GenericResponse<List<DireccionDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<DireccionDTO>>> call, Response<GenericResponse<List<DireccionDTO>>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<DireccionDTO>>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<DireccionDTO>> obtenerPrincipal(long idUsuario){
        final MutableLiveData<GenericResponse<DireccionDTO>> respuesta = new MutableLiveData<>();
        this.direccionApi.obtenerPrincipal(idUsuario).enqueue(new Callback<GenericResponse<DireccionDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<DireccionDTO>> call, Response<GenericResponse<DireccionDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DireccionDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }


    public LiveData<GenericResponse<DireccionDTO>> hacerPrincipal(long idDireccion){
        final MutableLiveData<GenericResponse<DireccionDTO>> respuesta = new MutableLiveData<>();
        this.direccionApi.hacerPrincipal(idDireccion).enqueue(new Callback<GenericResponse<DireccionDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<DireccionDTO>> call, Response<GenericResponse<DireccionDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DireccionDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<DireccionDTO>> guardarDireccion(long idUsuario, Direccion d){
        final MutableLiveData<GenericResponse<DireccionDTO>> respuesta = new MutableLiveData<>();
        this.direccionApi.guardarDireccion(idUsuario, d).enqueue(new Callback<GenericResponse<DireccionDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<DireccionDTO>> call, Response<GenericResponse<DireccionDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DireccionDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }
    public LiveData<GenericResponse<DireccionDTO>> actualizarDireccion(long idUsuario, Direccion d) {
        final MutableLiveData<GenericResponse<DireccionDTO>> respuesta = new MutableLiveData<>();
        this.direccionApi.actualizarDireccion(idUsuario, d).enqueue(new Callback<GenericResponse<DireccionDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<DireccionDTO>> call, Response<GenericResponse<DireccionDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DireccionDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }
        public LiveData<GenericResponse<DireccionDTO>> desactivarDireccion(long id){
            final MutableLiveData<GenericResponse<DireccionDTO>> respuesta = new MutableLiveData<>();
            this.direccionApi.desactivarDireccion(id).enqueue(new Callback<GenericResponse<DireccionDTO>>() {
                @Override
                public void onResponse(Call<GenericResponse<DireccionDTO>> call, Response<GenericResponse<DireccionDTO>> response) {
                    respuesta.setValue(response.body());
                }

                @Override
                public void onFailure(Call<GenericResponse<DireccionDTO>> call, Throwable t) {
                    t.printStackTrace();
                    respuesta.setValue(new GenericResponse<>(
                            Global.TIPO_EX,
                            Global.RPTA_ERROR,
                            "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                            null));
                }
            });
            return respuesta;
        }

    public LiveData<GenericResponse<Void>> eliminarDirecciones(List<Long> listaIds){
        final MutableLiveData<GenericResponse<Void>> respuesta = new MutableLiveData<>();
        this.direccionApi.eliminarDirecciones(listaIds).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "DIRECCIÓN REPOSTIORY: ERROR · "+ t.getMessage(),
                        null));
            }
        });
        return respuesta;
    }
}
