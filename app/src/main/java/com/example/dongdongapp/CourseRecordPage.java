package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.dongdongapp.Adapter.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class CourseRecordPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    ViewPageAdapter viewPageAdapter;
    private int courseId;
    private int userId;
    private String allTips;
    private String courseName;
    private String courseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_record_page);

        Bundle bundle = getIntent().getExtras();
        courseId = bundle.getInt("courseId");
        userId = bundle.getInt("userId");
        courseName = bundle.getString("courseName");
        allTips = bundle.getString("courseTips");
        courseType = bundle.getString("courseType");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPage);

        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
            viewPager2.setCurrentItem(tab.getPosition());
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
          }

          @Override
          public void onPageSelected(int position) {
            super.onPageSelected(position);
            tabLayout.getTabAt(position).select();
          }

          @Override
          public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
          }
        });
    }
    public Bundle getCourseBundle(){
      Bundle bundle = new Bundle();
      bundle.putInt("courseId",courseId);
      bundle.putString("courseName",courseName);
      bundle.putString("courseTips",allTips);
      bundle.putInt("userId",userId);
      bundle.putString("courseType",courseType);
      return bundle;
    }

    public Bundle getRecordBundle(){
      Bundle bundle = new Bundle();
      bundle.putInt("courseInt",courseId);
      bundle.putInt("userId",userId);
      bundle.putString("courseName",courseName);
      bundle.putString("courseType",courseType);
      bundle.putString("courseTips",allTips);
      return bundle;
    }
}
