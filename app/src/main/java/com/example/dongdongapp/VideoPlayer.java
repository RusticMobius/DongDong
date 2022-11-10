package com.example.dongdongapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class VideoPlayer extends AppCompatActivity {

    private SurfaceView videoPlaySurfaceView;
    private ImageButton playButton;
    private ImageButton redoButton;
    private ImageButton uploadButton;
    private MediaPlayer mediaPlayer;
    private boolean isInitFinish = false;
    private SurfaceHolder surfaceHolder;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        redoButton = findViewById(R.id.redoButton);
        uploadButton = findViewById(R.id.uploadButton);
        playButton = findViewById(R.id.playButton);
        videoPlaySurfaceView = findViewById(R.id.videoSurfaceView);

        videoUri = getIntent().getData();
        Log.d("checkUri", String.valueOf(videoUri));

        mediaPlayer = new MediaPlayer();

        initButtonListener();
        initSurfaceHolder();
    }


    private void initSurfaceHolder() {
      surfaceHolder = videoPlaySurfaceView.getHolder();
      surfaceHolder.addCallback(new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
          mediaPlayer.setDisplay(holder);
          // TODO implement the method
          setPlayVideo();
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
          Log.e("SurfaceChanged", "surfaceChanged: width=" + width + "height" + height);
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

        }
      });
    }

    private void initButtonListener(){

      redoButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          retToRedo();
        }
      });

      uploadButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          uploadVideo();
        }
      });

      playButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(mediaPlayer.isPlaying()){
            pausePlay();
            playButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_48);
          }else {
            startPlay();
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_48);
          }
        }
      });
    }

    private void retToRedo(){
      //TODO
      finish();
    }

    private void uploadVideo(){
      //TODO
    }

    private void setPlayVideo(){
      // TODO
      try {
        mediaPlayer.setDataSource(this.getApplicationContext(),videoUri);
        mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);//缩放模式
        mediaPlayer.setLooping(true);//设置循环播放
        mediaPlayer.prepareAsync();//异步准备
        // mMediaPlayer.prepare();//同步准备,因为是同步在一些性能较差的设备上会导致UI卡顿
        mediaPlayer.setOnPreparedListener(
          new MediaPlayer.OnPreparedListener() { //准备完成回调
          @Override
          public void onPrepared(MediaPlayer mp) {
            isInitFinish = true;
            mp.start();
          }
        });
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void startPlay(){
      if (!mediaPlayer.isPlaying()){
        mediaPlayer.start();
      }
    }

    private void stopPlay(){
      if (mediaPlayer.isPlaying()){
        mediaPlayer.stop();
      }
    }

    private void pausePlay(){
      if (mediaPlayer.isPlaying()){
        mediaPlayer.pause();
      }
    }

    private void seekTo(int time){
      mediaPlayer.seekTo(time);
    }

    @Override
    protected void onDestroy() {
      super.onDestroy();
      if (mediaPlayer != null){
        if (mediaPlayer.isPlaying()){
          mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
      }
    }

}
