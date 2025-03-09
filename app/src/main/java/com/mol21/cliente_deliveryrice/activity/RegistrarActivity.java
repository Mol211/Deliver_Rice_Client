package com.mol21.cliente_deliveryrice.activity;

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
import com.mol21.cliente_deliveryrice.model.DTO.RegistrarUsuarioDTO;
import com.mol21.cliente_deliveryrice.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.model.Direccion;
import com.mol21.cliente_deliveryrice.model.Usuario;
import com.mol21.cliente_deliveryrice.viewmodel.UsuarioViewModel;

import java.time.LocalDateTime;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegistrarActivity extends AppCompatActivity {
    TextInputLayout txtEmail, txtPass, txtNombre, txtApellido, txtTfno, txtCodPostal, txtCiudad, txtCalle, txtNumero;
    TextInputEditText etEmail, etPass, etNombre, etApellido, etTfno, etCodPostal, etCiudad, etCalle, etNumero;
    Button btnRegistrar, btnBack;
    private UsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
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
        etEmail = findViewById(R.id.et_Reg_email);
        etPass = findViewById(R.id.et_Reg_pass);
        etNombre = findViewById(R.id.et_Reg_nombre);
        etApellido = findViewById(R.id.et_Reg_apellidos);
        etTfno = findViewById(R.id.et_Reg_Tfno);
        etCodPostal = findViewById(R.id.et_Reg_codPostal);
        etCiudad = findViewById(R.id.et_Reg_ciudad);
        etCalle = findViewById(R.id.et_Reg_calle);
        etNumero = findViewById(R.id.et_Reg_numero);
        txtEmail = findViewById(R.id.txt_Reg_email);
        txtPass = findViewById(R.id.txt_Reg_pass);
        txtNombre = findViewById(R.id.txt_Reg_nombre);
        txtApellido = findViewById(R.id.txt_Reg_apellidos);
        txtTfno = findViewById(R.id.txt_Reg_tfno);
        txtCodPostal = findViewById(R.id.txt_Reg_codPostal);
        txtCiudad = findViewById(R.id.txt_Reg_ciudad);
        txtCalle = findViewById(R.id.txt_Reg_calle);
        txtNumero = findViewById(R.id.txt_Reg_numero);
        btnRegistrar = findViewById(R.id.btn_Reg_registrarr);
        btnBack = findViewById(R.id.btn_Reg_back);
        btnRegistrar.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String tfno = etTfno.getText().toString();
            String codPostal = etCodPostal.getText().toString();
            String ciudad = etCiudad.getText().toString();
            String numero = etNumero.getText().toString();
            String calle = etCalle.getText().toString();
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
            etPass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtPass.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtEmail.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etNombre.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtNombre.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etApellido.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtApellido.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etTfno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtTfno.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etNumero.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtNumero.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etCiudad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtCiudad.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etCodPostal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtCodPostal.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            etCalle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    txtCalle.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        });
        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });

    }
    public boolean validar(){
        boolean esValido = false;
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        String tfno = etTfno.getText().toString();
        String codPostal = etCodPostal.getText().toString();
        String ciudad = etCiudad.getText().toString();
        String numero = etNumero.getText().toString();
        String calle = etCalle.getText().toString();
        if(email.isEmpty()){
            esValido = false;
            etEmail.requestFocus();
            txtEmail.setError("Ingrese un correo electrónico válido");
        }
        else{
            txtEmail.setErrorEnabled(false);
            esValido = true;
        }
        if(pass.isEmpty()){
            esValido = false;
            etPass.requestFocus();
            txtPass.setError("Ingrese una contraseña válida");
        }
        else{
            txtPass.setErrorEnabled(false);
            esValido = true;
        }
        if(nombre.isEmpty()){
            esValido = false;
            etNombre.requestFocus();
            txtNombre.setError("Ingrese un nombre válido");
        }
        else{
            txtNombre.setErrorEnabled(false);
            esValido = true;
        }
        if(apellido.isEmpty()){
            esValido = false;
            etApellido.requestFocus();
            txtApellido.setError("Ingrese un apellido");
        }
        else{
            txtApellido.setErrorEnabled(false);
            esValido = true;
        }
        if(tfno.isEmpty()){
            esValido = false;
            etTfno.requestFocus();
            txtTfno.setError("Ingrese un número de teléfono");
        }
        else{
            txtTfno.setErrorEnabled(false);
            esValido = true;
        }
        if(codPostal.isEmpty()){
            esValido = false;
            etCodPostal.requestFocus();
            txtCodPostal.setError("Ingrese un código postal");
        }
        else{
            txtCodPostal.setErrorEnabled(false);
            esValido = true;
        }
        if(ciudad.isEmpty()){
            esValido = false;
            etCiudad.requestFocus();
            txtCiudad.setError("Ingrese una ciudad");
        }
        else{
            txtCiudad.setErrorEnabled(false);
            esValido = true;
        }
        if(calle.isEmpty()){
            esValido = false;
            etCalle.requestFocus();
            txtCalle.setError("Ingrese una calle");
        }
        else{
            txtCalle.setErrorEnabled(false);
            esValido = true;
        }
        if(numero.isEmpty()){
            esValido = false;
            etNumero.requestFocus();
            txtNumero.setError("Ingrese un número");
        }
        else{
            txtNumero.setErrorEnabled(false);
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