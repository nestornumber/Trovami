package com.cubit.trovami;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Ajustes_EditarNombres extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_editar_nombres);

        TextView textViewNombreUsuario = findViewById(R.id.tvAjustes5);
        EditText editTextNuevoNombreUsuario = findViewById(R.id.editText2);
        EditText editTextContrasena = findViewById(R.id.editText4);
        Button btnGuardar = findViewById(R.id.button1);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String storedUsuario = preferences.getString("usuario", "");
        String storedContrasena = preferences.getString("contrasena", "");

        textViewNombreUsuario.setText(storedUsuario);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaContrasena = editTextContrasena.getText().toString().trim();
                String nuevoNombreUsuario = editTextNuevoNombreUsuario.getText().toString().trim();

                if (nuevaContrasena.equals(storedContrasena)) {
                    // La contraseña ingresada coincide con la guardada, se puede cambiar el nombre de usuario
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("usuario", nuevoNombreUsuario);
                    editor.apply();

                    // Actualizar el TextView con el nuevo nombre de usuario
                    textViewNombreUsuario.setText(nuevoNombreUsuario);

                    Toast.makeText(Ajustes_EditarNombres.this, "Nombre de usuario actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Ajustes_EditarNombres.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Resto del código para el BottomNavigationView sigue igual
        // ...

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(),
                            Busqueda.class));
                    return true;
                }
                if (id == R.id.search) {
                    startActivity(new Intent(getApplicationContext(),
                            MostrarResultados.class));
                    return true;
                }
                if (id == R.id.edit) {
                    startActivity(new Intent(getApplicationContext(),
                            EditarObjeto.class));
                    return true;
                }
                if (id == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(),
                            Ajustes.class));
                    return true;
                }
                return false;
            }
        });
    }
}