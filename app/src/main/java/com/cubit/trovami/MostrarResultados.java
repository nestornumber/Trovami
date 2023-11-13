package com.cubit.trovami;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class MostrarResultados extends AppCompatActivity {

    private LinearLayout linearLayoutContenedor;
    private EditText editTextBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT
        ));
        scrollView.setBackgroundColor(getResources().getColor(R.color.trovami_background));

        LinearLayout layoutPrincipal = new LinearLayout(this);
        layoutPrincipal.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramsLayoutPrincipal = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        paramsLayoutPrincipal.setMargins(16, 16, 16, 16); // Margen general
        layoutPrincipal.setLayoutParams(paramsLayoutPrincipal);

        editTextBuscar = new EditText(this);
        editTextBuscar.setHint("Ingrese el nombre del objeto");
        editTextBuscar.setHintTextColor(Color.BLACK);
        editTextBuscar.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams paramsEditText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEditText.setMargins(0, 20, 0, 20);
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

        mostrarTodosObjetos();
    }

    private void mostrarTodosObjetos() {
        linearLayoutContenedor.removeAllViews();

        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        Set<String> keys = preferences.getAll().keySet();
        for (String key : keys) {
            if (!key.endsWith("_estanteria") && !key.endsWith("_imagen")) {
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

        // Configurar fondo y borde de la tarjeta
        GradientDrawable border = new GradientDrawable();
        border.setColor(getResources().getColor(R.color.trovami_background));
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
        botonLoTome.setText("Lo Tomé");
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
        botonLoTome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirConfigurarAlertas(nombreObjetoActual);
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
            Toast.makeText(this, "Objeto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirConfigurarAlertas(String nombreObjeto) {
        Intent intent = new Intent(this, ConfigurarAlertas.class);
        intent.putExtra("nombreObjeto", nombreObjeto);
        startActivity(intent);
    }

    private void eliminarObjeto(String nombreObjeto) {
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(nombreObjeto);
        editor.remove(nombreObjeto + "_estanteria");
        editor.remove(nombreObjeto + "_imagen");
        editor.apply();

        finish();
        startActivity(getIntent());
    }
}
