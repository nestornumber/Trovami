package com.cubit.trovami;

import android.graphics.BitmapFactory;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Set;

public class MostrarResultados extends AppCompatActivity {

    private LinearLayout linearLayoutContenedor;
    private EditText editTextBuscar;
    private boolean objetoTomado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
=======
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id == R.id.home){
                    startActivity(new Intent(getApplicationContext(),
                            Busqueda.class));
                    return true;
                }
                if (id == R.id.search) {
                    startActivity(new Intent(getApplicationContext(),
                            MostrarResultados.class));
                    return true;
                }
                if (id == R.id.edit){
                    startActivity(new Intent(getApplicationContext(),
                            EditarObjeto.class));
                    return true;
                }
                if (id == R.id.settings){
                    startActivity(new Intent(getApplicationContext(),
                            Ajustes.class));
                    return true;
                }
                return false;
            }
        });

>>>>>>> 0e1ad54a5a4bdc5e5b26b1a3b3a00312af39d952
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT
        ));
        scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.trovami_background));

        LinearLayout layoutPrincipal = new LinearLayout(this);
        layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramsLayoutPrincipal = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        paramsLayoutPrincipal.setMargins(16, 16, 16, 16);
        layoutPrincipal.setLayoutParams(paramsLayoutPrincipal);

        LinearLayout botonesLayoutSuperior = new LinearLayout(this);
        botonesLayoutSuperior.setOrientation(LinearLayout.HORIZONTAL);
        botonesLayoutSuperior.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Botones adicionales
        String[] nombresBotones = {"Inicio", "Buscar", "Agregar", "Ajustes"};
        int[] drawables = {
                R.drawable.nav_icon_home,
                R.drawable.nav_icon_search,
                R.drawable.nav_icon_add2,
                R.drawable.nav_icon_settings
        };

        for (int i = 0; i < nombresBotones.length; i++) {
            Button boton = new Button(this);
            boton.setText(nombresBotones[i]);
            boton.setBackgroundResource(R.drawable.trovami_btn_editar);
            boton.setTextColor(Color.BLACK);

            // Configura el tamaño del botón (ajusta según tus necesidades)
            LinearLayout.LayoutParams paramsBoton = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsBoton.setMargins(0, 0, 10, 0);  // Ajusta los márgenes según tu diseño
            boton.setLayoutParams(paramsBoton);

            // Puedes ajustar el tamaño del texto
            boton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            // Configura el icono del botón
            boton.setCompoundDrawablesWithIntrinsicBounds(0, drawables[i], 0, 0);


            final int finalI = i;
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirActividad(finalI);
                }
            });
            botonesLayoutSuperior.addView(boton);
        }

        layoutPrincipal.addView(botonesLayoutSuperior);


        editTextBuscar = new EditText(this);
        editTextBuscar.setId(View.generateViewId());
        editTextBuscar.setHint("Ingrese el nombre del objeto");
        editTextBuscar.setHintTextColor(ContextCompat.getColor(this, R.color.trovami_textGray));
        editTextBuscar.setTextColor(ContextCompat.getColor(this, R.color.trovami_textGray));
        editTextBuscar.setBackground(ContextCompat.getDrawable(this, R.drawable.trovami_btn_rounded));
        editTextBuscar.setTypeface(getResources().getFont(R.font.inter_regular_style));
        editTextBuscar.setPadding(25, 10, 25, 10);
        editTextBuscar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        editTextBuscar.setAllCaps(false);

        LinearLayout.LayoutParams paramsEditText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEditText.setMargins(0, 50, 0, 0);
        editTextBuscar.setLayoutParams(paramsEditText);
        layoutPrincipal.addView(editTextBuscar);

        LinearLayout botonesLayout = new LinearLayout(this);
        botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
        botonesLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        Button botonBuscar = new Button(this);
        botonBuscar.setText("Buscar");
        botonBuscar.setBackgroundResource(R.drawable.trovami_btn_lotome);
        botonBuscar.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams paramsBotonBuscar = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        paramsBotonBuscar.setMargins(0, 10, 5, 10);
        botonBuscar.setLayoutParams(paramsBotonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarObjeto();
            }
        });
        botonesLayout.addView(botonBuscar);

        Button botonTodos = new Button(this);
        botonTodos.setText("Todos");
        botonTodos.setBackgroundResource(R.drawable.trovami_btn_lotome);
        botonTodos.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams paramsBotonTodos = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        paramsBotonTodos.setMargins(5, 10, 0, 10);
        botonTodos.setLayoutParams(paramsBotonTodos);
        botonTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTodosObjetos();
            }
        });
        botonesLayout.addView(botonTodos);

        layoutPrincipal.addView(botonesLayout);

        linearLayoutContenedor = new LinearLayout(this);
        linearLayoutContenedor.setOrientation(LinearLayout.VERTICAL);
        linearLayoutContenedor.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        layoutPrincipal.addView(linearLayoutContenedor);

        scrollView.addView(layoutPrincipal);
        setContentView(scrollView);

        objetoTomado = false;
        mostrarTodosObjetos();
    }

    private void abrirActividad(int posicionBoton) {
        Intent intent;

        switch (posicionBoton) {
            case 0:
                // Lógica para el primer botón (Inicio)
                intent = new Intent(MostrarResultados.this, Busqueda.class);
                startActivity(intent);
                break;
            case 1:
                // Lógica para el segundo botón (Buscar)
                intent = new Intent(MostrarResultados.this, MostrarResultados.class);
                startActivity(intent);
                break;
            case 2:
                // Lógica para el tercer botón (Agregar)
                intent = new Intent(MostrarResultados.this, EditarObjeto.class);
                startActivity(intent);
                break;
            case 3:
                // Lógica para el tercer botón (ajustes)
                intent = new Intent(MostrarResultados.this, Ajustes.class);
                startActivity(intent);
                break;

            default:
                // Manejo por defecto o lanzar una excepción si es necesario
                break;}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem ajustesItem = menu.add("Ajustes");
        MenuItem agregarObjetoItem = menu.add("Agregar Objeto");
        MenuItem buscarItem = menu.add("Buscar");

        ajustesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MostrarResultados.this, Ajustes.class));
                return true;
            }
        });

        agregarObjetoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MostrarResultados.this, EditarObjeto.class));
                return true;
            }
        });

        buscarItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MostrarResultados.this, MostrarResultados.class));
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void mostrarTodosObjetos() {
        linearLayoutContenedor.removeAllViews();

        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        Set<String> keys = preferences.getAll().keySet();
        for (String key : keys) {
            if (!key.endsWith("_estanteria") && !key.endsWith("_imagen")) {
                objetoTomado = getEstadoObjetoTomado(key);
                mostrarObjetoLayout(key);
            }
        }
    }

    private void mostrarObjetoLayout(String key) {
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);

        String nombre = key;
        String ubicacion = preferences.getString(key, "");
        String estanteria = preferences.getString(key + "_estanteria", "");
        String imagenUriString = preferences.getString(key + "_imagen", "");

        RelativeLayout objetoLayout = new RelativeLayout(this);
        objetoLayout.setBackgroundResource(R.drawable.trovami_card_result);
        objetoLayout.setPadding(20, 20, 20, 20);

        GradientDrawable border = new GradientDrawable();
        border.setColor(ContextCompat.getColor(this, R.color.trovami_background));
        border.setStroke(2, Color.BLACK);
        objetoLayout.setBackground(border);

        LinearLayout.LayoutParams paramsObjetoLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        paramsObjetoLayout.setMargins(0, 0, 0, 20);
        objetoLayout.setLayoutParams(paramsObjetoLayout);

        TextView textViewNombre = new TextView(this);
        textViewNombre.setId(View.generateViewId());
        textViewNombre.setText(nombre);
        textViewNombre.setTextSize(getResources().getDimension(R.dimen.font_size_large));
        textViewNombre.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams paramsNombre = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsNombre.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        objetoLayout.addView(textViewNombre, paramsNombre);

        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setImageResource(R.drawable.editobj_info_image_720p);
        RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                200
        );
        paramsImage.addRule(RelativeLayout.BELOW, textViewNombre.getId());
        objetoLayout.addView(imageView, paramsImage);

        TextView textViewUbicacion = new TextView(this);
        textViewUbicacion.setId(View.generateViewId());
        textViewUbicacion.setText("Ubicación: " + ubicacion);
        textViewUbicacion.setTextSize(getResources().getDimension(R.dimen.font_size_medium));
        textViewUbicacion.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams paramsUbicacion = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsUbicacion.addRule(RelativeLayout.BELOW, imageView.getId());
        paramsUbicacion.setMargins(0, 24, 0, 0);
        objetoLayout.addView(textViewUbicacion, paramsUbicacion);

        TextView textViewEstanteria = new TextView(this);
        textViewEstanteria.setId(View.generateViewId());
        textViewEstanteria.setText("Estantería: " + estanteria);
        textViewEstanteria.setTextSize(getResources().getDimension(R.dimen.font_size_smallMedium));
        textViewEstanteria.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams paramsEstanteria = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEstanteria.addRule(RelativeLayout.BELOW, textViewUbicacion.getId());
        paramsEstanteria.setMargins(0, 10, 0, 0);
        objetoLayout.addView(textViewEstanteria, paramsEstanteria);

        linearLayoutContenedor.addView(objetoLayout);

        if (imagenUriString != null && !imagenUriString.isEmpty()) {
            Uri imagenUri = Uri.parse(imagenUriString);
            imageView.setImageURI(imagenUri);
        } else {
            imageView.setImageResource(R.drawable.editobj_info_image_720p);
        }

        final String nombreObjetoActual = nombre;

        Button botonEliminar = new Button(this);
        botonEliminar.setText("Eliminar");
        botonEliminar.setBackgroundResource(R.drawable.trovami_btn_editar);
        botonEliminar.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams paramsEliminar = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEliminar.addRule(RelativeLayout.BELOW, textViewEstanteria.getId());
        paramsEliminar.addRule(RelativeLayout.ALIGN_PARENT_START);
        paramsEliminar.setMargins(0, 10, 0, 0);
        objetoLayout.addView(botonEliminar, paramsEliminar);
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarObjeto(nombreObjetoActual);
            }
        });

        Button botonLoTome = new Button(this);
        botonLoTome.setBackgroundResource(R.drawable.trovami_btn_lotome);
        botonLoTome.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams paramsLoTome = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        paramsLoTome.addRule(RelativeLayout.BELOW, textViewEstanteria.getId());
        paramsLoTome.addRule(RelativeLayout.ALIGN_PARENT_END);
        paramsLoTome.setMargins(0, 10, 0, 0);
        objetoLayout.addView(botonLoTome, paramsLoTome);

        // Restaurar el estado del botón
        if (objetoTomado) {
            botonLoTome.setText("Devolver");
        } else {
            botonLoTome.setText("Lo Tomé");
        }

        botonLoTome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objetoTomado) {
                    objetoTomado = false;
                    botonLoTome.setText("Lo Tomé");
                    eliminarNotificacion();
                } else {
                    objetoTomado = true;
                    botonLoTome.setText("Devolver");
                    mostrarNotificacion("Tomaste el objeto: " + nombreObjetoActual);
                }
                guardarEstadoObjetoTomado(nombreObjetoActual, objetoTomado);
            }
        });
    }

    private void buscarObjeto() {
        linearLayoutContenedor.removeAllViews();

        String nombreObjetoBuscado = editTextBuscar.getText().toString().trim();

        if (nombreObjetoBuscado.isEmpty()) {
            mostrarTodosObjetos();
        } else {
            buscarYMostrarObjeto(nombreObjetoBuscado);
        }
    }

    private void buscarYMostrarObjeto(String nombreObjeto) {
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        if (preferences.contains(nombreObjeto)) {
            mostrarObjetoLayout(nombreObjeto);
        } else {
            mostrarNotificacion("Objeto no encontrado");
        }
    }

    private void eliminarObjeto(String nombreObjeto) {
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(nombreObjeto);
        finish();
        startActivity(getIntent());
    }

    private void mostrarNotificacion(String mensaje) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("mi_canal_id",
                    "Mi Canal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Intent yaLoRegreseIntent = new Intent(this, NotificacionReceiver.class);
        yaLoRegreseIntent.setAction("YA_LO_REGRESE");
        PendingIntent yaLoRegresePendingIntent =
                PendingIntent.getBroadcast(this, 0, yaLoRegreseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent eliminarIntent = new Intent(this, NotificacionReceiver.class);
        eliminarIntent.setAction("ELIMINAR_NOTIFICACION");
        PendingIntent eliminarPendingIntent =
                PendingIntent.getBroadcast(this, 0, eliminarIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mi_canal_id")
                .setContentTitle("Notificación Trovami")
                .setContentText(mensaje)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .addAction(R.drawable.alerta_icon, "Ya lo Regresé", yaLoRegresePendingIntent)
                .setContentIntent(eliminarPendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

    private boolean getEstadoObjetoTomado(String nombreObjeto) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getBoolean("objetoTomado_" + nombreObjeto, false);
    }

    private void guardarEstadoObjetoTomado(String nombreObjeto, boolean estado) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("objetoTomado_" + nombreObjeto, estado);
        editor.apply();
    }


    private void eliminarNotificacion() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

        guardarEstadoObjetoTomado("objetoTomado", objetoTomado);
    }
}

class NotificacionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("YA_LO_REGRESE".equals(action) || "ELIMINAR_NOTIFICACION".equals(action)) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);
        }
    }
}