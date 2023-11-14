package com.cubit.trovami;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

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
    }
}