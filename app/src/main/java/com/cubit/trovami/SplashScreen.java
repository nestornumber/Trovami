package com.cubit.trovami;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final String PREFS_NAME = "FirstTimePreferences";
    private static final String KEY_FIRST_TIME = "isFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Verificar si es la primera vez que el usuario ingresa a la aplicación
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(KEY_FIRST_TIME, true);

        // Establecer el tiempo de espera antes de navegar a la siguiente actividad (4 segundos)
        int splashTimeOut = 2000;

        new Handler().postDelayed(() -> {
            // Si es la primera vez, navegar a la actividad de presentación
            if (isFirstTime) {
                Intent i = new Intent(SplashScreen.this, Presentacion0.class);
                startActivity(i);
                finish();
            } else {
                // Si no es la primera vez, navegar a la actividad de inicio de sesión
                Intent i = new Intent(SplashScreen.this, IniciarSesion.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        // Guardar que el usuario ya ha ingresado por primera vez
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_FIRST_TIME, false);
        editor.apply();
    }
}
