package com.cubit.trovami;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Ajustes extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id == R.id.home){
                    startActivity(new Intent(getApplicationContext(),
                            Busqueda.class));
                    return true;
                }
                if (id == R.id.search) {
                    startActivity(new Intent(getApplicationContext(),
                            MostrarResultados.class));
                    return true;
                }
                if (id == R.id.edit){
                    startActivity(new Intent(getApplicationContext(),
                            EditarObjeto.class));
                    return true;
                }
                if (id == R.id.settings){
                    startActivity(new Intent(getApplicationContext(),
                            Ajustes.class));
                    return true;
                }
                return false;
            }
        });

        // Obtener referencia al TextView de nombreDeUsuario
        TextView textViewNombreDeUsuario = findViewById(R.id.tvAlertas4);

        // Reemplaza "nombreDeUsuarioGuardado" con la variable que contiene el nombre de usuario guardado
        String nombreDeUsuario = obtenerNombreDeUsuarioGuardado();
        textViewNombreDeUsuario.setText(nombreDeUsuario);

        // Obtener referencias a los botones
        Button btnEditarContraseña = findViewById(R.id.button2);
        Button btnCambiarUsuario = findViewById(R.id.button1);
        Button btnCerrarSesion = findViewById(R.id.button3);
        Button btnAcercaDe = findViewById(R.id.button4);
        Button btnSalirDeLaApp = findViewById(R.id.button5);

        // Configurar eventos de clic para los botones
        btnEditarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ajustes.this, Ajustes_EditarContrasena.class);
                startActivity(intent);
            }
        });

            btnCambiarUsuario.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Ajustes.this, Ajustes_EditarNombres.class);
               startActivity(intent);
         }
       });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ajustes.this, IniciarSesion.class);
                startActivity(intent);
            }
        });

        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ajustes.this, AcercaDe.class);
                startActivity(intent);
            }
        });

        btnSalirDeLaApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); // Cierra la actividad actual y todas las actividades relacionadas
            }
        });

    }

    private String obtenerNombreDeUsuarioGuardado() {
        // Reemplaza "nombreDeUsuarioGuardado" con la clave correcta utilizada para guardar el nombre de usuario
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getString("usuario", "");
    }
}