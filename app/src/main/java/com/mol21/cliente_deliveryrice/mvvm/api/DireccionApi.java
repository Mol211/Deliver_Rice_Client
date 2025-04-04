package com.mol21.cliente_deliveryrice.mvvm.api;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DireccionApi {
    String apiDireccion = "api/direccion";
    @GET(apiDireccion + "/usuario/{idUsuario}")
    Call<GenericResponse<List<DireccionDTO>>> obtenerDirecciones(@Path("idUsuario") long idUsuario);
    @GET(apiDireccion + "/principal/{idUsuario}")
    Call <GenericResponse<DireccionDTO>> obtenerPrincipal(@Path("idUsuario") long idUsuario);
    @PUT(apiDireccion + "/principal/{id}")
    Call<GenericResponse<DireccionDTO>> hacerPrincipal(@Path("id") long id);
    @POST(apiDireccion + "/usuario/{idUsuario}")
    Call<GenericResponse<DireccionDTO>> guardarDireccion
            (@Path("idUsuario")long idUsuario,
            @Body Direccion direccion);
    @PUT(apiDireccion + "/actualizar")
    Call<GenericResponse<DireccionDTO>> actualizarDireccion
            (@Body Direccion direccion);
    @DELETE(apiDireccion + "/{id}")
    Call<GenericResponse<DireccionDTO>> desactivarDireccion(@Path("id") long id);
    @DELETE(apiDireccion + "/delete")
    Call<GenericResponse<Void>> eliminarDirecciones(@Body List<Long> listaIds);


}
