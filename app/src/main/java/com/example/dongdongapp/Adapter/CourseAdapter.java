package com.example.dongdongapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dongdongapp.CoursePage;
import com.example.dongdongapp.CourseRecordPage;
import com.example.dongdongapp.MainActivity;
import com.example.dongdongapp.R;
import com.example.dongdongapp.model.CourseModel;

import com.bumptech.glide.Glide;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

  private List<CourseModel> CourseList;
  private Context context;
  private int userId;

  public CourseAdapter(Context context, int userId){
    this.context = context;
    this.userId = userId;
  }
  @NonNull
  @Override
  public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // here we need to create a row item layout file
    View view = LayoutInflater.from(context).inflate(R.layout.course_row_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
    //here we will bind data to our layout
    CourseModel item = CourseList.get(position);
    holder.courseName.setText(item.getCourseName());
    holder.courseDescription.setText(item.getCourseDescription());
    // TODO 动态加载课程图片
    // Glide.with(context).load(item.getCourseImgUrl());

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d("courseAdapter","clicked a course, let enter exercise!");
        //TODO
        // turn to course Page
//        Intent i = new Intent(context, VideoPlayer.class);
//        i.putExtra("url", videoLessionList.get(position).getVideoUrl());
//        context.startActivity(i);
        Bundle bundle = new Bundle();
        bundle.putInt("courseId", item.getId());
        bundle.putInt("userId", userId);
        bundle.putString("courseName",item.getCourseName());
        bundle.putString("courseTips",item.getTips());
        Intent intent = new Intent((Activity)context, CourseRecordPage.class);
        intent.putExtras(bundle);
        context.startActivity(intent);


      }
    });

  }

  @Override
  public int getItemCount() {
    return CourseList.size();
  }

  public void setCourseList(List<CourseModel> courseList) {
    CourseList = courseList;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    ImageButton collectButton;
    TextView courseName;
    TextView courseDescription;
    ImageView courseImg;

    ViewHolder(View view){
      super(view);
      courseName = view.findViewById(R.id.courseNameText);
      courseDescription = view.findViewById(R.id.courseDescriptionText);
      courseImg = view.findViewById(R.id.courseImg);
      collectButton = view.findViewById(R.id.collectButton);
    }
  }
}
