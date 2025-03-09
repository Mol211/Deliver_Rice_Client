package com.mol21.cliente_deliveryrice.ui.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentCarritoBinding;

public class CarritoFragment extends Fragment {
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
        return root;
    }
}