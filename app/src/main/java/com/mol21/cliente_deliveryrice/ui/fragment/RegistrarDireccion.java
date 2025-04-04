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
import com.mol21.cliente_deliveryrice.databinding.FragmentRegistrarDireccionBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.DireccionDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.DireccionViewModel;
import com.mol21.cliente_deliveryrice.utils.SessionManager;


public class RegistrarDireccion extends Fragment {
    FragmentRegistrarDireccionBinding binding;
    DireccionViewModel viewModel;
    SessionManager session;


    public RegistrarDireccion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrarDireccionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initViewModel();
        session = SessionManager.getInstance(getContext());
        return root;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(DireccionViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            DireccionDTO direccion = getArguments().getParcelable("direccion");
            if(direccion!=null){
                binding.tvTitleNew.setText(R.string.titulo_edit_direction);
                binding.etRegCiudad.setText(direccion.getCiudad());
                binding.etRegCalle.setText(direccion.getCalle());
                binding.etRegNumero.setText(direccion.getNumero());
                binding.etRegCodPostal.setText(direccion.getCodPostal());
                if(direccion.isEsPrincipal()) binding.cboxDireccionPredeterm.setChecked(true);
                binding.btnRegistrar.setOnClickListener(v->{
                    String codPostal = binding.etRegCodPostal.getText().toString();
                    String calle = binding.etRegCalle.getText().toString();
                    String numero = binding.etRegNumero.getText().toString();
                    String ciudad = binding.etRegCiudad.getText().toString();
                    boolean esPrincipal = binding.cboxDireccionPredeterm.isChecked() ? true : false;
                    if(validar()) {
                        Direccion d = new Direccion();
                        d.setDireccion_id(direccion.getId());
                        Log.d("DIRECCIONID", "id_direccion: "+direccion.getId());
                        d.setCodPostal(codPostal);
                        d.setNumero(numero);
                        d.setCalle(calle);
                        d.setCiudad(ciudad);
                        d.setEsPrincipal(esPrincipal);
                        Log.d("DIRECCION", "direccion: " + d.toString());

                        viewModel.actualizarDireccion(d).observe(getViewLifecycleOwner(),response->{
                            if (response.getRpta() == 1) {
                                toastCorrecto("Se ha actualiado la dirección");
                                NavController navController = Navigation.findNavController(v);
                                navController.navigateUp();
                            } else if (response.getRpta()==0) {
                                Log.d("WARNING!!", "onViewCreated: "+response.getMessage());

                            } else {
                                Log.d("ERROR AL EDITAR DIRECCION", "onViewCreated: "+ response.getMessage());
                                toastError("No se ha podido guardar la dirección");
                            }
                        });

                    }

                });
            }
            else{
                binding.tvTitleNew.setText(R.string.titulo_new_direction);
                binding.btnRegistrar.setOnClickListener(v -> {
                    String codPostal = binding.etRegCodPostal.getText().toString();
                    String calle = binding.etRegCalle.getText().toString();
                    String numero = binding.etRegNumero.getText().toString();
                    String ciudad = binding.etRegCiudad.getText().toString();
                    boolean esPrincipal = binding.cboxDireccionPredeterm.isChecked() ? true : false;
                    Log.d("ESVALIDO", "esvalido: "+ validar());
                    if(validar()){
                        Direccion d = new Direccion();
                        d.setCodPostal(codPostal);
                        d.setNumero(numero);
                        d.setCalle(calle);
                        d.setCiudad(ciudad);
                        d.setEsPrincipal(esPrincipal);
                        Log.d("DIRECCION", "direccion: "+d.toString());
                        viewModel.guardarDireccion(session.getUsuario().getId(), d).observe(getViewLifecycleOwner(), response -> {
                            if (response.getRpta() == 1) {
                                toastCorrecto("Se ha guardado la nueva dirección");
                                NavController navController = Navigation.findNavController(v);
                                navController.navigateUp();
                            } else {
                                Log.d("ERROR AL EDITAR DIRECCION", "onViewCreated: "+ response.getMessage());
                                toastError("No se ha podido guardar la dirección");
                            }
                        });
                    }
                });
            }
        }
        binding.btnBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();
        });
        binding.etRegNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegNumero.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegCalle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegCalle.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegCiudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegCiudad.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etRegCodPostal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.txtRegCodPostal.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    public boolean validar() {
        boolean esValido = false;
        String codPostal = binding.etRegCodPostal.getText().toString();
        String calle = binding.etRegCalle.getText().toString();
        String numero = binding.etRegNumero.getText().toString();
        String ciudad = binding.etRegCiudad.getText().toString();
        if (codPostal.isEmpty()) {
            esValido = false;
            binding.etRegCodPostal.requestFocus();
            binding.txtRegCodPostal.setError("Ingrese un código postal válido");
        }
        if (ciudad.isEmpty()) {
            esValido = false;
            binding.etRegCiudad.requestFocus();
            binding.txtRegCiudad.setError("Ingrese una ciudad válida");
        }
        if (calle.isEmpty()) {
            esValido = false;
            binding.etRegCalle.requestFocus();
            binding.txtRegCalle.setError("Ingrese una calle válida");
        }
        if (numero.isEmpty()) {
            esValido = false;
            binding.etRegNumero.requestFocus();
            binding.txtRegNumero.setError("Ingrese un número válido");
        }
        else{
            esValido = true;
        }
        return esValido;
    }

}