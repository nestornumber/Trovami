package com.cubit.trovami;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrarse2 extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse2);

        // Obtener referencias a las vistas
        EditText editTextUsuario = findViewById(R.id.editTextText3);
        EditText editTextContrasena = findViewById(R.id.editTextText4);
        Button btnCrearCuenta = findViewById(R.id.button4);
        Button btnRegresar = findViewById(R.id.button2);

        // Configurar el evento de clic para el botón "CREAR CUENTA"
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los datos ingresados
                String usuario = editTextUsuario.getText().toString().trim();
                String contrasena = editTextContrasena.getText().toString().trim();

                // Verificar si se ingresaron ambos datos
                if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                    // Guardar los datos en SharedPreferences
                    guardarDatosEnSharedPreferences(usuario, contrasena);

                    // Iniciar la actividad IniciarSesion
                    Intent intent = new Intent(Registrarse2.this, IniciarSesion.class);
                    startActivity(intent);
                } else {
                    // Mostrar mensaje de alerta si no se ingresaron ambos datos
                    Toast.makeText(Registrarse2.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el evento de clic para el botón "REGRESAR"
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad Registrarse1
                Intent intent = new Intent(Registrarse2.this, Registrarse1.class);
                startActivity(intent);
            }
        });
    }

    private void guardarDatosEnSharedPreferences(String usuario, String contrasena) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("usuario", usuario);
        editor.putString("contrasena", contrasena);
        editor.apply();
    }
}
