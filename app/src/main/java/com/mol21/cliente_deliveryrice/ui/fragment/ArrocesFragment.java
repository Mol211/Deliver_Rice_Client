package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentArrocesBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.ui.adapter.ProductoAdapter;
import com.mol21.cliente_deliveryrice.utils.SessionManager;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.ProductoViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArrocesFragment extends Fragment {
    private CategoriaProducto categoriaFragment;
    private FragmentArrocesBinding binding;
    private ProductoViewModel productoViewModel;
    private CarritoViewModel carritoViewModel;
    private ProductoAdapter productoAdapter;
    @Inject
    SessionManager sessionManager;
    private long carritoId;

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
        init();
        initRecyclerView();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carritoViewModel.getTotalProductos().observe(getViewLifecycleOwner(),total->{
            if(total>0) {
                Log.d("TotalEnViewModel", "onViewCreated: "+total);
                binding.btnIrCesta.setVisibility(VISIBLE);
            } else{
                Log.d("TotalEnViewModel", "onViewCreated: "+total);
                binding.btnIrCesta.setVisibility(GONE);
            }
        });
        carritoViewModel.getTotalPrecio().observe(getViewLifecycleOwner(), precio->{
            Log.d("Precio Total en ViEWMODEL", "onViewCreated: "+ precio);
            if(precio!=null){
                binding.btnIrCesta.setText("Ir a la cesta · "+precio+"€");}
        });
        //Boton para ir a la cesta
        //Se configura para que no permita volver al fragment actual, sino al principal.
        binding.btnIrCesta.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_arrocesFragment_to_carritoFragment,
                    null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.nav_arrocesFragment,true)
                            .build());
        });
    }

    private void init() {
        carritoId = sessionManager.getIdCarrito();
        if(categoriaFragment != null){
            switch (categoriaFragment){
                case ARROZ:
                    binding.tvTittulo.setText("Arroces");
                    break;
                case ENTRANTE:
                    binding.tvTittulo.setText("Entrantes");
                    break;
                case POSTRE:
                    binding.tvTittulo.setText("Postres");
                    break;
            }
        }

    }

    private void initRecyclerView() {
        productoAdapter = new ProductoAdapter(getViewLifecycleOwner(),new ArrayList<>(), carritoViewModel, carritoId);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        llManager.setSmoothScrollbarEnabled(true);
        binding.rvArroces.setLayoutManager(llManager);
        binding.rvArroces.setAdapter(productoAdapter);
    }

    private void initViewModel(CategoriaProducto categoria) {
        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
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