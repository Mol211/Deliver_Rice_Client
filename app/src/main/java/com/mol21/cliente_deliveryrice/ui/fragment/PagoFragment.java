package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.DireccionViewModel;
import com.mol21.cliente_deliveryrice.ui.activity.CategoryActivity;
import com.mol21.cliente_deliveryrice.databinding.FragmentPagoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.MetodoPago;
import com.mol21.cliente_deliveryrice.ui.adapter.ItemPagoAdapter;
import com.mol21.cliente_deliveryrice.ui.listener.OnItemClickListener;
import com.mol21.cliente_deliveryrice.ui.listener.OnItemSelectedListener;
import com.mol21.cliente_deliveryrice.utils.SessionManager;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import java.util.ArrayList;
import java.util.List;


public class PagoFragment extends Fragment implements OnItemSelectedListener{
    private FragmentPagoBinding binding;
    private SessionManager sesion;


    //Atributos para manejar los recycler
    private boolean isExpanded;
    private List<ItemDTO> listaItems;
    private List<DireccionDTO> listaDirecciones;
    List<MetodoPago> metodoPagoList;


    //ViewModels
    private CarritoViewModel carritoViewModel;
    private DireccionViewModel direccionViewModel;

    private MetodoPago metodo;
    private DireccionDTO direccion;


    public PagoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViewModel();
        sesion = SessionManager.getInstance(getContext());
        isExpanded = false;
        listaItems = new ArrayList();
        listaDirecciones = new ArrayList<>();
        metodoPagoList = new ArrayList<>();
        return root;
    }

    private void initRecyclerView(List<ItemDTO> listaItems) {
        binding.rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvItems.setAdapter(new ItemPagoAdapter(listaItems));
        DividerItemDecoration divider = new DividerItemDecoration(binding.rvItems.getContext(),
                LinearLayoutManager.VERTICAL);
        binding.rvItems.addItemDecoration(divider);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Obtener la lista de Items del carrito
        carritoViewModel.listaItems(sesion.getIdCarrito()).observe(getViewLifecycleOwner(), response -> {
            listaItems.addAll(response.getBody());
            carritoViewModel.actualizarTotales(listaItems);
            initRecyclerView(listaItems);
        });
        carritoViewModel.getTotalProductos().observe(getViewLifecycleOwner(), total -> {
            binding.tvItemsPedido.setText("Tienes " + total + " productos en la cesta");
        });
        carritoViewModel.getTotalPrecio().observe(getViewLifecycleOwner(), precio -> {
            binding.tvResumenSubTotal.setText(precio + "€");
            binding.tvResumenTotal.setText(precio + "€");
        });


        //OnClickListener en el llayout para desplegar el recycler:
        binding.llTuPedido.setOnClickListener(v -> {
            isExpanded = !isExpanded;
            binding.btnItemsPedido.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
            binding.rvItems.setVisibility(isExpanded ? VISIBLE : GONE);
        });


        //Obtener Dirección Principal y mostrarla en el CardView
        direccionViewModel.obtenerPrincipal(sesion.getUsuario().getId()).
                observe(getViewLifecycleOwner(), response -> {
                    this.direccion = response.getBody();
                    Log.d("DIRECCIONPRINCIPAL", "direccionPrincipal: " +
                            direccion.getCalle() + " " + direccion.getNumero() + " " + direccion.getCodPostal());
                    binding.cvCalle.setText(direccion.getCalle() + ", " + direccion.getCodPostal());
                    binding.cvNumero.setText(direccion.getNumero());
                    binding.tvResumenDireccion.setText(direccion.getCalle() + ", " + direccion.getNumero() + ", " + direccion.getCodPostal());
                });

        //Obtener Direcciones
        direccionViewModel.obtenerDirecciones(sesion.getUsuario().getId())
                .observe(getViewLifecycleOwner(), response -> {
                    listaDirecciones.addAll(response.getBody());
                });

        //Mostrar BottomSheetDirecciones
        binding.cvDireccion.setOnClickListener(v -> {
            mostrarBottomSheetDirecciones(listaDirecciones);
            Toast.makeText(getContext(), "Se ha pulsado el CARDVIEW", Toast.LENGTH_SHORT).show();
        });

        //ListaMetodoPago
        metodoPagoList.add(MetodoPago.BIZUM);
        metodoPagoList.add(MetodoPago.TARJETA);
        metodoPagoList.add(MetodoPago.EFECTIVO);

        //Mostrar BottomSheetMetodoPago
        binding.btnMetodoPago.setOnClickListener(v -> {
            mostrarBottomSheetMetodoPago(metodoPagoList);
        });

        //Realizar Pedido
        binding.btnConfirmarPago.setOnClickListener(v->{
            if(metodo == null){
                toastError("Porfavor seleccione un método de pago");
            }
            else{
                crearPedido(sesion.getIdCarrito(), metodo, direccion.getId());
            }
        });
    }

    private void mostrarBottomSheetMetodoPago(List<MetodoPago> metodoPagoList) {
        BottomSheetMetodoPago dialog = new BottomSheetMetodoPago(metodoPagoList);
        dialog.show(getChildFragmentManager(), "MetodoPago");
    }

    private void mostrarBottomSheetDirecciones(List<DireccionDTO> listaDirecciones) {
        BottomSheetDireccion dialog = new BottomSheetDireccion(listaDirecciones);
        dialog.show(getChildFragmentManager(), "Hola");
    }

    @Override
    public void onDireccionSeleccionada(DireccionDTO d) {
        binding.cvCalle.setText(d.getCalle() + ", " + d.getCodPostal());
        binding.cvNumero.setText(d.getNumero());
        binding.tvResumenDireccion.setText(d.getCalle() + ", " + d.getNumero() + ", " + d.getCodPostal());
        this.direccion = d;
    }

    @Override
    public void onMetodoSeleccionado(MetodoPago metodo) {
        binding.tvResumenMetodoPago.setText(metodo.name());
        binding.tvCdMetodoPago.setText(metodo.name());
        this.metodo = metodo;
    }

    private void crearPedido(long idCarrito, MetodoPago metodo, long idDireccion) {
        carritoViewModel.procesarCarrito(idCarrito, metodo, idDireccion)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getRpta() == 1) {
                        Log.d("PAGO FRAGMENT ", "Carrito que se procesa: " + sesion.getIdCarrito());
                        carritoViewModel.obtenerCarrito(sesion.getUsuario().getId())
                                .observe(getViewLifecycleOwner(), respuesta -> {
                                    if (respuesta.getRpta() == 1) {
                                        sesion.nuevoCarrito(respuesta.getBody().getId());
                                        Log.d("NUEVO CARRITO CREADO", "NUEVO CARRITO: " + respuesta.getBody().getId());
                                        Log.d("NUEVO CARRITO CREADO", "MENSAJE DEL BACKEND: " + respuesta.getMessage());
                                        Toast.makeText(getContext(), "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), CategoryActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getContext(), "No se ha podido realizar el pedido", Toast.LENGTH_SHORT).show();
                                        Log.e("ERROR", "No se ha podido crear el nuevo carrito");
                                    }
                                });
                    } else if (response.getRpta() == 0) {
                        Log.d("PAGO FRAGMENT: WARNING", "no SE PUEDE CREAR EL PEDIDO: " + response.getMessage());
                        Toast.makeText(getContext(), "No se ha podido realizar el pedido", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initViewModel() {
        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
        direccionViewModel = new ViewModelProvider(requireActivity()).get(DireccionViewModel.class);
    }
    public void toastError(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, null);
        TextView txtMensaje = view.findViewById(R.id.txtMessageToastError);
        txtMensaje.setText(mensaje);
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


}