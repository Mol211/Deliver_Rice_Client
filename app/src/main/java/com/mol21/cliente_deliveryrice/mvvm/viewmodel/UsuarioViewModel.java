package com.mol21.cliente_deliveryrice.mvvm.viewmodel;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.RegistrarUsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Usuario;
import com.mol21.cliente_deliveryrice.mvvm.repository.UsuarioRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UsuarioViewModel extends ViewModel {
    private final UsuarioRepository usuarioRepository;
    @Inject
    public UsuarioViewModel(Application application, UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public LiveData<GenericResponse<UsuarioDTO>> login(String email, String password){
        return this.usuarioRepository.login(email, password);
    }
    public LiveData<GenericResponse<UsuarioDTO>> registrarCliente(RegistrarUsuarioDTO regUsuarioDTO){
        return this.usuarioRepository.registrarCliente(regUsuarioDTO);
    }
    public LiveData<GenericResponse<UsuarioDTO>> registrarRepartidor(RegistrarUsuarioDTO regUsuarioDTO){
        return this.usuarioRepository.registrarRepartidor(regUsuarioDTO);
    }
    public LiveData<GenericResponse<UsuarioDTO>> updateUser(long id,Usuario u){
        return this.usuarioRepository.updateUser(id,u);
    }
}
