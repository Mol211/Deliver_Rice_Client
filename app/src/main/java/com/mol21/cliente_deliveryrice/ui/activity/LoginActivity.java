package com.mol21.cliente_deliveryrice.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityLoginBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.CarritoDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Rol;
import com.mol21.cliente_deliveryrice.utils.SessionManager;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.UsuarioViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity{

    //Atributos de clase
    private SessionManager sessionManager;
    ActivityLoginBinding binding;
    private UsuarioViewModel viewModel;
    private CarritoViewModel carritoViewModel;
    private UsuarioDTO usuario;


    //Metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //Buscamos si existe una sesión activa en las Sh y si existe directamente mostramos la activity Categoria
        sessionManager = SessionManager.getInstance(this);
        boolean sesionIniciada = sessionManager.isSesionIniciada();

        // Debugging: Verificar qué devuelve isSesionIniciada
        Log.d("Sesion", "isSesionIniciada: " + sessionManager.isSesionIniciada());
        if(sesionIniciada){
            usuario = sessionManager.getUsuario();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        //Inflamos la actividad Login y la establecemos como Vista
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Añadimos un disparador en la tecla de vovler atrás para que no se pueda salir de la aplicación sin un diálogo
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mostrarDialogoSalida();
            }
        });
        //Busca los paddings de la pantalla y los añade a la vista para respetarlos
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Inicia ViewModel y Lógica de la actividad
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
        carritoViewModel = new ViewModelProvider(this).get(CarritoViewModel.class);
    }
    private void init() {
        //Listener del botón Login
        binding.btnLogin.setOnClickListener(view -> {
            String correo = binding.etRegEmail.getText().toString();
            String password = binding.etRegPass.getText().toString();
            try{
                if(!validar()) {
                //Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                toastError("Por favor complete todos los campos");
                }else {
                    //Si puede validar llama a metodo .login del viewModel
                    viewModel.login(correo, password)
                            .observe(this, response -> {
                                if (response.getRpta() == 1) {
                                    usuario = response.getBody();
                                    if (usuario.getRol()!=Rol.CLIENTE){
                                        login(usuario,-1);
                                    }else{
                                            long idUsuario = usuario.getId();
                                            carritoViewModel.nuevoCarrito(idUsuario).observe(this,respuesta->{
                                                if(respuesta.getRpta()==-1){
                                                    toastError(respuesta.getMessage());
                                                }else {
                                                    //Si respuesta positiva, se ha creado nuevo carrito
                                                    //Si respuesta 0, carrito ya existe y devuelve carrito existente
                                                    long idCarrito = respuesta.getBody().getId();
                                                    this.toastCorrecto(response.getMessage());
                                                    Log.d("ID CARRITO", "IDCarrito: "+idCarrito);
                                                    login(usuario,idCarrito);
                                                }
                                            });
                                    }
                                } else {
                                    if( response.getRpta() == 0 ){
                                        toastError(response.getMessage());
                                    } else{
                                        toastError("El servidor no se encuentra disponible");
                                    }
                                }
                            });
                }
            }catch(Exception e){
                e.printStackTrace();
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

        //Listener del btn Registrar
        binding.btnRegistrar.setOnClickListener(view ->{
            startActivity(new Intent(this, RegistrarActivity.class));
        });

        //Listener del btn ForgetPass
        binding.txtForgetPass.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgetPassActivity.class));
        });

    }
    //Metodo que inicia sesion en sharedPreferences y abre la activity Categoria.
    private void login(UsuarioDTO u, long carritoId) {
        sessionManager.iniciarSesion(u, carritoId);
//        binding.etRegEmail.setText("");
//        binding.etRegPass.setText("");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private long nuevoCarrito(long idUsuario) {
        CarritoDTO carrito = carritoViewModel.nuevoCarrito(idUsuario).getValue().getBody();
        return carrito.getId();
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