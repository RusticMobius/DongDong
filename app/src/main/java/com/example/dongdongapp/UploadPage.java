package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.dongdongapp.service.VideoService;
import com.example.dongdongapp.util.RealPathUtil;

public class UploadPage extends AppCompatActivity {

    private int userId;
    private int courseId;
    private String courseType;
    private Uri videoUri;
    private VideoService videoService ;


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_page);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("userId");
        courseId = bundle.getInt("courseId");
        courseType = bundle.getString("courseType");
        videoUri = Uri.parse(bundle.getString("videoUri"));
        videoService = new VideoService();
        Log.d("uploadInfo",courseType + " " + userId );
        uploadVideo();
    }
    private void uploadVideo(){
      String videoPath = RealPathUtil.getRealPath(getApplicationContext(),videoUri);
      new Thread(new Runnable() {
        @Override
        public void run() {
          // videoService.uploadVideo(videoPath, courseType, userId);
        }
      }).start();
    }
}