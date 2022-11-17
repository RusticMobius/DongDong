package com.example.dongdongapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dongdongapp.AnalyzePage;
import com.example.dongdongapp.R;
import com.example.dongdongapp.RecordViewPage;
import com.example.dongdongapp.model.RecordModel;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

  private List<RecordModel> recordList;
  private Context context;
  private int userId;
  private int courseId;
  private String courseType;
  private Bundle bundle;

  public RecordAdapter(Context context, Bundle bundle){
    this.bundle = bundle;
    this.context = context;
    this.userId = bundle.getInt("userId");
    this.courseId = bundle.getInt("courseId");
    this.courseType = bundle.getString("courseType");
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.record_row_layout, parent, false);
    return new RecordAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    RecordModel item = recordList.get(position);

    holder.recordDate.setText(item.getRecordDate());

    if (item.isAnalyzeFinish()){
      holder.recordRank.setText(item.getRecordRank());

      switch (item.getRecordRank()){
        case "A":
          holder.recordRank.setTextColor(Color.parseColor("#25E4B8"));
          break;
        case "A+":
          holder.recordRank.setTextColor(Color.parseColor("#25E4B8"));
          break;
        case "B":
          holder.recordRank.setTextColor(Color.parseColor("#A363DC"));
          break;
        case "C":
          holder.recordRank.setTextColor(Color.parseColor("#FFEB3B"));
          break;
        default:
          break;
      }

      holder.recordAdvice.setText(item.getRecordAdvice());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d("recordAdapter","clicked a record!");
          // TODO DO WE NEED a page for record detail ?
          Intent intent = new Intent(context, RecordViewPage.class);
          bundle.putString("rank",item.getRecordRank());
          bundle.putString("analysis",item.getRecordAdvice());
          bundle.putString("videoUrl",item.getVideoUrl());
          intent.putExtras(bundle);
          context.startActivity(intent);
        }
      });
    } else {
      holder.recordRank.setText("?");
      holder.recordRank.setTextColor(Color.parseColor("#FFBB86FC"));
      holder.recordAdvice.setText("The record is still under analysis");
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Log.d("recordAdapter","clicked a record!");
          // TODO DO WE NEED a page for record detail ?
          Intent intent = new Intent(context, AnalyzePage.class);
          bundle.putBoolean("toFinish", true);
          intent.putExtras(bundle);
          context.startActivity(intent);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return recordList.size();
  }

  public void setRecordModelList(List<RecordModel> recordModelList){
    recordList = recordModelList;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView recordRank;
    TextView recordDate;
    TextView recordAdvice;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      recordRank = itemView.findViewById(R.id.recordRank);
      recordDate = itemView.findViewById(R.id.recordDateText);
      recordAdvice = itemView.findViewById(R.id.recordAdvice);
    }

  }
}
