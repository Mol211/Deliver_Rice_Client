package com.mol21.cliente_deliveryrice.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityInicioBinding;
import com.mol21.cliente_deliveryrice.mvvm.model.DTO.UsuarioDTO;
import com.mol21.cliente_deliveryrice.mvvm.model.Rol;
import com.mol21.cliente_deliveryrice.utils.SessionManager;
import com.mol21.cliente_deliveryrice.mvvm.viewmodel.CarritoViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    SessionManager sessionManager;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private NavController navController;
    private CarritoViewModel viewModel;
    private UsuarioDTO usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuarioActual = sessionManager.getUsuario();


        //Obtener el navController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_inicio);
        navController = navHostFragment.getNavController();

        //Cargar navGraph y cambiar startDestination según rol
        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.mobile_navigation);
        int startDestination = R.id.nav_categoria;
        if(usuarioActual.getRol().equals(Rol.ADMINISTRADOR)){
            startDestination = R.id.nav_currentPedidos;
        }
        else if (usuarioActual.getRol().equals(Rol.REPARTIDOR)) {
            startDestination = R.id.nav_pedidosAsignados;
        }
        navGraph.setStartDestination(startDestination);
        navController.setGraph(navGraph);

        setSupportActionBar(binding.appBarInicio.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                startDestination)
                .setOpenableLayout(drawer)
                .build();

//        navigationView.clearFocus();
//        navController = Navigation.findNavController(this, R.id.nav_host_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setCheckedItem(startDestination);

        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mostrarDialogoSalida();
            }
        });

        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CarritoViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
        Menu viewMenu = binding.navView.getMenu();
        switch (usuarioActual.getRol()){
            case CLIENTE:
                viewMenu.setGroupVisible(R.id.user_menu,true);
                break;
            case REPARTIDOR:
                viewMenu.setGroupVisible(R.id.repartidor_menu, true);
                break;
            case ADMINISTRADOR:
                viewMenu.setGroupVisible(R.id.admin_menu,true);
                break;
        }
    }

    private void loadData() {
        UsuarioDTO u = sessionManager.getUsuario();
        if (u != null) {
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView tvNombre = vistaHeader.findViewById(R.id.tview_header_nombre),
                    tvEmail = vistaHeader.findViewById(R.id.tview_header_email);
            tvNombre.setText(u.getNombre() + " " + u.getApellido());
            tvEmail.setText(u.getEmail());
        }
    }

    private void mostrarDialogoSalida() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Seguro que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> finishAffinity()) // Cierra la actividad
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo
                .show();
    }


    //Inflamos menú superior
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_top, menu);
        if(!usuarioActual.getRol().equals(Rol.CLIENTE)){
            menu.findItem(R.id.carrito).setVisible(false);
        }
        return true;
    }

    //Menu superior derecho

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Cerrar Sesion
        if (item.getItemId() == R.id.cerrarSesion) {
            mostrarDialogoCerrarSesion();
        }
        //Ir al Carrito
        else if (item.getItemId() == R.id.carrito) {
            if(navController.getCurrentDestination().getId()!=R.id.nav_carritoFragment){
                Toast.makeText(this, navController.getCurrentDestination().getDisplayName(), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.nav_carritoFragment, null,
                        new NavOptions.Builder()
                                .setPopUpTo(R.id.nav_arrocesFragment, true)
                                .build());
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Seguro que desea cerrar la sesión actual?" +
                        "Se perderán todos los datos de sus compras actuales")
                .setPositiveButton("Sí", (dialog, which) -> logout()) // Cierra la actividad
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo
                .show();
    }

    private void logout() {
        viewModel.vaciarCarrito(sessionManager.getUsuario().getId()).observe(this,response->{
            if(response.getRpta()==-1){
                Log.e("ERROR",response.getMessage());

            }
            else{
                sessionManager.cerrarSesion();
                this.finish();
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}