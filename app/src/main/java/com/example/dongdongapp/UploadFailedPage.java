package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UploadFailedPage extends AppCompatActivity {

    private ImageButton retButton;
    private ImageButton retryButton;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_failed_page);
        retButton = findViewById(R.id.retButton);
        retryButton = findViewById(R.id.uploadButton);
        bundle = getIntent().getExtras();
        initButtonListener();
    }

    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(getApplicationContext(), CourseRecordPage.class);
          intent.putExtras(bundle);
          startActivity(intent);
        }
      });
      retryButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(getApplicationContext(), UploadPage.class);
          intent.putExtras(bundle);
          startActivity(intent);
        }
      });
    }

}
