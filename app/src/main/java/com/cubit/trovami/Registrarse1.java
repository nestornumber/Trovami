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


        EditText editTextNombrePersonal = findViewById(R.id.editTextText3);
        Button btnSiguiente = findViewById(R.id.button4);
        Button btnRegresar = findViewById(R.id.button2);


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombrePersonal = editTextNombrePersonal.getText().toString().trim();


                if (!nombrePersonal.isEmpty()) {

                    Intent intent = new Intent(Registrarse1.this, Registrarse2.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(Registrarse1.this, "Ingrese un nombre personal", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Registrarse1.this, IniciarSesion.class);
                startActivity(intent);
            }
        });
    }
}
