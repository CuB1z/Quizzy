package com.quizzy;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.exifinterface.media.ExifInterface;

import com.google.common.util.concurrent.ListenableFuture;
import com.quizzy.config.Constants;
import com.quizzy.utils.SoundPlayer;

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
    public void onRequestPermissionsResult(
        int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_SHORT).show();
                restartGame(null);
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
                System.exit(0);
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
                    // Do nothing
                }
            });
    }

    private Bitmap fixOrientation(String path, Bitmap bitmap) {
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            );

            Matrix matrix = new Matrix();

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                case ExifInterface.ORIENTATION_UNDEFINED:
                    if (bitmap.getWidth() > bitmap.getHeight()) {
                        matrix.postRotate(90);
                    }
                    break;
                default:
                    return bitmap;
            }

            return Bitmap.createBitmap(
                bitmap,
                0, 0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,
                true
            );

        } catch (Exception e) {
            return bitmap;
        }
    }

    private void processAndSaveImage(File photoFile) {
        Bitmap photoBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        photoBitmap = fixOrientation(photoFile.getAbsolutePath(), photoBitmap);

        // Load the overlay frame from resources
        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.marco);

        // Scale overlay to match photo dimensions
        Bitmap scaledOverlay = Bitmap.createScaledBitmap(
            overlay,
            photoBitmap.getWidth(),
            photoBitmap.getHeight(),
            true
        );

        // Create a new bitmap with the same dimensions as the photo
        Bitmap finalBitmap = Bitmap.createBitmap(
            photoBitmap.getWidth(),
            photoBitmap.getHeight(),
            Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(finalBitmap);

        // Draw the original photo
        canvas.drawBitmap(photoBitmap, 0, 0, null);

        // Draw the overlay frame
        canvas.drawBitmap(scaledOverlay, 0, 0, null);

        // Add time to the photo
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        paint.setTextSize(photoBitmap.getWidth() * 0.05f);
        paint.setShadowLayer(8f, 0f, 0f, Color.BLACK);
        paint.setAntiAlias(true);

        String timerValue = getIntent().getStringExtra(Constants.TIMER_VALUE);
        String text = String.format("%s", timerValue);

        float x = photoBitmap.getWidth() * 0.5f - paint.measureText(text) * 0.5f;
        float y = photoBitmap.getHeight() - photoBitmap.getHeight() * 0.25f;

        canvas.drawText(text, x, y, paint);

        // Save final bitmap to gallery
        saveBitmapToGallery(finalBitmap);
        restartGame(null);

    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Photo_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Quizzy");

        try {
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            assert uri != null;

            OutputStream out = getContentResolver().openOutputStream(uri);
            assert out != null;

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            Toast.makeText(this, "Foto guardada en /DCIM/Quizzy", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar la foto", Toast.LENGTH_SHORT).show();
        }
    }
    public void restartGame(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
        startActivity(intent, options.toBundle());
        finish();
    }
}



