package com.mol21.cliente_deliveryrice.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mol21.cliente_deliveryrice.api.UsuarioApi;
import com.mol21.cliente_deliveryrice.model.DTO.RegistrarUsuarioDTO;
import com.mol21.cliente_deliveryrice.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.model.Direccion;
import com.mol21.cliente_deliveryrice.model.Usuario;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.Global;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsuarioRepository {

    private final UsuarioApi usuarioApi;

    @Inject
    public UsuarioRepository(UsuarioApi api) {
        this.usuarioApi = api;
    }

    public LiveData<GenericResponse<UsuarioDTO>> login(String email, String password) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> respuesta = new MutableLiveData<>();
        this.usuarioApi.login(email, password).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al conectar con el servidor " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<UsuarioDTO>> registrarCliente(RegistrarUsuarioDTO regUsuarioDTO) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> respuesta = new MutableLiveData<>();
        this.usuarioApi.registrarCliente(regUsuarioDTO).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>(
                        Global.TIPO_EX,
                        Global.RPTA_ERROR,
                        "Se ha producido un error al conectar con el servidor "+t.getMessage(),
                        null
                ));
                System.out.println("Se ha producido un error al conectar con el servidor. Error en UsuarioRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<UsuarioDTO>> registrarRepartidor(RegistrarUsuarioDTO regUsuarioDTO) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> respuesta = new MutableLiveData<>();
        this.usuarioApi.registrarRepartidor(regUsuarioDTO).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al conectar con el servidor. Error en UsuarioRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }

    public LiveData<GenericResponse<UsuarioDTO>> updateUser(long id, Usuario u) {
        final MutableLiveData<GenericResponse<UsuarioDTO>> respuesta = new MutableLiveData<>();
        this.usuarioApi.updateUser(id, u).enqueue(new Callback<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<UsuarioDTO>> call, Response<GenericResponse<UsuarioDTO>> response) {
                respuesta.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<UsuarioDTO>> call, Throwable t) {
                respuesta.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al conectar con el servidor. Error en UsuarioRepository. " + t.getMessage());
                t.printStackTrace();
            }
        });
        return respuesta;
    }
}



