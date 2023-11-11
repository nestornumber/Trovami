package com.cubit.trovami;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesion extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Obtener referencias a las vistas
        EditText editTextUsuario = findViewById(R.id.editTextText3);
        EditText editTextContrasena = findViewById(R.id.editTextText4);
        Button btnIniciarSesion = findViewById(R.id.button2);
        Button btnCrearCuenta = findViewById(R.id.button4);

        // Configurar el evento de clic para el botón "INICIAR SESIÓN"
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos ingresados
                String usuario = editTextUsuario.getText().toString().trim();
                String contrasena = editTextContrasena.getText().toString().trim();

                // Verificar si se ingresaron ambos datos
                if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                    // Verificar si los datos coinciden con los almacenados en SharedPreferences
                    if (verificarDatosEnSharedPreferences(usuario, contrasena)) {
                        // Datos correctos, iniciar la actividad TipoDeUsuario
                        Intent intent = new Intent(IniciarSesion.this, EditarObjeto.class);
                        startActivity(intent);
                    } else {
                        // Mostrar mensaje de error si los datos son incorrectos
                        Toast.makeText(IniciarSesion.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar mensaje de alerta si no se ingresaron ambos datos
                    Toast.makeText(IniciarSesion.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el evento de clic para el botón "CREAR CUENTA"
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad Registrarse1
                Intent intent = new Intent(IniciarSesion.this, Registrarse1.class);
                startActivity(intent);
            }
        });
    }

    private boolean verificarDatosEnSharedPreferences(String usuario, String contrasena) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String storedUsuario = preferences.getString("usuario", "");
        String storedContrasena = preferences.getString("contrasena", "");
        return usuario.equals(storedUsuario) && contrasena.equals(storedContrasena);
    }
}
