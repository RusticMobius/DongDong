package com.example.dongdongapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dongdongapp.R;
import com.example.dongdongapp.model.RecordModel;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

  private List<RecordModel> recordList;
  private Context context;
  private int userId;
  private int courseId;

  public RecordAdapter(Context context, int userId, int courseId){
    this.context = context;
    this.userId = userId;
    this.courseId = courseId;
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
    holder.recordDate.setText(item.getRecordDate());
    holder.recordAdvice.setText(item.getRecordAdvice());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("recordAdapter","clicked a record!");
        // TODO DO WE NEED a page for record detail ?
      }
    });
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
