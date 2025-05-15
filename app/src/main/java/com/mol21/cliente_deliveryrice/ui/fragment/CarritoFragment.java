package com.mol21.cliente_deliveryrice.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentCarritoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.CarritoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.ItemDTO;
import com.mol21.cliente_deliveryrice.ui.adapter.ItemCarritoAdapter;
import com.mol21.cliente_deliveryrice.utils.SessionManager;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CarritoFragment extends Fragment {
    @Inject
    SessionManager sesionManager;
    private CarritoViewModel viewModel;
    private ItemCarritoAdapter adapter;
    FragmentCarritoBinding binding;
    public CarritoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCarritoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //Obtenemos el ViewModel ya declarado en la actividad
        initViewModel();
        initRecyclerView();
        binding.btnIrPago.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_carritoFragment_to_pagoFragment,null);
        });
        return root;

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Metodo para
        obtenerCarrito();
        viewModel.getTotalProductos().observe(getViewLifecycleOwner(), total->{
            if(total<=0){
                binding.llNoCarrito.setVisibility(VISIBLE);
                binding.nsHayProductos.setVisibility(GONE);
            }else{
                binding.llNoCarrito.setVisibility(GONE);
                binding.nsHayProductos.setVisibility(VISIBLE);
                binding.txtNumeroProductos.setText(total+" productos en tu cesta");
            }

        });
        viewModel.getTotalPrecio().observe(getViewLifecycleOwner(), precio->{
            if(precio!=null){
            binding.btnIrPago.setText("Ir al pago · "+precio+"€");}
        });
        //Funcionalidad para vaciar la cesta del carrito.
        binding.btnVaciarCesta.setOnClickListener(v->{
            Log.d("CLICK EN CESTA","Se ha hecho click en cesta");
            vaciarCesta();
        });
        binding.tvIdCarrito.setText("El id del carrito es: "+ sesionManager.getIdCarrito());
    }

    private void vaciarCesta() {
        Log.d("METODO VACIAR CESTA","Esta dentro");
        new AlertDialog.Builder(getContext())
                .setTitle("Confirmación")
                .setMessage("¿Seguro que desesa eliminar todos los productos?")
                .setPositiveButton("Sí", (dialog, wich) ->
                        viewModel.vaciarCarrito(sesionManager.getUsuario().getId()).observe(getViewLifecycleOwner(), response->{
                            if(response.getRpta()==1){
                                Log.d("METODO VACIAR CARRITO","Respuesta OK");
                                viewModel.actualizarTotales(Collections.emptyList());
                                obtenerCarrito();
                            }
                            else{
                                Log.e("ERROR", response.getMessage() );
                            }
                        }))
                .setNegativeButton("No", (dialog, wich) -> dialog.dismiss())
                .show();

    }

    private void obtenerCarrito() {
        viewModel.obtenerCarrito(sesionManager.getUsuario().getId())
                .observe(getViewLifecycleOwner(), response->{
            if (response.getRpta()==1){
                CarritoDTO carrito = response.getBody();
                List<ItemDTO> listaItems = carrito.getListaItems();
                if(!listaItems.isEmpty()){
                    Log.d("ListaItems: ",listaItems.get(0).getNombreProducto());
                    binding.nsHayProductos.setVisibility(VISIBLE);
                    binding.llNoCarrito.setVisibility(GONE);
                    binding.txtNumeroProductos.setText(carrito.getTotalProductos() +" productos en tu cesta");
                    binding.btnIrPago.setText("Ir al pago · "+carrito.getPrecioCarrito()+"€");
                    adapter.updateList(listaItems);
                }else{
                    binding.llNoCarrito.setVisibility(VISIBLE);
                    binding.nsHayProductos.setVisibility(GONE);
                    Log.d("2 RESPUESTA | CODE",response.getMessage()+"|"+response.getRpta());
                }
            }else{
                Log.d("3 RESPUESTA",response.getMessage());
                binding.llNoCarrito.setVisibility(VISIBLE);
                binding.nsHayProductos.setVisibility(GONE);
            }
        });
    }

    private void initRecyclerView() {
        ArrayList<ItemDTO>listaVacia = new ArrayList<>();
        adapter = new ItemCarritoAdapter(listaVacia, viewModel, getViewLifecycleOwner());
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        llManager.setSmoothScrollbarEnabled(true);
        binding.rvItemsCarrito.setLayoutManager(llManager);
        binding.rvItemsCarrito.setAdapter(adapter);
    }

    //Obtenemos el ViewModel ya declarado en la actividad


}