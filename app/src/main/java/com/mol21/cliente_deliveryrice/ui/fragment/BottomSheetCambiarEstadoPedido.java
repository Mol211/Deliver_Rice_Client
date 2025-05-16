package com.mol21.cliente_deliveryrice.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentBottomSheetCambiarEstadoPedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.PedidoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.EstadoPedido;
import com.mol21.cliente_deliveryrice.mvvm.model.Pedido;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CheckoutViewModel;

public class BottomSheetCambiarEstadoPedido extends BottomSheetDialogFragment {
    private PedidoDTO pedidoDTO;
    private FragmentBottomSheetCambiarEstadoPedidoBinding binding;
    private CheckoutViewModel checkoutViewModel;
    private UsuarioDTO repartidor;
    private String accion="";
    private Runnable onDismissButton;


    public BottomSheetCambiarEstadoPedido(PedidoDTO pedido) {
        this.pedidoDTO = pedido;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetCambiarEstadoPedidoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        initViewModel();
        return root;
    }

    private void initViewModel() {
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EstadoPedido estadoPedido = pedidoDTO.getEstadoPedido();
        switch (estadoPedido) {
            case PENDIENTE:
                binding.txtCardView.setText("Empezar a preparar el pedido");
                accion = "preparar";
                break;
            case EN_PROCESO:
                binding.txtCardView.setText("Enviar el pedido");
                accion = "enviar";
                break;
            case ENVIADO:
                binding.txtCardView.setText("Marcar el pedido como entregado");
                accion = "completar";
                break;
        }
        checkoutViewModel.obtenerRepartidor()
                .observe(getViewLifecycleOwner(),respuesta ->{
                    if(respuesta.getRpta()==1){
                        repartidor = respuesta.getBody();
                    }
                    Log.d("Repartidor obtenido en el bottomSheet", "repartidorID: "+repartidor.getId());
                });

        binding.btnCambiarEstado.setOnClickListener(v->{
            checkoutViewModel.cambiarEstado(pedidoDTO.getId(),accion,repartidor.getId())
                    .observe(getViewLifecycleOwner(),respuesta->{
                        Log.d("Respuesta al cambiar estado", respuesta.getMessage());
                        switch (accion){
                            case "enviar":
                                toastOk("Se ha enviado el pedido");
                                break;
                            case "completar":
                                toastOk("Se ha entregado el pedido");
                                break;
                            case "preparar":
                                toastOk("El pedido se está preparando");
                                break;
                        }
                        if(onDismissButton != null){
                            onDismissButton.run();
                        }
                        dismiss();
                    });
        });
    }
    public void setOnDismissBottom(Runnable onDismissBottom) {
        this.onDismissButton = onDismissBottom;
    }
    public void toastOk(String mensaje) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, getActivity().findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMessageToastOK);
        txtMensaje.setText(mensaje);
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

}