package com.mol21.cliente_deliveryrice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mol21.cliente_deliveryrice.R;
import com.mol21.cliente_deliveryrice.databinding.ActivityInicioBinding;
import com.mol21.cliente_deliveryrice.model.Usuario;
import com.mol21.cliente_deliveryrice.utils.LocalDateTimeAdapter;

import java.time.LocalDateTime;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mostrarDialogoSalida();
            }
        });
        setSupportActionBar(binding.appBarInicio.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
       navController = Navigation.findNavController(this, R.id.nav_host_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String usuarioJson = preferences.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView tvNombre = vistaHeader.findViewById(R.id.tview_header_nombre),
                    tvEmail = vistaHeader.findViewById(R.id.tview_header_email);
            tvNombre.setText(u.getNombre() + " " + u.getApellido());
            tvEmail.setText(u.getEmail());
        } else {

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
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cerrarSesion) {
            mostrarDialogoCerrarSesion();
        } else if (item.getItemId() == R.id.carrito) {
            Toast.makeText(this, "Carrito", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.carritoFragment);
            //Navigation.findNavController(view).navigate(R.id.action_nav_home_to_arrocesFragment);
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
        SharedPreferences preferences = getSharedPreferences("sesion", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}