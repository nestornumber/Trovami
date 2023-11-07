package com.cubit.trovami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler manejador=new Handler();

        manejador.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent objVentana=new Intent(SplashScreen.this, Busqueda.class);
                startActivity(objVentana);
            }
        }, 1500);
    }
}