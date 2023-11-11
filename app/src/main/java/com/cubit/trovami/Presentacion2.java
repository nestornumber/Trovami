package com.cubit.trovami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Presentacion2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion2);

        Button siguienteButton = findViewById(R.id.button2);
        Button regresarButton = findViewById(R.id.button4);

        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Presentacion2.this, Presentacion3.class);
                startActivity(intent);
            }
        });

        regresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Presentacion2.this, Presentacion1.class);
                startActivity(intent);
            }
        });
    }
}
