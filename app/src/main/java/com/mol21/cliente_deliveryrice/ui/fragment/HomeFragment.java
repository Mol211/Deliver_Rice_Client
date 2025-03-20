package com.mol21.cliente_deliveryrice.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentHomeBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //Configurar el botón para navegar a ArrocesFragment
        binding.btnArroces.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("categoria", CategoriaProducto.ARROZ);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_arrocesFragment, bundle);
        });
        binding.btnEntrantes.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("categoria", CategoriaProducto.ENTRANTE);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_arrocesFragment, bundle);
        });
        binding.btnPostres.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("categoria", CategoriaProducto.POSTRE);
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_arrocesFragment, bundle);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}