package com.example.dongdongapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class VideoRecordPageJAVA extends AppCompatActivity implements ImageAnalysis.Analyzer {

  private PreviewView previewView;
  private Activity mActivity;

  private ImageButton retButton;
  private ImageButton recordButton;
  private ImageButton commitButton;

  private VideoCapture videoCapture;

  private boolean isRecording;

  private ListenableFuture<ProcessCameraProvider> listenableFuture;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video_record_page);
    mActivity = this;


    previewView = findViewById(R.id.previewView);
    retButton = findViewById(R.id.retButton);
    recordButton = findViewById(R.id.recordButton);
    commitButton = findViewById(R.id.reverseButton);
    initButton();

    listenableFuture = ProcessCameraProvider.getInstance(this);
    listenableFuture.addListener(() -> {
      try {
        ProcessCameraProvider cameraProvider = listenableFuture.get();
        startCameraX(cameraProvider);
      } catch (ExecutionException | InterruptedException e) {
        e.printStackTrace();
      }
    }, ContextCompat.getMainExecutor(this));

  }



  @SuppressLint("RestrictedApi")
  private void startCameraX(ProcessCameraProvider cameraProvider) {
    cameraProvider.unbindAll();
    CameraSelector cameraSelector = new CameraSelector.Builder()
      .requireLensFacing(CameraSelector.LENS_FACING_BACK)
      .build();

    Preview preview = new Preview.Builder()
      .build();

    preview.setSurfaceProvider(previewView.getSurfaceProvider());

    // Video capture use case
    videoCapture = new VideoCapture.Builder()
      .setVideoFrameRate(30)
      .build();

    // Image analysis use case
//    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
//      .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//      .build();
//
//    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), this);

    //bind to lifecycle:
    cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, videoCapture);
  }


  @Override
  public void analyze(@NonNull ImageProxy image) {
    // image processing here for the current frame
    Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
    image.close();
  }

  private void initButton() {

    retButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    recordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!isRecording) {
          isRecording = true;
          startRecorder();
          recordButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_48);
        } else {
          isRecording = false;
          stopRecorder();
          recordButton.setImageResource(R.drawable.ic_baseline_play_circle_48);
        }
      }
    });

    commitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO update video
      }
    });
  }
  @SuppressLint("RestrictedApi")
  private void startRecorder() {
    if (videoCapture != null) {

      long timestamp = System.currentTimeMillis();

      ContentValues contentValues = new ContentValues();
      contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
      contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

      try {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 1);
          //return;
        }
        videoCapture.startRecording(
          new VideoCapture.OutputFileOptions.Builder(
            getContentResolver(),
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            contentValues
          ).build(),
          ContextCompat.getMainExecutor(this),
          new VideoCapture.OnVideoSavedCallback() {
            @Override
            public void onVideoSaved(@NonNull VideoCapture.OutputFileResults outputFileResults) {
              Toast.makeText(mActivity, "Video has been saved successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
              Toast.makeText(mActivity, "Error saving video: " + message, Toast.LENGTH_SHORT).show();
            }
          }
        );
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }


  @SuppressLint("RestrictedApi")
  private void stopRecorder() {
    if (videoCapture != null) {
      videoCapture.stopRecording();
    }
  }
}
