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
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayer extends AppCompatActivity {

    private SurfaceView videoPlaySurfaceView;
    private ImageButton playButton;
    private ImageButton redoButton;
    private ImageButton uploadButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView currentTime;
    private TextView totalTime;
    private SurfaceHolder surfaceHolder;
    private Uri videoUri;

    private Timer timer;  //定时器
    private boolean isSeekbarOnChange; //互斥变量，防止进度条和定时器冲突。
    private boolean isInitFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        redoButton = findViewById(R.id.redoButton);
        uploadButton = findViewById(R.id.uploadButton);
        playButton = findViewById(R.id.playButton);
        videoPlaySurfaceView = findViewById(R.id.videoSurfaceView);

        seekBar = findViewById(R.id.seekBar);
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);


        videoUri = getIntent().getData();
        Log.d("checkUri", String.valueOf(videoUri));

        mediaPlayer = new MediaPlayer();
        timer = new Timer();

        initButtonListener();
        initSeekBarListener();
        initSurfaceHolder();
    }

    private void initSeekBarListener(){
      seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
          // int duration = mediaPlayer.getDuration(); //total time
          int position = mediaPlayer.getCurrentPosition();  //start time
          currentTime.setText(calculateTime(position / 1000));
          // totalTime.setText(calculateTime(duration / 1000));
          startTimer();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
          isSeekbarOnChange = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
          isSeekbarOnChange = false;
          mediaPlayer.seekTo(seekBar.getProgress());
          currentTime.setText(calculateTime(mediaPlayer.getCurrentPosition() / 1000));
        }
      });
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
        mediaPlayer.setLooping(false);//设置循环播放
        mediaPlayer.prepareAsync();//异步准备
        // mMediaPlayer.prepare();//同步准备,因为是同步在一些性能较差的设备上会导致UI卡顿
        mediaPlayer.setOnPreparedListener(
          new MediaPlayer.OnPreparedListener() { //准备完成回调
          @Override
          public void onPrepared(MediaPlayer mp) {
            isInitFinish = true;
            int duration = mediaPlayer.getDuration(); //total time
            int position = mediaPlayer.getCurrentPosition();  //start time
            seekBar.setMax(duration);
            currentTime.setText(calculateTime(position / 1000));
            totalTime.setText(calculateTime(duration / 1000));

          }
        });
      } catch (IOException e) {
        e.printStackTrace();
      }
      mediaPlayer.setOnCompletionListener(
        new MediaPlayer.OnCompletionListener() {
          @Override
          public void onCompletion(MediaPlayer mp) {
            playButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_48);
          }
        }
      );


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

    private void startTimer(){
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          if(!isSeekbarOnChange){
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
          }
        }
      },0,500);
    }

  //计算播放时间
  private String calculateTime(int time){
    int minute;
    int second;
    if(time > 60){
      minute = time / 60;
      second = time % 60;
      //分钟再0~9
      if(minute >= 0 && minute < 10){
        //判断秒
        if(second >= 0 && second < 10){
          return "0"+minute+":"+"0"+second;
        }else {
          return "0"+minute+":"+second;
        }
      }else {
        //分钟大于10再判断秒
        if(second >= 0 && second < 10){
          return minute+":"+"0"+second;
        }else {
          return minute+":"+second;
        }
      }
    }else if(time < 60){
      second = time;
      if(second >= 0 && second < 10){
        return "00:"+"0"+second;
      }else {
        return "00:"+ second;
      }
    }
    return null;
  }

}
