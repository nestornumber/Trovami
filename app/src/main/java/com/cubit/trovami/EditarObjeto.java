package com.cubit.trovami;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarObjeto extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_GALLERY_PERMISSION = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 103;
    private static final int REQUEST_IMAGE_PICK = 104;

    private ImageView imageView;
    private EditText editTextNombre;
    private EditText editTextUbicacion;
    private EditText editTextEstanteria;

    private Uri imagenSeleccionadaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_objeto);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.edit);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if (id == R.id.home){
                    startActivity(new Intent(getApplicationContext(),
                            Busqueda.class));
                    overridePendingTransition (0, 0); return true;
                }
                if (id == R.id.search) {
                    startActivity(new Intent(getApplicationContext(),
                            MostrarResultados.class));
                    overridePendingTransition(0, 0); return true;
                }
                if (id == R.id.edit){
                    startActivity(new Intent(getApplicationContext(),
                            EditarObjeto.class));
                    overridePendingTransition (0, 8); return true;
                }
                if (id == R.id.settings){
                    startActivity(new Intent(getApplicationContext(),
                            Ajustes.class));
                    overridePendingTransition (0, 8); return true;
                }
                return false;
            }
        });

        imageView = findViewById(R.id.imageView1);
        editTextNombre = findViewById(R.id.editText1);
        editTextUbicacion = findViewById(R.id.editText2);
        editTextEstanteria = findViewById(R.id.editText3);

        Button btnTomarFoto = findViewById(R.id.button2);
        Button btnSeleccionarFoto = findViewById(R.id.button1);
        Button btnGuardar = findViewById(R.id.button3);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    tomarFoto();
                }
            }
        });

        btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGalleryPermission();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatosObjeto();
                abrirMostrarResultados();
            }
        });
    }

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void seleccionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    private boolean checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY_PERMISSION);
            return false;
        } else {
            seleccionarFoto();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tomarFoto();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GALLERY_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    seleccionarFoto();
                } else {
                    Toast.makeText(this, "Permiso de galería denegado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);

                // Guarda la imagen en el directorio y obtén la URI
                Uri imagenUri = guardarImagenEnDirectorioYObtenerUri(editTextNombre.getText().toString().trim(), imageBitmap);

                // Guarda la URI de la imagen
                imagenSeleccionadaUri = imagenUri;
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imagenSeleccionadaUri = data.getData();
                imageView.setImageURI(imagenSeleccionadaUri);

                // No necesitas guardar la imagen directamente aquí
            }
        }
    }

    private Uri guardarImagenEnDirectorioYObtenerUri(String nombre, Bitmap bitmap) {
        try {
            // Obtén el directorio externo específico para la aplicación
            File directorioImagenes = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ObjetosImagenes");

            // Crear el directorio si no existe
            if (!directorioImagenes.exists()) {
                directorioImagenes.mkdirs();
            }

            // Crear un archivo para la imagen en el directorio
            String nombreArchivo = "imagen_" + nombre + ".jpg";
            File archivoImagen = new File(directorioImagenes, nombreArchivo);

            // Guardar la imagen en el archivo
            try (FileOutputStream out = new FileOutputStream(archivoImagen)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }

            // Notificar a la galería que se ha agregado una nueva imagen
            MediaStore.Images.Media.insertImage(getContentResolver(), archivoImagen.getAbsolutePath(), archivoImagen.getName(), archivoImagen.getName());

            // Devuelve la URI de la imagen guardada
            return Uri.fromFile(archivoImagen);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void guardarDatosObjeto() {
        String nombre = editTextNombre.getText().toString().trim();
        String ubicacion = editTextUbicacion.getText().toString().trim();
        String estanteria = editTextEstanteria.getText().toString().trim();

        if (nombre.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(this, "Llene toda la información requerida", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("ObjetosData", MODE_PRIVATE);

        if (preferences.contains(nombre)) {
            Toast.makeText(this, "Ya existe un objeto con ese nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nombre, ubicacion);

        if (!estanteria.isEmpty()) {
            editor.putString(nombre + "_estanteria", estanteria);
        }

        if (imagenSeleccionadaUri != null) {
            // Guardar la URI de la imagen
            editor.putString(nombre + "_imagen", imagenSeleccionadaUri.toString());
        }

        editor.apply();

        limpiarCampos();

        Toast.makeText(this, "Objeto agregado", Toast.LENGTH_SHORT).show();
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextUbicacion.setText("");
        editTextEstanteria.setText("");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.editobj_info_image_720p));
        imagenSeleccionadaUri = null;
    }

    private void abrirMostrarResultados() {
        Intent intent = new Intent(this, MostrarResultados.class);
        startActivity(intent);
    }
}
