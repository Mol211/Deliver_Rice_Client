package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentPedidosBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.PedidoViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.PedidoAdapter;
import com.mol21.cliente_deliveryrice.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PedidosFragment extends Fragment {

    private FragmentPedidosBinding binding;
    private PedidoViewModel pedidoViewModel;
    private SessionManager sessionManager;
    List<PedidoDTO> listaPedidos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        initViewModel();
        sessionManager = SessionManager.getInstance(getContext());
        listaPedidos = new ArrayList<>();
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pedidoViewModel.obtenerListPedido(sessionManager.getUsuario().getId())
                .observe(getViewLifecycleOwner(),response->{
                    Log.d("LISTAPEDIDOS", "onViewCreated: "+response.getBody());
                    Log.d("LISTAPEDIDOS","isEmpty?"+response.getBody().isEmpty());
                    //Mostrar una pantalla u otra dependiendo de si hay pedidos o no
                    listaPedidos = response.getBody();
                    Log.i("LISTAPEDIDOS",listaPedidos.get(0).toString());
                    if(listaPedidos.isEmpty()) {
                        Log.d("LISTAPEDIDOS", "empty: "+listaPedidos);
                        binding.llNoPedido.setVisibility(VISIBLE);
                        binding.ndSiPedidos.setVisibility(GONE);
                        //OnClick para ir a realizar el primer pedido si detecta que no tiene pedidos.
                        binding.btnPrimerPedido.setOnClickListener(v->{
                            NavController navController = NavHostFragment.findNavController(this);
                            navController.navigate(R.id.action_nav_userPedidos_to_nav_home);
                        });
                    } else {
                        Log.d("LISTAPEDIDOS", "noEmpty: "+listaPedidos);
                        Log.d("TAG", "numero de productos del primer pedido: "+listaPedidos.get(0).getTotalProductos());
                        Log.d("TAG", "estado del primer pedido: "+listaPedidos.get(0).getEstadoPedido());
                        binding.llNoPedido.setVisibility(GONE);
                        binding.ndSiPedidos.setVisibility(VISIBLE);
                        initRecyclerView();
                        //OnClick para abrir el fragment de DetallePedido al hacer click
                    }
                });

    }

    private void initRecyclerView() {
        binding.rvPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPedidos.setAdapter(new PedidoAdapter(listaPedidos,pedido->{
            pedidoViewModel.guardarId(pedido.getId());
            Navigation.findNavController(getView()).navigate(R.id.action_nav_userPedidos_to_detallePedido);
        }));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initViewModel() {
        pedidoViewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
    }
}