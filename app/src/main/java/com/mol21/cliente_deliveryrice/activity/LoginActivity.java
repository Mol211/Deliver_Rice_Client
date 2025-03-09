package com.mol21.cliente_deliveryrice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityLoginBinding;
import com.mol21.cliente_deliveryrice.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.utils.LocalDateTimeAdapter;
import com.mol21.cliente_deliveryrice.viewmodel.UsuarioViewModel;

import java.time.LocalDateTime;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity{
    ActivityLoginBinding binding;
    private UsuarioViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        boolean sesionIniciada = preferences.getBoolean("sesion_iniciada", false);

        if(sesionIniciada){
            startActivity(new Intent(this, CategoryActivity.class));
            finish();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mostrarDialogoSalida();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.init();
        this.initViewModel();
    }

    private void mostrarDialogoSalida() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Seguro que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> finish()) // Cierra la actividad
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo
                .show();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }
    private void init() {
        binding.btnLogin.setOnClickListener(view -> {
            String correo = binding.etRegEmail.getText().toString();
            String password = binding.etRegPass.getText().toString();
            try{
                if(!validar()) {
                //Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                toastError("Por favor complete todos los campos");
                }else {

                    Toast.makeText(this, "Conectando con el servidor...", Toast.LENGTH_SHORT).show();
                    viewModel.login(correo, password)
                            .observe(this, response -> {
                                if (response.getRpta() == 1) {
                                    //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                    this.toastCorrecto(response.getMessage());
                                    UsuarioDTO u = response.getBody();
                                    SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    final Gson g = new GsonBuilder()
                                            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                                            .create();
                                    editor.putBoolean("sesion_iniciada",true);
                                    editor.putString("UsuarioJson", g.toJson(u, new TypeToken<UsuarioDTO>() {
                                    }
                                            .getType()));
                                    editor.apply();
                                    binding.etRegEmail.setText("");
                                    binding.etRegPass.setText("");
                                    startActivity(new Intent(this, CategoryActivity.class));
                                } else {
//                                    //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
//                                    if (response == null || response.getMessage() == null || response.getMessage().isEmpty()) {
//                                        toastError("El servidor no se encuentra disponible");
//                                    } else {
//                                        toastError(response.getMessage());
//                                    }
                                    if( response.getRpta() == 0 ){
                                        toastError(response.getMessage());
                                    } else{
                                        toastError("El servidor no se encuentra disponible");
                                    }
                                }
                            });
                }
            }catch(Exception e){
                toastError(e.getMessage());
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
        });
        binding.btnRegistrar.setOnClickListener(view ->{
            startActivity(new Intent(this, RegistrarActivity.class));
        });
        binding.txtForgetPass.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgetPassActivity.class));
        });

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
    private boolean validar(){
        boolean isOk;
        String email = binding.etRegEmail.getText().toString();
        String pass = binding.etRegPass.getText().toString();
        if(email.isEmpty()){
            binding.etRegEmail.requestFocus();
            binding.txtRegEmail.setError("Ingrese su email");
            isOk=false;
        }else{
            binding.txtRegEmail.setErrorEnabled(false);
            isOk = true;
        }
        if(pass.isEmpty()){
            binding.etRegPass.requestFocus();
            binding.txtRegPass.setError("Ingrese su contraseña");
            isOk = false;
        }else{
            binding.txtRegPass.setErrorEnabled(false);
            isOk = true;
        }
        return isOk;
    }
}