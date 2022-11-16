package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AnalyzePage extends AppCompatActivity {

    private ImageButton retButton;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_page);
        retButton = findViewById(R.id.retButton);
        bundle = getIntent().getExtras();
        initButtonListener();

    }
    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(bundle.getBoolean("toFinish", true)){
            finish();
          } else {
          Intent intent = new Intent(getApplicationContext(),CourseRecordPage.class);
          intent.putExtras(bundle);
          startActivity(intent);
          }
        }
      });
    }


}
