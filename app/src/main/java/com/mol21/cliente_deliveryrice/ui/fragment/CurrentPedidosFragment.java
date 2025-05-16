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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mol21.cliente_deliveryrice.databinding.FragmentCurrentPedidosBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CheckoutViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.PedidoAdapter;
import com.mol21.cliente_deliveryrice.utils.GenericResponse;
import com.mol21.cliente_deliveryrice.utils.SessionManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CurrentPedidosFragment extends Fragment {

    private FragmentCurrentPedidosBinding binding;
    private CheckoutViewModel checkoutViewModel;

    @Inject
    SessionManager sessionManager;
    List<PedidoDTO> listaPedidos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurrentPedidosBinding.inflate(inflater, container, false);
        initViewModel();
        listaPedidos = new ArrayList<>();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        listaPedidos.clear();
        List<LiveData<GenericResponse<List<PedidoDTO>>>>listaDeListas = checkoutViewModel.obtenerPedidosEnProceso();
        for(LiveData<GenericResponse<List<PedidoDTO>>> liveData : listaDeListas) {
            liveData.removeObservers(getViewLifecycleOwner());
            liveData.observe(getViewLifecycleOwner(),response->{
                for(PedidoDTO pedido: listaPedidos){
                        Log.d("Pedido","pedido:"+pedido.getId());
                    }
                Log.d("RESPONSE","response:"+response.getRpta());

                if(response.getRpta()==1){
                    listaPedidos.addAll(response.getBody());
                }
//                Log.d("EL primer pedido es: ","EL primer pedido es: "+listaPedidos.get(0).getId());
                if(listaPedidos.isEmpty()){
                    binding.llNoPedido.setVisibility(VISIBLE);
                    binding.ndSiPedidos.setVisibility(GONE);
                } else {
                    binding.llNoPedido.setVisibility(GONE);
                    binding.ndSiPedidos.setVisibility(VISIBLE);
                    initRecyclerView();
                }
            });
        }

    }

    private void initRecyclerView() {
        Map<EstadoPedido,Integer> ordenEstado = Map.of(
                EstadoPedido.PENDIENTE,1,
                EstadoPedido.EN_PROCESO,2,
                EstadoPedido.ENVIADO,3
        );
        List<PedidoDTO>listaOrdenada = listaPedidos.stream()
                        .sorted(Comparator.comparing(p-> ordenEstado.get(p.getEstadoPedido())))
                                .collect(Collectors.toList());
        binding.rvCurrentPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCurrentPedidos.setAdapter(new PedidoAdapter(listaOrdenada, pedido->{
            mostrarBottomSheetCambiarEstadoPedido(pedido);
    }));
    }

    private void mostrarBottomSheetCambiarEstadoPedido(PedidoDTO pedido) {
        BottomSheetCambiarEstadoPedido dialog = new BottomSheetCambiarEstadoPedido(pedido);
        dialog.setOnDismissBottom(()->{
            init();
        });
        dialog.show(getChildFragmentManager(), "Cambiar Estado Pedido");

    }

    private void initViewModel() {
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}