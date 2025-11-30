package com.quizzy;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.OutputStream;

public class PhotoActivity extends BaseActivity {

    private static final int CAMERA_PERMISSION_CODE = 1001;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_photo);

        checkCameraPermission();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE
            );
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                PreviewView previewView = findViewById(R.id.cameraPreview);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (Exception e) {
                Log.e("CameraX", "Error al iniciar la cámara", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    public void takePhotoButtonClicked(View view) {
        if (imageCapture == null) return;

        File photoFile = new File(getCacheDir(), "photo_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        processAndSaveImage(photoFile);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("CameraX", "Error al tomar la foto", exception);
                    }
                });
    }

    private void processAndSaveImage(File photoFile) {
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

        // --- 1. Cargamos el marco PNG ---
        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.marco);

        // --- 2. Escalamos el marco al tamaño exacto de la foto ---
        Bitmap scaledOverlay = Bitmap.createScaledBitmap(
                overlay,
                bitmap.getWidth(),
                bitmap.getHeight(),
                true
        );

        // --- 3. Creamos un bitmap final donde dibujar todo ---
        Bitmap finalBitmap = Bitmap.createBitmap(
                bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(finalBitmap);

        // 1️⃣ Dibuja la foto original
        canvas.drawBitmap(bitmap, 0, 0, null);

        // 2️⃣ Dibuja el marco encima
        canvas.drawBitmap(scaledOverlay, 0, 0, null);

        // 3️⃣ Escribe el texto del tiempo
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(bitmap.getWidth() * 0.05f); // Tamaño relativo
        paint.setShadowLayer(8f, 0f, 0f, Color.BLACK);
        paint.setAntiAlias(true);

        // Posición del texto (abajo a la derecha)
        String texto = "Tiempo: " + "03:00";
        float x = bitmap.getWidth() * 0.05f;
        float y = bitmap.getHeight() - bitmap.getHeight() * 0.05f;

        canvas.drawText(texto, x, y, paint);

        // Guardar la imagen final
        saveBitmapToGallery(finalBitmap);
    }

// ----------------------------------------------------------

    private void saveBitmapToGallery(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Photo_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Quizzy");

        try {
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            OutputStream out = getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            Toast.makeText(this, "Foto guardada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la foto", Toast.LENGTH_SHORT).show();
        }
    }
}
