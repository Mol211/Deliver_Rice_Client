package com.mol21.cliente_deliveryrice.api;

import com.mol21.cliente_deliveryrice.model.DTO.RegistrarUsuarioDTO;
import com.mol21.cliente_deliveryrice.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.model.Usuario;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioApi {
    //Ruta del controlador de usuario
    String apiUsuario= "api/usuario";

    //Ruta del login
     String rutaLogin = apiUsuario.concat("/login");
     String rutaRegistrarCliente = apiUsuario.concat("/cliente");
    @FormUrlEncoded
    @POST(apiUsuario+"/login")
    Call<GenericResponse<UsuarioDTO>> login(@Field("email") String email, @Field("password") String password);

    @POST(apiUsuario+"/cliente")
    Call<GenericResponse<UsuarioDTO>> registrarCliente(@Body RegistrarUsuarioDTO usuarioDTO);

    @POST(apiUsuario+"/repartidor")
    Call<GenericResponse<UsuarioDTO>> registrarRepartidor(@Body RegistrarUsuarioDTO usuarioDTO);

    @PUT(apiUsuario+"/{id}")
    Call<GenericResponse<UsuarioDTO>> updateUser(@Path("id") long id, @Body Usuario u);
}
