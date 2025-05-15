package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentHistorialPedidosBinding;
import com.mol21.cliente_deliveryrice.databinding.FragmentHistorialPedidosRepartidorBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CheckoutViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.PedidoAdapter;
import com.mol21.cliente_deliveryrice.utils.SessionManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistorialPedidosRepartidorFragment extends Fragment {

    private FragmentHistorialPedidosRepartidorBinding binding;
    private CheckoutViewModel viewModel;
    @Inject
    SessionManager sessionManager;
    List<PedidoDTO> listaPedidos;

    public HistorialPedidosRepartidorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initViewModel();
        binding = FragmentHistorialPedidosRepartidorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        viewModel.obtenerPedidos(null,sessionManager.getUsuario().getId(),null)
                .observe(getViewLifecycleOwner(),response-> {
                    for(PedidoDTO pedido: response.getBody()){
                        Log.d("Pedido","pedido:"+pedido.getId());
                    }
                    Log.d("RESPONSE", "RESPONSE: "+response.getBody());
                    if (response.getRpta() != 1 || response.getBody().isEmpty()) {
                        binding.llNoCarrito.setVisibility(VISIBLE);
                        binding.rvPedidos.setVisibility(GONE);
                    } else {
                        binding.llNoCarrito.setVisibility(GONE);
                        binding.rvPedidos.setVisibility(VISIBLE);
                        listaPedidos = response.getBody();
                        for(PedidoDTO pedido: listaPedidos){
                            Log.d("Pedido","pedido:"+pedido.getId());
                        }
                        initRecyclerView();
                    }
                });
    }

    private void initRecyclerView() {
        binding.rvPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPedidos.setAdapter(new PedidoAdapter(listaPedidos,pedido->{

        }));

    }
    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
    }
}






























