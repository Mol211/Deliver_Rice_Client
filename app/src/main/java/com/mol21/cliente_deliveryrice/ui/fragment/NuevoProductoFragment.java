package com.mol21.cliente_deliveryrice.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.FragmentNuevoProductoBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.CategoriaProducto;
import com.mol21.cliente_deliveryrice.mvvm.model.Producto;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.ProductoViewModel;

import java.math.BigDecimal;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NuevoProductoFragment extends Fragment {

    private ProductoViewModel productoViewModel;
    private FragmentNuevoProductoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevoProductoBinding.inflate(inflater, container, false);
        initViewModel();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnRegRegistrarr.setOnClickListener(v->{

            if(validar()){
                String nombre = binding.etRegNombre.getText().toString();
                String descripcion = binding.etRegDescripcion.getText().toString();
                String url = binding.etRegUrl.getText().toString();
                int stock = Integer.parseInt(binding.etRegStock.getText().toString());
                BigDecimal precio = BigDecimal.valueOf(Double.parseDouble(binding.etRegPrecio.getText().toString()));
                CategoriaProducto categoriaProducto = CategoriaProducto.valueOf(binding.etCategoriaProducto.getText().toString());

                Producto p = new Producto();
                p.setNombre(nombre);
                p.setCategoriaProducto(categoriaProducto);
                p.setDescripcion(descripcion);
                p.setImagenUrl(url);
                p.setPrecio(precio);
                p.setStock(stock);

                productoViewModel.crearProducto(p).observe(getViewLifecycleOwner(),respuesta->{
                    if(respuesta.getRpta()==1){
                        toastCorrecto("Se ha creado un nuevo producto");
                        NavController navController = Navigation.findNavController(v);
                        navController.navigateUp();
                    } else {
                        toastError("Error: No se puede crear el Producto");
                    }
                });
            }
            });


        binding.btnRegBack.setOnClickListener(v->{
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();
        });
        binding.etRegNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegNombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegDescripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegDescripcion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegUrl.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etCategoriaProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegCategoria.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegStock.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegPrecio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegPrecio.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validar() {
        boolean esValido = false;
        String nombre = binding.etRegNombre.getText().toString();
        String descripcion = binding.etRegDescripcion.getText().toString();
        String url = binding.etRegUrl.getText().toString();
        String stock = binding.etRegStock.getText().toString();
        String precio = binding.etRegPrecio.getText().toString();
        String categoriaProducto = binding.etCategoriaProducto.getText().toString();
        if(nombre.isEmpty()){
            esValido = false;
            binding.etRegNombre.requestFocus();
            binding.txtRegNombre.setError("Ingrese un nombre válido");
        }
        if(descripcion.isEmpty()){
            esValido = false;
            binding.etRegDescripcion.requestFocus();
            binding.txtRegDescripcion.setError("Ingrese una descripción");
        }
        if(url.isEmpty()){
            esValido = false;
            binding.etRegUrl.requestFocus();
            binding.txtRegUrl.setError("Ingrese una URL válida");
        }
        if(stock.isEmpty()){
            esValido = false;
            binding.etRegStock.requestFocus();
            binding.txtRegStock.setError("Introduzca un Stock positivo");
        }
        if(categoriaProducto.isEmpty()){
            esValido = false;
            binding.etCategoriaProducto.requestFocus();
            binding.txtRegCategoria.setError("Debe seleccionar una categoría");
        }
        if(precio.isEmpty()){
            esValido = false;
            binding.etRegPrecio.requestFocus();
            binding.txtRegPrecio.setError("Ingrese un precio válido");
        }
        else esValido = true;
        return esValido;
    }

    private void initViewModel() {
        productoViewModel = new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
    }
    public void toastCorrecto(String mensaje) {
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
}