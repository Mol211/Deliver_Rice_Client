package com.mol21.cliente_deliveryrice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mol21.cliente_deliveryrice.databinding.FragmentBottomSheetDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.ui.fragment.adapter.DireccionPagoAdapter;

import java.util.List;

public class BottomSheetDireccion extends BottomSheetDialogFragment {
    RecyclerView recycler;
    OnDireccionSeleccionadaListener listener;
    private final List<DireccionDTO> listaDirecciones;
    private FragmentBottomSheetDireccionBinding binding;
    //Interface que sirve para comunicar la direccion seleccionada al parent que lo invocó.
    public interface OnDireccionSeleccionadaListener {
        void onDireccionSeleccionada(DireccionDTO d);
    }

    public BottomSheetDireccion(List<DireccionDTO> listaDirecciones) {
        // Required empty public constructor
        this.listaDirecciones = listaDirecciones;
    }
    //Primer metodo del ciclo de vida de un Fragment
    //Se crea cuando un fragment se "pega" "Attach" a una activity.
    //Podemos obtener referencias a la Activity y comunicarnos con ella

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment(); // Obtiene el Fragment que lo invocó
        //Verifica si la Activity implementa la interfaz
        if(parentFragment instanceof OnDireccionSeleccionadaListener){
            listener = (OnDireccionSeleccionadaListener) parentFragment;
        } else{
            throw new RuntimeException(parentFragment.toString() + " debe implementar OnDireccionSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetDireccionBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        recycler = binding.recyclerDireccionPago;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new DireccionPagoAdapter(listaDirecciones, direccion->{
            if(listener != null){
                listener.onDireccionSeleccionada(direccion);
                dismiss();
            }
        }));

        binding.btnNuevaDireccion.setOnClickListener(v->{
            dismiss();
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_pagoFragment_to_registrarDireccion);
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null; // Evita memory leaks
    }
}