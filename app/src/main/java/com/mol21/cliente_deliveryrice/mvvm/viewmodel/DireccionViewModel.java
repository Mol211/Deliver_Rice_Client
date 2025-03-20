package com.mol21.cliente_deliveryrice.mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.mvvm.repository.DireccionRepository;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DireccionViewModel extends ViewModel {
    private final DireccionRepository direccionRepository;

    @Inject
    public DireccionViewModel(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    public LiveData<GenericResponse<List<DireccionDTO>>> obtenerDirecciones(long usuarioId){
        return this.direccionRepository.obtenerDirecciones(usuarioId);
    }
    public LiveData<GenericResponse<DireccionDTO>> obtenerPrincipal(long usuarioId){
        return this.direccionRepository.obtenerPrincipal(usuarioId);
    }
    public LiveData<GenericResponse<DireccionDTO>> hacerPrincipal(long direccionId){
        return this. direccionRepository.hacerPrincipal(direccionId);
    }
    public LiveData<GenericResponse<DireccionDTO>> guardarDireccion(long usuarioId, Direccion d){
        return this.direccionRepository.guardarDireccion(usuarioId,d);
    }
    public LiveData<GenericResponse<DireccionDTO>> actualizarDireccion(long usuarioId, Direccion d){
        return this.direccionRepository.actualizarDireccion(usuarioId,d);
    }
    public LiveData<GenericResponse<Void>> eliminarDireccion(long idDireccion){
        return this.direccionRepository.eliminarDireccion(idDireccion);
    }
    public LiveData<GenericResponse<Void>> eliminarDirecciones(List<Long> listaIds){
        return this.direccionRepository.eliminarDirecciones(listaIds);
    }
}
