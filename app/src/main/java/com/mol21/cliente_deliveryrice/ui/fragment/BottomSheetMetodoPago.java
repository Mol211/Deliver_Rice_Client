package com.mol21.cliente_deliveryrice.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mol21.cliente_deliveryrice.databinding.FragmentBottomSheetMetodoPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.ui.adapter.MetodoPagoAdapter;
import com.mol21.cliente_deliveryrice.ui.listener.OnItemSelectedListener;

import java.util.List;


public class BottomSheetMetodoPago extends BottomSheetDialogFragment {
    RecyclerView recycler;

    private final List<MetodoPago> listaMetodos;
    OnItemSelectedListener listener;
    private FragmentBottomSheetMetodoPagoBinding binding;

    public BottomSheetMetodoPago(List<MetodoPago> listaMetodos) {
        // Required empty public constructor
        this.listaMetodos = listaMetodos;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();

        if(parentFragment instanceof OnItemSelectedListener){
            listener = (OnItemSelectedListener) parentFragment;
        } else{
            throw new RuntimeException(parentFragment.toString() + " debe implementar OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetMetodoPagoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler = binding.recyclerMetodoPago;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new MetodoPagoAdapter(listaMetodos, metodo->{
            if(listener != null){
                listener.onMetodoSeleccionado(metodo);
                dismiss();
            }
        }));
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null; // Evita memory leaks
    }
}