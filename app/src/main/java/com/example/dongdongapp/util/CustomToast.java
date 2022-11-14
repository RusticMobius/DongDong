package com.example.dongdongapp.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongdongapp.R;

public class CustomToast {
  private TextView msgView;

  public Toast createToast(Context context,int layout_id, String msg){
    Toast toast = new Toast(context);
    View layoutView = View.inflate(context, layout_id,null);
    msgView = layoutView.findViewById(R.id.toastMsg);
    msgView.setText(msg);
    // toast.setGravity(Gravity.CENTER, 0, 0);
    return toast;
  }
}
