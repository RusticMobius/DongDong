package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.dongdongapp.Adapter.CourseAdapter;
import com.example.dongdongapp.model.CourseModel;
import com.example.dongdongapp.util.RealPathUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton userButton;
    private RecyclerView courseRecyclerView;
    private List<CourseModel> courseList;
    private CourseAdapter courseAdapter;
    private Context context;
    private SharedPreferences userNameSp;
    private SharedPreferences.Editor spEditor;

    // TODO ABOUT USERID sharedPreference
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   //隐藏最上部帮助栏
        context = this;
        courseList = new CourseModel().getCourseModelList();
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userButton = findViewById(R.id.userButton);
        userId = getSharedPreferences("loginInfo",Context.MODE_PRIVATE).getInt("userId",userId);
        Log.d("checkUserId", String.valueOf(userId));
        courseAdapter = new CourseAdapter(this, userId);
        initButtonListener();
        courseAdapter.setCourseList(courseList);
        courseRecyclerView.setAdapter(courseAdapter);

        if (userId == -1){
          Intent intent = new Intent(this, LoginActivity.class);
          this.startActivity(intent);
        }

        UriConverterTest();


        //TODO CONTROL LOG LOGICAL

    }

    private void initButtonListener(){
      userButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          showPopupMenu(userButton);
        }
      });
    }

    private void showPopupMenu(View view){
      // 这里的view代表popupMenu需要依附的view
      PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
      // 获取布局文件
      popupMenu.getMenuInflater().inflate(R.menu.login_service_layout, popupMenu.getMenu());
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        popupMenu.setForceShowIcon(true);
      }
      popupMenu.show();
      // 通过上面这几行代码，就可以把控件显示出来了
      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
          Intent intent;

          switch (item.getItemId()){
            case R.id.logoutMenuItem:
            case R.id.toggleMenuItem:
              userNameSp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
              spEditor = userNameSp.edit();
              spEditor.putInt("userId",-1);
              spEditor.apply();
              intent = new Intent(context,LoginActivity.class);
              startActivity(intent);
              break;
            case R.id.registerMenuItem:
              intent = new Intent(context,RegisterActivity.class);
              startActivity(intent);
              break;
          }
          return true;
        }
      });
      popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
        @Override
        public void onDismiss(PopupMenu menu) {
          // 控件消失时的事件
        }
      });


    }

    public int getUserId(){
      return userId;
    }

    private void UriConverterTest(){
      Uri uri = Uri.parse("content://media/external/video/media/1000000046");
      RealPathUtil realPathUtil = new RealPathUtil();
      String path = RealPathUtil.getRealPath(context,uri);
      Log.d("parseUri",path);
    }
}
