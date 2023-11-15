package com.cubit.trovami;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Busqueda extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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

//        Button btnAjustes = findViewById(R.id.button3);
        Button btnBuscar = findViewById(R.id.button1);
        Button btnAnadirObjeto = findViewById(R.id.button2);

        // Configurar evento de clic para el botón "Ajustes"
//        btnAjustes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Navegar a la actividad de ajustes
//                Intent intent = new Intent(Busqueda.this, Ajustes.class);
//                startActivity(intent);
//            }
//        });

        // Configurar evento de clic para el botón "Buscar"
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar a la actividad de mostrar resultados
                Intent intent = new Intent(Busqueda.this, MostrarResultados.class);
                startActivity(intent);
            }
        });

        // Configurar evento de clic para el botón "Añadir Objeto"
        btnAnadirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navegar a la actividad de editar objeto
                Intent intent = new Intent(Busqueda.this, EditarObjeto.class);
                startActivity(intent);
            }
        });
    }
}