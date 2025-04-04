package com.mol21.cliente_deliveryrice.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityRegistrarBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.RegistrarUsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Direccion;
import com.mol21.cliente_deliveryrice.mvvm.model.Usuario;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.UsuarioViewModel;

import java.time.LocalDateTime;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegistrarActivity extends AppCompatActivity {
    TextInputLayout txtEmail, txtPass, txtNombre, txtApellido, txtTfno, txtCodPostal, txtCiudad, txtCalle, txtNumero;
    TextInputEditText etEmail, etPass, etNombre, etApellido, etTfno, etCodPostal, etCiudad, etCalle, etNumero;
    Button btnRegistrar, btnBack;
    ActivityRegistrarBinding binding;
    private UsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegistrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.init();
        this.initViewModel();
    }
    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init() {
        binding.btnRegRegistrarr.setOnClickListener(view -> {
            String email = binding.etRegEmail.getText().toString();
            String pass = binding.etRegPass.getText().toString();
            String nombre = binding.etRegNombre.getText().toString();
            String apellido = binding.etRegApellidos.getText().toString();
            String tfno = binding.etRegTfno.getText().toString();
            String codPostal = binding.etRegCodPostal.getText().toString();
            String ciudad = binding.etRegCiudad.getText().toString();
            String numero = binding.etRegNumero.getText().toString();
            String calle = binding.etRegCalle.getText().toString();
            try{
                if(validar()){
                    Usuario u = new Usuario();
                    u.setEmail(email);
                    u.setApellido(apellido);
                    u.setNombre(nombre);
                    u.setPassword(pass);
                    u.setFechaCreacion(LocalDateTime.now());
                    u.setTelefono(tfno);

                    Direccion d = new Direccion();
                    d.setCalle(calle);
                    d.setCiudad(ciudad);
                    d.setCodPostal(codPostal);
                    d.setNumero(numero);
                    d.setEsPrincipal(true);
                    d.setFechaCreacion(LocalDateTime.now());
                    d.setUsuario(u);

                    RegistrarUsuarioDTO regUsuarioDTO = new RegistrarUsuarioDTO(u,d);
                    viewModel.registrarCliente(regUsuarioDTO)
                            .observe(this, response -> {
                                if(response.getRpta() == 1){
                                    toastCorrecto(response.getMessage());
                                    UsuarioDTO uDTO = response.getBody();
                                    this.finish();
//                                    etEmail.setText("");
//                                    etPass.setText("");
//                                    etNombre.setText("");
//                                    etApellido.setText("");
//                                    etNumero.setText("");
//                                    etCiudad.setText("");
//                                    etTfno.setText("");
//                                    etCodPostal.setText("");
//                                    etCalle.setText("");
                                } else if(response.getRpta() == -1){
                                    toastError(response.getMessage());
                                } else{
                                    toastError(response.getMessage());
                                }
                            });
                }
                else {
                    toastError("Por favor complete todos los campos");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            binding.etRegPass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.txtRegPass.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            binding.etRegEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.txtRegEmail.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
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
            binding.etRegApellidos.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.txtRegApellidos.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            binding.etRegTfno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.txtRegApellidos.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
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
        });
        binding.btnRegBack.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });

    }
    public boolean validar(){
        boolean esValido = false;
        String email = binding.etRegEmail.getText().toString();
        String pass = binding.etRegPass.getText().toString();
        String nombre = binding.etRegNombre.getText().toString();
        String apellido = binding.etRegApellidos.getText().toString();
        String tfno = binding.etRegTfno.getText().toString();
        String codPostal = binding.etRegCodPostal.getText().toString();
        String ciudad = binding.etRegCiudad.getText().toString();
        String numero = binding.etRegNumero.getText().toString();
        String calle = binding.etRegCalle.getText().toString();
        if(email.isEmpty()){
            esValido = false;
            binding.etRegEmail.requestFocus();
            binding.txtRegEmail.setError("Ingrese un correo electrónico válido");
        }
        else{
            binding.txtRegEmail.setErrorEnabled(false);
            esValido = true;
        }
        if(pass.isEmpty()){
            esValido = false;
            binding.etRegPass.requestFocus();
            binding.txtRegPass.setError("Ingrese una contraseña válida");
        }
        else{
            binding.txtRegPass.setErrorEnabled(false);
            esValido = true;
        }
        if(nombre.isEmpty()){
            esValido = false;
            binding.etRegNombre.requestFocus();
            binding.txtRegNombre.setError("Ingrese un nombre válido");
        }
        else{
            binding.txtRegNombre.setErrorEnabled(false);
            esValido = true;
        }
        if(apellido.isEmpty()){
            esValido = false;
            binding.etRegApellidos.requestFocus();
            binding.txtRegApellidos.setError("Ingrese un apellido");
        }
        else{
            binding.txtRegApellidos.setErrorEnabled(false);
            esValido = true;
        }
        if(tfno.isEmpty()){
            esValido = false;
            binding.etRegTfno.requestFocus();
            binding.txtRegTfno.setError("Ingrese un número de teléfono");
        }
        else{
            binding.txtRegTfno.setErrorEnabled(false);
            esValido = true;
        }
        if(codPostal.isEmpty()){
            esValido = false;
            binding.etRegCodPostal.requestFocus();
            binding.txtRegCodPostal.setError("Ingrese un código postal");
        }
        else{
            binding.txtRegCodPostal.setErrorEnabled(false);
            esValido = true;
        }
        if(ciudad.isEmpty()){
            esValido = false;
            binding.etRegCiudad.requestFocus();
            binding.txtRegCiudad.setError("Ingrese una ciudad");
        }
        else{
            binding.txtRegCiudad.setErrorEnabled(false);
            esValido = true;
        }
        if(calle.isEmpty()){
            esValido = false;
            binding.etRegCalle.requestFocus();
            binding.txtRegCalle.setError("Ingrese una calle");
        }
        else{
            binding.txtRegCalle.setErrorEnabled(false);
            esValido = true;
        }
        if(numero.isEmpty()){
            esValido = false;
            binding.etRegNumero.requestFocus();
            binding.txtRegNumero.setError("Ingrese un número");
        }
        else{
            binding.txtRegNumero.setErrorEnabled(false);
            esValido = true;
        }
        return esValido;
    }
    public void toastCorrecto(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup)findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMessageToastOK);
        txtMensaje.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
    public void toastError(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, null);
        TextView txtMensaje = view.findViewById(R.id.txtMessageToastError);
        txtMensaje.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}