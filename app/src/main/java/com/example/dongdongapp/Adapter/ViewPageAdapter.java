package com.example.dongdongapp.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dongdongapp.CourseRecordPage;
import com.example.dongdongapp.fragment.CourseFragment;
import com.example.dongdongapp.fragment.RecordFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

  private CourseFragment courseFragment = new CourseFragment();
  private RecordFragment recordFragment = new RecordFragment();


  public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
    CourseRecordPage activity = (CourseRecordPage) fragmentActivity;
    courseFragment.setArguments(activity.getCourseBundle());
    recordFragment.setArguments(activity.getRecordBundle());
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    switch (position) {
      case 1:
        return recordFragment;
      default:
        return courseFragment;
    }
  }

  @Override
  public int getItemCount() {
    return 2;
  }

}
