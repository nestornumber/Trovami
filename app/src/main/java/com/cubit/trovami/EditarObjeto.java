package com.cubit.trovami;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.HashSet;
import java.util.Set;

public class EditarObjeto extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final int REQUEST_GALLERY_PERMISSION = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 103;
    private static final int REQUEST_IMAGE_PICK = 104;

    private ImageView imageView;
    private EditText editTextNombre;
    private EditText editTextUbicacion;
    private EditText editTextEstanteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_objeto);

        imageView = findViewById(R.id.imageView3);
        editTextNombre = findViewById(R.id.editTextText);
        editTextUbicacion = findViewById(R.id.editTextText2);
        editTextEstanteria = findViewById(R.id.editTextText3);

        Button btnTomarFoto = findViewById(R.id.button);
        Button btnSeleccionarFoto = findViewById(R.id.button3);
        Button btnGuardar = findViewById(R.id.button2);

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
                if (checkGalleryPermission()) {
                    seleccionarFoto();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatosObjeto();
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
        }
        return true;
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
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageView.setImageURI(data.getData());
            }
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

        // Verificar si el nombre del objeto ya existe
        if (preferences.contains(nombre)) {
            Toast.makeText(this, "Ya existe un objeto con ese nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        // Guardar datos del objeto
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nombre, ubicacion);
        // También puedes guardar la ubicación y estantería si están presentes
        if (!estanteria.isEmpty()) {
            editor.putString(nombre + "_estanteria", estanteria);
        }
        editor.apply();

        // Limpiar campos después de guardar
        limpiarCampos();

        Toast.makeText(this, "Objeto agregado", Toast.LENGTH_SHORT).show();
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextUbicacion.setText("");
        editTextEstanteria.setText("");
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.editobj_info_image_720p)); // Puedes cambiar esto por la imagen predeterminada
    }
}
