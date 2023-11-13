
package com.cubit.trovami;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Set;

public class MostrarResultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_resultados);

        LinearLayout linearLayoutContenedor = findViewById(R.id.linearLayoutContenedor);

        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        Set<String> keys = preferences.getAll().keySet();
        for (String key : keys) {
            if (!key.endsWith("_estanteria") && !key.endsWith("_imagen")) {
                String nombre = key;
                String ubicacion = preferences.getString(key, "");
                String estanteria = preferences.getString(key + "_estanteria", "");
                String imagenUriString = preferences.getString(key + "_imagen", "");

                RelativeLayout objetoLayout = new RelativeLayout(this);
                objetoLayout.setBackgroundResource(R.drawable.trovami_card_result);
                objetoLayout.setPadding(20, 20, 20, 20);

                // Añadir margen inferior entre tarjetas
                LinearLayout.LayoutParams paramsObjetoLayout = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                paramsObjetoLayout.setMargins(0, 0, 0, 20);
                objetoLayout.setLayoutParams(paramsObjetoLayout);

                TextView textViewNombre = new TextView(this);
                textViewNombre.setId(View.generateViewId());
                textViewNombre.setText(nombre);
                textViewNombre.setTextSize(getResources().getDimension(R.dimen.font_size_large));
                textViewNombre.setTextColor(getResources().getColor(android.R.color.black));
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
                textViewUbicacion.setTextColor(getResources().getColor(android.R.color.black));
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
                textViewEstanteria.setTextColor(getResources().getColor(android.R.color.black));
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
                    // Si no hay una URI válida, establece la imagen predeterminada
                    imageView.setImageResource(R.drawable.editobj_info_image_720p);
                }

                // Obtener el nombre del objeto actual para pasarlo a la siguiente actividad
                final String nombreObjetoActual = nombre;

                Button botonEliminar = new Button(this);
                botonEliminar.setText("Eliminar");
                botonEliminar.setBackgroundResource(R.drawable.trovami_btn_editar);
                botonEliminar.setTextColor(getResources().getColor(android.R.color.black));
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
                        // Lógica para eliminar el objeto
                        eliminarObjeto(nombreObjetoActual);
                    }
                });

                Button botonLoTome = new Button(this);
                botonLoTome.setText("Lo Tomé");
                botonLoTome.setBackgroundResource(R.drawable.trovami_btn_lotome);
                botonLoTome.setTextColor(getResources().getColor(android.R.color.white));
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
                        // Lógica para "Lo Tomé"
                        abrirConfigurarAlertas(nombreObjetoActual);
                    }
                });
            }
        }
    }

    private void abrirEditarObjeto(String nombreObjeto) {
        Intent intent = new Intent(this, EditarObjeto.class);
        intent.putExtra("nombreObjeto", nombreObjeto);
        startActivity(intent);
    }

    private void abrirConfigurarAlertas(String nombreObjeto) {
        Intent intent = new Intent(this, ConfigurarAlertas.class);
        intent.putExtra("nombreObjeto", nombreObjeto);
        startActivity(intent);
    }

    private void eliminarObjeto(String nombreObjeto) {
        // Lógica para eliminar el objeto
        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(nombreObjeto);
        editor.remove(nombreObjeto + "_estanteria");
        editor.remove(nombreObjeto + "_imagen");
        editor.apply();

        // Recargar la actividad para reflejar los cambios
        finish();
        startActivity(getIntent());
    }
}