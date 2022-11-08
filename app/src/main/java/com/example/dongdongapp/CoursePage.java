package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CoursePage extends AppCompatActivity {

    private TextView courseNameView;
    private TextView tipsView;
    private String allTips;
    private String courseName;
    private ImageButton retButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_course_page);

        retButton = findViewById(R.id.retButton);

        courseNameView = findViewById(R.id.courseNameText);
        tipsView = findViewById(R.id.tipsText);
        tipsView.setMovementMethod(ScrollingMovementMethod.getInstance());

        courseNameView.setText(bundle.getString("courseName"));
        tipsView.setText(bundle.getString("courseTips"));



        retButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(CoursePage.this, MainActivity.class);
            CoursePage.this.startActivity(intent);
          }
        });
    }
}
