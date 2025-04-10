package com.mol21.cliente_deliveryrice.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentBottomSheetEntregarPedidoBinding;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CheckoutViewModel;


public class BottomSheetEntregarPedido extends BottomSheetDialogFragment {
    private CheckoutViewModel viewModel;
    private Runnable onDismissBottom;
    private FragmentBottomSheetEntregarPedidoBinding binding;
    private long idUsuario;
    private long idPedido;

    public BottomSheetEntregarPedido(long idPedido, long idUsuario) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetEntregarPedidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViewModel();
        return root;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get( CheckoutViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnEntregarPedido.setOnClickListener(v->{
            viewModel.cambiarEstado(idPedido,"completar",idUsuario).
                    observe(getViewLifecycleOwner(),response->{
                if(response.getRpta()==1) toastOk("El pedido se ha entregado correctamente");
                else toastError("No se ha podido entregar el pedido");
                if(onDismissBottom != null){
                    onDismissBottom.run();
                }
                dismiss();
            });
        });
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

    public void toastError(String mensaje) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, getActivity().findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMessageToastError);
        txtMensaje.setText(mensaje);
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void setOnDismissBottom(Runnable onDismissBottom) {
        this.onDismissBottom = onDismissBottom;
    }
}