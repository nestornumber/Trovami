package com.cubit.trovami;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Ajustes_EditarContrasena extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_editar_contrasena);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id == R.id.home){
                    startActivity(new Intent(getApplicationContext(),
                            Busqueda.class));
                    overridePendingTransition (0, 0); return true;
                }
                if (id == R.id.search) {
                    startActivity(new Intent(getApplicationContext(),
                            MostrarResultados.class));
                    overridePendingTransition (0, 0); return true;
                }
                if (id == R.id.edit){
                    startActivity(new Intent(getApplicationContext(),
                            EditarObjeto.class));
                    overridePendingTransition (0, 0); return true;
                }
                if (id == R.id.settings){
                    startActivity(new Intent(getApplicationContext(),
                            Ajustes.class));
                    overridePendingTransition (0, 0); return true;
                }
                return false;
            }
        });


        // Obtener referencias a las vistas
        TextView tvNombreUsuario = findViewById(R.id.tvAjustes5);
        EditText etContraseñaActual = findViewById(R.id.editText1);
        EditText etNuevaContraseña = findViewById(R.id.editText2);
        EditText etConfirmarNuevaContraseña = findViewById(R.id.editText3);
        Button btnGuardar = findViewById(R.id.button1);

        // Obtener datos guardados
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String nombreUsuarioGuardado = preferences.getString("usuario", "");
        String contraseñaGuardada = preferences.getString("contrasena", "");

        // Mostrar datos actuales
        tvNombreUsuario.setText(nombreUsuarioGuardado);
        etContraseñaActual.setText(contraseñaGuardada);

        // Configurar el evento de clic para el botón "GUARDAR"
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener las contraseñas ingresadas
                String contraseñaActual = etContraseñaActual.getText().toString().trim();
                String nuevaContraseña = etNuevaContraseña.getText().toString().trim();
                String confirmarNuevaContraseña = etConfirmarNuevaContraseña.getText().toString().trim();

                // Verificar si la contraseña actual coincide con la almacenada
                if (!contraseñaActual.equals(contraseñaGuardada)) {
                    Toast.makeText(Ajustes_EditarContrasena.this, "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show();
                } else if (!nuevaContraseña.equals(confirmarNuevaContraseña)) {
                    Toast.makeText(Ajustes_EditarContrasena.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar la nueva contraseña en SharedPreferences
                    guardarNuevaContraseñaEnSharedPreferences(nuevaContraseña);

                    // Mostrar mensaje de éxito
                    Toast.makeText(Ajustes_EditarContrasena.this, "Contraseña modificada exitosamente", Toast.LENGTH_SHORT).show();

                    // Iniciar la actividad IniciarSesion
                    Intent intent = new Intent(Ajustes_EditarContrasena.this, IniciarSesion.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void guardarNuevaContraseñaEnSharedPreferences(String nuevaContraseña) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("contrasena", nuevaContraseña);
        editor.apply();
    }
}
