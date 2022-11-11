package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.dongdongapp.Adapter.CourseAdapter;
import com.example.dongdongapp.model.CourseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageButton userButton;
    private RecyclerView courseRecyclerView;
    private List<CourseModel> courseList;
    private CourseAdapter courseAdapter;
    private boolean isLogged;
    // TODO ABOUT USERID
    private int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   //隐藏最上部帮助栏

        courseList = new ArrayList<>();
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter = new CourseAdapter(this, userId);

        courseList = new ArrayList<>();

        // fake data
        // TODO read courseList from ...

        for(int i = 0; i < 5; i++){
          CourseModel fakeCourse = new CourseModel();
          fakeCourse.setCourseName("FAKE COURSE " + i );
          fakeCourse.setCourseDescription("a fake course description for frontend test");
          fakeCourse.setStatus(0);
          courseList.add(fakeCourse);
          Random random = new Random();
          int tipsNum = 1 + random.nextInt(7);
          String tips = "";
          for(int j = 0; j < tipsNum; j++ ){
            tips += "Tips" + (j+1) + ": this is a tips for course" + i + "阿斯顿法国红酒快乐；\n\n\n";
          }
          fakeCourse.setTips(tips);


        }

        courseAdapter.setCourseList(courseList);
        courseRecyclerView.setAdapter(courseAdapter);

        //TODO CONTROL LOG LOGICAL

    }

    public int getUserId(){
      return userId;
    }
}
