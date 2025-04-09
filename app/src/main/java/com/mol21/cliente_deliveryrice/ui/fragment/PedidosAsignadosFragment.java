package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mol21.cliente_deliveryrice.databinding.FragmentPedidosAsignadosBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CheckoutViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.PedidoAdapter;
import com.mol21.cliente_deliveryrice.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class PedidosAsignadosFragment extends Fragment {

    private FragmentPedidosAsignadosBinding binding;
    private SessionManager sessionManager;
    private CheckoutViewModel checkoutViewModel;
    List<PedidoDTO> listaPedidos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPedidosAsignadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sessionManager = SessionManager.getInstance(getContext());
        listaPedidos = new ArrayList<>();
        initViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkoutViewModel.obtenerPedidos( null,sessionManager.getUsuario().getId(), EstadoPedido.ENVIADO)
                .observe(getViewLifecycleOwner(), response->{
                    if(response.getRpta()!=1){
                        binding.llNoCarrito.setVisibility(VISIBLE);
                        binding.ndSiPedidos.setVisibility(GONE);

                    } else{
                        listaPedidos = response.getBody();
                        binding.llNoCarrito.setVisibility(GONE);
                        binding.ndSiPedidos.setVisibility(VISIBLE);
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
        checkoutViewModel = new ViewModelProvider(getActivity()).get(CheckoutViewModel.class);
    }
}






















