package com.cubit.trovami;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Esperar 4 segundos y luego iniciar la actividad Presentacion0
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Presentacion0.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual para que no pueda regresar a la pantalla de bienvenida.
            }
        }, 4000); // 4000 milisegundos = 4 segundos
    }
}
