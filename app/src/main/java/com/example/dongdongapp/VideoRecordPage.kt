package com.example.dongdongapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import android.util.Log
import android.view.View
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.PermissionChecker
import com.example.dongdongapp.databinding.ActivityVideoRecordPageBinding
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale

typealias LumaListener = (luma: Double) -> Unit


class VideoRecordPage : AppCompatActivity() {
    private lateinit var viewBinding: ActivityVideoRecordPageBinding
    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityVideoRecordPageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

      // Request camera permissions
      if (allPermissionsGranted()) {
        startCamera()
      } else {
        ActivityCompat.requestPermissions(
          this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
      }

      // Set up the listeners for take photo and video capture buttons
      // viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }
      viewBinding.recordButton.setOnClickListener{ captureVideo() }
      viewBinding.retButton.setOnClickListener { retToParentPage() }
      viewBinding.commitButton.setOnClickListener { uploadVideo() }
      cameraExecutor = Executors.newSingleThreadExecutor()
    }


  private fun retToParentPage(){
    //TODO
    finish()
  }

  private fun uploadVideo(){
    // TODO
  }

  // Implements VideoCapture use case, including start and stop capturing.
  private fun captureVideo() {


    // 检查是否已创建 VideoCapture 用例：如果尚未创建，则不执行任何操作
    val videoCapture = this.videoCapture ?: return

    // 在 CameraX 完成请求操作之前，停用界面；在后续步骤中，它会在我们的已注册的 VideoRecordListener 内重新启用
    viewBinding.recordButton.isEnabled = false

    // 如果有正在进行的录制操作，请将其停止并释放当前的 recording。当所捕获的视频文件可供我们的应用使用时，我们会收到通知
    val curRecording = recording
    if (curRecording != null) {
      // Stop the current recording session.
      curRecording.stop()
      recording = null
      return
    }


    // create and start a new recording session
    val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINESE)
      .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
      put(MediaStore.MediaColumns.DISPLAY_NAME, name)
      // TODO
      // filetype
      put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
      }
    }

    // 使用外部内容选项创建 MediaStoreOutputOptions.Builder
    val mediaStoreOutputOptions = MediaStoreOutputOptions
      .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
      .setContentValues(contentValues)
      .build()


    // 将输出选项配置为 VideoCapture<Recorder> 的 Recorder 并启用录音
    recording = videoCapture.output
      .prepareRecording(this, mediaStoreOutputOptions)
      .apply {
        // 在此录音中启用音频
        if (PermissionChecker.checkSelfPermission(this@VideoRecordPage,
            Manifest.permission.RECORD_AUDIO) ==
          PermissionChecker.PERMISSION_GRANTED)
        {
          withAudioEnabled()
        }
      }
      .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
        when(recordEvent) {
          is VideoRecordEvent.Start -> {
            viewBinding.recordButton.apply {
              setImageResource(R.drawable.ic_baseline_pause_circle_outline_48)
              isEnabled = true
            }
          }
          is VideoRecordEvent.Finalize -> {
            if (!recordEvent.hasError()) {
              val msg = "Video capture succeeded: " +
                "${recordEvent.outputResults.outputUri}"
              Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                .show()
              Log.d(TAG, msg)
            } else {
              recording?.close()
              recording = null
              Log.e(TAG, "Video capture ends with error: " +
                "${recordEvent.error}")
            }
            viewBinding.recordButton.apply {
              setImageResource(R.drawable.ic_baseline_stop_circle_48)
              isEnabled = true
            }
          }
        }
      }
  }

  private fun startCamera() {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

    cameraProviderFuture.addListener({


      // Used to bind the lifecycle of cameras to the lifecycle owner
      val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

      // Preview
      val preview = Preview.Builder()
        .build()
        .also {
          it.setSurfaceProvider(viewBinding.previewView.surfaceProvider)
        }
      // Analyzer
      val imageAnalyzer = ImageAnalysis.Builder()
        .build()
        .also {
          it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
            Log.d("imageAnalyzer", "Average luminosity: $luma")
          })
        }
      // VideoCapture
      val recorder = Recorder.Builder()
        .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
        .build()
      videoCapture = VideoCapture.withOutput(recorder)

      // Select back camera as a default
      val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

      try {
        // Unbind use cases before rebinding
        cameraProvider.unbindAll()

        // Bind use cases to camera
        cameraProvider.bindToLifecycle(
          this, cameraSelector, preview, videoCapture)

      } catch(exc: Exception) {
        Log.e(TAG, "Use case binding failed", exc)
      }

    }, ContextCompat.getMainExecutor(this))
  }

  private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
      baseContext, it) == PackageManager.PERMISSION_GRANTED
  }

  override fun onDestroy() {
    super.onDestroy()
    cameraExecutor.shutdown()
  }

  companion object {
    private const val TAG = "CameraXApp"
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS =
      mutableListOf (
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
      ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
          add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
      }.toTypedArray()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<String>, grantResults:
    IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      if (allPermissionsGranted()) {
        startCamera()
      } else {
        Toast.makeText(this,
          "Permissions not granted by the user.",
          Toast.LENGTH_SHORT).show()
        finish()
      }
    }
  }

  private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
      rewind()    // Rewind the buffer to zero
      val data = ByteArray(remaining())
      get(data)   // Copy the buffer into a byte array
      return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

      val buffer = image.planes[0].buffer
      val data = buffer.toByteArray()
      val pixels = data.map { it.toInt() and 0xFF }
      val luma = pixels.average()

      listener(luma)

      image.close()
    }
  }

}
