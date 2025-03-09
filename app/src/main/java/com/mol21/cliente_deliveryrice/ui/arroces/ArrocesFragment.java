package com.mol21.cliente_deliveryrice.ui.arroces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityInicioBinding;
import com.mol21.cliente_deliveryrice.databinding.ActivityLoginBinding;
import com.mol21.cliente_deliveryrice.databinding.FragmentArrocesBinding;
import com.mol21.cliente_deliveryrice.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.model.DTO.ProductoDTO;
import com.mol21.cliente_deliveryrice.ui.adapter.ProductoAdapter;
import com.mol21.cliente_deliveryrice.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArrocesFragment extends Fragment {
    private CategoriaProducto categoriaFragment;
    private FragmentArrocesBinding binding;
    private ProductoViewModel productoViewModel;
    private ProductoAdapter productoAdapter;

    public ArrocesFragment() {
    }
    public static ArrocesFragment newInstance(CategoriaProducto categoria){
        ArrocesFragment fragment = new ArrocesFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoria",categoria);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArrocesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(getArguments() != null) {
            categoriaFragment = (CategoriaProducto) getArguments().getSerializable("categoria");
        }
        initViewModel(categoriaFragment);
        initRecyclerView();
        init();
        return root;
    }

    private void init() {
        if(categoriaFragment != null){
            switch (categoriaFragment){
                case ARROZ:
                    binding.tvTittulo.setText("Lista de Arroces");
                    break;
                case ENTRANTE:
                    binding.tvTittulo.setText("Lista de Entrantes");
                    break;
                case POSTRE:
                    binding.tvTittulo.setText("Lista de Postres");
                    break;
            }
        }

    }

    private void initRecyclerView() {
        productoAdapter = new ProductoAdapter(new ArrayList<>());
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        llManager.setSmoothScrollbarEnabled(true);
        binding.rvArroces.setLayoutManager(llManager);
        binding.rvArroces.setAdapter(productoAdapter);
    }

    private void initViewModel(CategoriaProducto categoria) {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        productoViewModel.obtenerProductos(categoria)
                .observe(getViewLifecycleOwner(), respuesta->{
                    if(respuesta.getRpta()==1){
                        productoAdapter.updateList(respuesta.getBody());
                    } else if(respuesta.getRpta()==0){
                        Toast.makeText(getContext(),respuesta.getMessage(),Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getContext(), "El servidor no se encuentra disponible", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

}