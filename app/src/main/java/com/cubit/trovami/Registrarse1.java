package com.cubit.trovami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrarse1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse1);

        // Obtener referencias a las vistas
        EditText editTextNombrePersonal = findViewById(R.id.editTextText3);
        Button btnSiguiente = findViewById(R.id.button4);
        Button btnRegresar = findViewById(R.id.button2);

        // Configurar el evento de clic para el botón "SIGUIENTE"
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el nombre ingresado
                String nombrePersonal = editTextNombrePersonal.getText().toString().trim();

                // Verificar si se ingresó un nombre
                if (!nombrePersonal.isEmpty()) {
                    // Iniciar la actividad Registrarse2
                    Intent intent = new Intent(Registrarse1.this, Registrarse2.class);
                    startActivity(intent);
                } else {
                    // Mostrar mensaje de alerta si no se ingresó un nombre
                    Toast.makeText(Registrarse1.this, "Ingrese un nombre personal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configurar el evento de clic para el botón "REGRESAR"
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad IniciarSesion
                Intent intent = new Intent(Registrarse1.this, IniciarSesion.class);
                startActivity(intent);
            }
        });
    }
}
