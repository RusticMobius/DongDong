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

public class MainActivity extends AppCompatActivity {
    private ImageButton userButton;
    private RecyclerView courseRecyclerView;
    private List<CourseModel> courseList;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   //隐藏最上部帮助栏

        courseList = new ArrayList<>();
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter = new CourseAdapter(this);

        courseList = new ArrayList<>();

        // fake data
        // TODO read courseList from ...
        CourseModel fakeCourse = new CourseModel();
        for(int i = 0; i < 5; i++){
          fakeCourse.setCourseName("FAKE COURSE " + i);
          fakeCourse.setCourseDescription("a fake course description for frontend test");
          fakeCourse.setStatus(0);
          courseList.add(fakeCourse);

        }

        courseAdapter.setCourseList(courseList);
        courseRecyclerView.setAdapter(courseAdapter);




    }
}
