package com.mol21.cliente_deliveryrice.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentDireccionesBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.DireccionViewModel;
import com.mol21.cliente_deliveryrice.ui.adapter.DireccionAdapter;
import com.mol21.cliente_deliveryrice.ui.listener.OnDireccionClickListener;
import com.mol21.cliente_deliveryrice.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class DireccionesFragment extends Fragment{

    FragmentDireccionesBinding binding;
    DireccionViewModel viewModel;
    SessionManager sesion;
    private DireccionAdapter adapter;
    public DireccionesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDireccionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sesion = SessionManager.getInstance(getContext());
        initViewModel();
        initRecyclerView();
        return root;
    }

    private void initRecyclerView() {
        ArrayList<DireccionDTO> lista = new ArrayList<>();
        adapter = new DireccionAdapter(lista, new OnDireccionClickListener() {
            @Override
            public void onEditarClick(DireccionDTO direccion) {
                NavController navController = NavHostFragment.findNavController(DireccionesFragment.this);
                Bundle bundle = new Bundle();
                bundle.putString("modo","editar");
                bundle.putParcelable("direccion", direccion);
                navController.navigate(R.id.action_direccionesFragment_to_registrarDireccion, bundle);
            }

            @Override
            public void onEliminarClick(DireccionDTO direccion) {
                mostrarDialogoEliminar(direccion);
                mostrarDirecciones();
            }

            @Override
            public void onMarcarComoPrincipalClick(DireccionDTO direccion) {
                viewModel.hacerPrincipal(direccion.getId()).observe(getViewLifecycleOwner(),response->{
                    if(response.getRpta()==1){
                        mostrarDirecciones();
                    }else{
                        Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.rvDirecciones.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDirecciones.setAdapter(adapter);
    }

    private void mostrarDialogoEliminar(DireccionDTO direccion) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirmación")
                .setMessage("¿Seguro que desea eliminar la dirección seleccionada?")
                .setPositiveButton("Sí", (dialog, which) -> viewModel.desactivarDireccion(direccion.getId())
                        .observe(getViewLifecycleOwner(),response->{
                            Log.d("DIRECCIONID", "DIRECCIONID: "+direccion.getId());
                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                            mostrarDirecciones();
                        })) // Cierra la actividad
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo
                .show();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(DireccionViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostrarDirecciones();
        binding.btnCrearDireccion.setOnClickListener(v->{
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_direccionesFragment_to_registrarDireccion);
        });

    }

    private void mostrarDirecciones() {
        viewModel.obtenerDirecciones(sesion.getUsuario().getId()).observe(getViewLifecycleOwner(),response->{
            List<DireccionDTO> nuevaLista = response.getBody();
            adapter.updateList(nuevaLista);
        });
    }
}