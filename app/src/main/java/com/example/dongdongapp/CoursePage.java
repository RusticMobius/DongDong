package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

// 弃用的课程详情页，改用了底部有tabBar的页面

public class CoursePage extends AppCompatActivity {

    private TextView courseNameView;
    private TextView tipsView;
    private int userId;
    private int courseId;
    private String courseType;
    private ImageButton retButton;
    private Button recordButton;
    private Bundle recordActivityBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_course_page);

        retButton = findViewById(R.id.retButton);
        recordButton = findViewById(R.id.recordButton);

        courseNameView = findViewById(R.id.courseNameText);
        tipsView = findViewById(R.id.tipsText);
        tipsView.setMovementMethod(ScrollingMovementMethod.getInstance());

        courseNameView.setText(bundle.getString("courseName"));
        tipsView.setText(bundle.getString("courseTips"));
        courseId = bundle.getInt("courseId");
        userId = bundle.getInt("userId");
        courseType = bundle.getString("courseType");
        initRecordIntentBundle();
        initButtonListener();
    }

    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(CoursePage.this, MainActivity.class);
          CoursePage.this.startActivity(intent);
        }
      });

      recordButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          // Intent intent = new Intent(CoursePage.this, VideoRecordPage.class);
          // intent.putExtras(recordActivityBundle);
          // startActivityForResult(intent,1);

        }
      });
    }

    private void initRecordIntentBundle(){
      recordActivityBundle = new Bundle();
      recordActivityBundle.putInt("courseId", courseId);
      recordActivityBundle.putInt("userId", userId);
      recordActivityBundle.putString("courseType",courseType);

    }
}
