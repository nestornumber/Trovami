package com.cubit.trovami;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarSesion extends AppCompatActivity {

    private static final String PREFS_NAME = "UserData";
    private int intentosFallidos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        EditText editTextUsuario = findViewById(R.id.editTextText3);
        EditText editTextContrasena = findViewById(R.id.editTextText4);
        Button btnIniciarSesion = findViewById(R.id.button2);
        Button btnCrearCuenta = findViewById(R.id.button4);
        Switch switchRecuerdame = findViewById(R.id.switch1);
        Button btnOlvideContrasena = findViewById(R.id.tvAlertas6);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean recuerdame = preferences.getBoolean("recuerdame", false);
        boolean usuarioRegistrado = preferences.contains("usuario");

        if (recuerdame) {
            String storedUsuario = preferences.getString("usuario", "");
            String storedContrasena = preferences.getString("contrasena", "");
            editTextUsuario.setText(storedUsuario);
            editTextContrasena.setText(storedContrasena);
            switchRecuerdame.setChecked(true);
        }

        if (usuarioRegistrado) {
            btnCrearCuenta.setVisibility(View.GONE);
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editTextUsuario.getText().toString().trim();
                String contrasena = editTextContrasena.getText().toString().trim();

                if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                    if (verificarDatosEnSharedPreferences(usuario, contrasena)) {
                        if (switchRecuerdame.isChecked()) {
                            guardarDatosRecuerdame(usuario, contrasena);
                        }
                        Intent intent = new Intent(IniciarSesion.this, Busqueda.class);
                        startActivity(intent);
                    } else {
                        intentosFallidos++;
                        if (intentosFallidos >= 3) {
                            mostrarMensajeConteoRegresivo();
                        } else {
                            Toast.makeText(IniciarSesion.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(IniciarSesion.this, "Ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesion.this, Registrarse1.class);
                startActivity(intent);
            }
        });

        btnOlvideContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoOlvideContrasena();
            }
        });
    }

    private boolean verificarDatosEnSharedPreferences(String usuario, String contrasena) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String storedUsuario = preferences.getString("usuario", "");
        String storedContrasena = preferences.getString("contrasena", "");
        return usuario.equals(storedUsuario) && contrasena.equals(storedContrasena);
    }

    private void guardarDatosRecuerdame(String usuario, String contrasena) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contrasena", contrasena);
        editor.putBoolean("recuerdame", true);
        editor.apply();
    }

    private AlertDialog alertDialog;

    private void mostrarMensajeConteoRegresivo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Has intentado demasiadas veces");
        builder.setMessage("Inténtalo de nuevo más tarde.");
        builder.setCancelable(false); // No permite cancelar el diálogo con el botón de retroceso

        alertDialog = builder.create();
        alertDialog.show();

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                alertDialog.setMessage("Has intentado demasiadas veces. Inténtalo de nuevo en: " + millisUntilFinished / 1000 + " segundos");
            }

            @Override
            public void onFinish() {
                alertDialog.dismiss();
                intentosFallidos = 0;
            }
        }.start();
    }

    private void mostrarDialogoOlvideContrasena() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Olvidé mi contraseña");
        builder.setMessage("Ingresa tu nombre de usuario y la última contraseña que recuerdas.");

        View view = getLayoutInflater().inflate(R.layout.dialog_olvide_contrasena, null);
        EditText etUsuarioRecuperacion = view.findViewById(R.id.etUsuarioRecuperacion);
        EditText etUltimaContrasena = view.findViewById(R.id.etUltimaContrasena);

        builder.setView(view);

        builder.setPositiveButton("Verificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String usuarioRecuperacion = etUsuarioRecuperacion.getText().toString().trim();
                String ultimaContrasena = etUltimaContrasena.getText().toString().trim();
                verificarDatosRecuperacion(usuarioRecuperacion, ultimaContrasena);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void verificarDatosRecuperacion(String usuarioRecuperacion, String ultimaContrasena) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String storedUsuario = preferences.getString("usuario", "");
        String storedUltimaContrasena = preferences.getString("contrasena", "");

        if (usuarioRecuperacion.equals(storedUsuario) || ultimaContrasena.equals(storedUltimaContrasena)) {
            Intent intent = new Intent(IniciarSesion.this, Ajustes_EditarContrasena.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Datos incorrectos. No se pudo verificar la identidad.", Toast.LENGTH_SHORT).show();
        }
    }
}
