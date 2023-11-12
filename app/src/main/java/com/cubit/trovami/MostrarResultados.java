package com.cubit.trovami;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;

public class MostrarResultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_resultados);

        // Obtener referencia al contenedor LinearLayout
        LinearLayout linearLayoutContenedor = findViewById(R.id.linearLayoutContenedor);

        // Obtener datos guardados en SharedPreferences
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        for (String key : preferences.getAll().keySet()) {
            // Filtrar solo las claves que corresponden a los objetos
            if (!key.endsWith("_estanteria") && !key.endsWith("_imagen")) {
                // Este es un ejemplo para obtener el nombre y la ubicación, ajusta según tu estructura
                String nombre = key;
                String ubicacion = preferences.getString(key, "");
                String estanteria = preferences.getString(key + "_estanteria", "");
                String imagenUriString = preferences.getString(key + "_imagen", "");

                // Crear un nuevo elemento en tu diseño para mostrar los datos del objeto
                RelativeLayout objetoLayout = new RelativeLayout(this);
                objetoLayout.setBackgroundResource(R.drawable.trovami_card_result);
                objetoLayout.setPadding(20, 20, 20, 20);

                TextView textViewNombre = new TextView(this);
                textViewNombre.setId(View.generateViewId());
                textViewNombre.setText(nombre);
                textViewNombre.setTextSize(getResources().getDimension(R.dimen.font_size_large));
                RelativeLayout.LayoutParams paramsNombre = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsNombre.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                objetoLayout.addView(textViewNombre, paramsNombre);

                ImageView imageView = new ImageView(this);
                imageView.setId(View.generateViewId());
                imageView.setImageResource(R.drawable.editobj_info_image_720p); // Puedes establecer la imagen por defecto
                RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        200 // Altura de la imagen
                );
                paramsImage.addRule(RelativeLayout.BELOW, textViewNombre.getId());
                objetoLayout.addView(imageView, paramsImage);

                TextView textViewUbicacion = new TextView(this);
                textViewUbicacion.setId(View.generateViewId());
                textViewUbicacion.setText("Ubicación: " + ubicacion);
                textViewUbicacion.setTextSize(getResources().getDimension(R.dimen.font_size_medium));
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
                RelativeLayout.LayoutParams paramsEstanteria = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsEstanteria.addRule(RelativeLayout.BELOW, textViewUbicacion.getId());
                paramsEstanteria.setMargins(0, 10, 0, 0);
                objetoLayout.addView(textViewEstanteria, paramsEstanteria);

                // Añadir el objetoLayout al contenedor
                linearLayoutContenedor.addView(objetoLayout);

                // Si hay una imagen guardada, cargarla en el ImageView
                if (!imagenUriString.isEmpty()) {
                    Uri imagenUri = Uri.parse(imagenUriString);
                    imageView.setImageURI(imagenUri);
                } else {
                    // Si no hay una URI, intentar cargar el Bitmap
                    Bitmap imagenBitmap = obtenerBitmapDesdePreferences(preferences, key);
                    if (imagenBitmap != null) {
                        imageView.setImageBitmap(imagenBitmap);
                    }
                }

                // Configurar el botón de editar
                Button botonEditar = new Button(this);
                botonEditar.setId(View.generateViewId());
                botonEditar.setText("Editar");
                RelativeLayout.LayoutParams paramsEditar = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsEditar.addRule(RelativeLayout.BELOW, textViewEstanteria.getId());
                paramsEditar.setMargins(0, 20, 0, 0);
                objetoLayout.addView(botonEditar, paramsEditar);

                botonEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para editar el objeto
                        Intent intent = new Intent(MostrarResultados.this, EditarObjeto.class);
                        startActivity(intent);
                    }
                });

                // Configurar el botón de "Lo Tomé"
                Button botonLoTome = new Button(this);
                botonLoTome.setId(View.generateViewId());
                botonLoTome.setText("Lo Tomé");
                RelativeLayout.LayoutParams paramsLoTome = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsLoTome.addRule(RelativeLayout.BELOW, botonEditar.getId());
                paramsLoTome.setMargins(0, 10, 0, 0);
                objetoLayout.addView(botonLoTome, paramsLoTome);

                botonLoTome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lógica para marcar como "Lo Tomé"
                        Intent intent = new Intent(MostrarResultados.this, ConfigurarAlertas.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    // Método para obtener el Bitmap desde las preferencias
    private Bitmap obtenerBitmapDesdePreferences(SharedPreferences preferences, String key) {
        // Intentar obtener el Bitmap almacenado en las preferencias compartidas
        String bitmapString = preferences.getString(key + "_imagen", "");
        if (!bitmapString.isEmpty()) {
            // Verificar si la imagen proviene de la cámara
            if (bitmapString.startsWith("data:image/jpeg;base64,")) {
                // La imagen proviene de la cámara, extraer el código Base64 y decodificarlo
                String base64Image = bitmapString.substring("data:image/jpeg;base64,".length());
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } else {
                // La imagen proviene de la galería, cargar el Bitmap directamente
                byte[] decodedString = Base64.decode(bitmapString, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
        }
        return null;
    }
}
