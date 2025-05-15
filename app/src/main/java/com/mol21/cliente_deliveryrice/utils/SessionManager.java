package com.mol21.cliente_deliveryrice.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Rol;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SessionManager {
    private static final String PREF_NAME = "sesion";
    private static final String KEY_USUARIO_JSON= "usuarioJson";
    private static final String KEY_IDCARRITO= "idCarrito";
    private static final String KEY_SESION_INICIADA = "sesion_iniciada";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    @Inject
    public SessionManager(@ApplicationContext Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
    public void iniciarSesion(UsuarioDTO usuario, long idCarrito){
        String usuarioJson = gson.toJson(usuario);
        editor.putBoolean(KEY_SESION_INICIADA, true);
        editor.putString(KEY_USUARIO_JSON, usuarioJson);
        editor.putLong(KEY_IDCARRITO, idCarrito);
        editor.apply();
    }
    public UsuarioDTO getUsuario(){
        String usuarioJson = sharedPreferences.getString(KEY_USUARIO_JSON,null);
        return usuarioJson!=null ? gson.fromJson(usuarioJson, UsuarioDTO.class) : null;
    }
    public long getIdCarrito(){
        return sharedPreferences.getLong(KEY_IDCARRITO, -1);
    }
    public boolean isSesionIniciada(){
        return sharedPreferences.getBoolean(KEY_SESION_INICIADA, false);
    }
    public void cerrarSesion(){
        editor.clear();
        editor.apply();
    }

    public void nuevoCarrito(long idCarrito) {
        editor.remove(KEY_IDCARRITO);
        editor.putLong(KEY_IDCARRITO, idCarrito);
        editor.apply();
    }
}
