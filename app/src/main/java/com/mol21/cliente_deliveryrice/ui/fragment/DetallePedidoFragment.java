package com.mol21.cliente_deliveryrice.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mol21.cliente_deliveryrice.databinding.FragmentDetallePedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.PedidoViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.DetallePedidoAdapter;

import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DetallePedidoFragment extends Fragment {
    FragmentDetallePedidoBinding binding;
    PedidoViewModel viewModel;
    PedidoDTO pedido;

    public DetallePedidoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetallePedidoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        initViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.obtenerPedido(viewModel.getIdPedido().getValue()).observe(getViewLifecycleOwner(), response->{
            pedido = response.getBody();

            //Pintamos datos del pedido por pantalla
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("EEEE, d/MMMM/yyyy", new Locale("es", "ES"));
            binding.tvFechaDetalle.setText(pedido.getFechaCreacion().format(formatter));

            switch(pedido.getEstadoPedido()){
                case ENVIADO:
                    binding.ivEstadoPedido.setText("Enviado");
                    break;
                case EN_PROCESO:
                    binding.ivEstadoPedido.setText("En proceso");
                    break;
                case ENTREGADO:
                    binding.ivEstadoPedido.setText("Completado");
                    break;
                case CANCELADO:
                    binding.ivEstadoPedido.setText("Cancelado");
                    break;
                case PENDIENTE:
                    binding.ivEstadoPedido.setText("Pendiente");
                    break;
            }

            binding.tvCant.setText(pedido.getTotalProductos()+" productos");

            binding.cvCalle.setText(pedido.getDireccion().getCalle()+", "+pedido.getDireccion().getCodPostal());
            binding.cvNumero.setText(pedido.getDireccion().getNumero());

            binding.tvResumenSubTotal.setText(pedido.getPrecioTotal()+"€");
            binding.tvResumenTotal.setText(pedido.getPrecioTotal()+"€");

            initRecyclerView();
        });



    }

    private void initRecyclerView() {
        binding.rvDetallePedido.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDetallePedido.setAdapter(new DetallePedidoAdapter(pedido.getListaDetalles()));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(PedidoViewModel.class);
    }
}