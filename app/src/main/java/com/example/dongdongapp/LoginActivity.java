package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongdongapp.service.AccountService;
import com.example.dongdongapp.util.CustomToast;

// 登陆界面

public class LoginActivity extends AppCompatActivity {
    private ImageButton retButton;
    private Button loginButton;
    private EditText userNameText;
    private EditText passwordText;
    private TextView registerLink;
    private AccountService accountService;
    private Context context;
    private SharedPreferences userNameSp;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountService = new AccountService();
        retButton = findViewById(R.id.retButton);
        loginButton = findViewById(R.id.loginButton);
        userNameText = findViewById(R.id.editTextUserName);
        passwordText = findViewById(R.id.editTextPassword);
        registerLink = findViewById(R.id.registerText);
        context = LoginActivity.this;
        initButtonListener();
        initLink();


    }
    private void initButtonListener(){
      loginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String userName = userNameText.getText().toString();
          String password = passwordText.getText().toString();

          String msg = "";
          Toast toast = new Toast(context);
          toast.setDuration(Toast.LENGTH_SHORT);

          View toastView;
          TextView msgText;

          // TODO test Thread
          loginThread(userName,password);

        }
      });
    }

    private void initLink(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO
          Intent intent = new Intent(context, MainActivity.class);
          startActivity(intent);
        }
      });
      registerLink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, RegisterActivity.class);
          startActivity(intent);
        }
      });
    }

    // TODO

    private void loginThread(String userName, String password){
      new Thread(new Runnable() {
        @Override
        public void run() {
          int userId = accountService.login(userName,password);
          Log.d("loginTest", String.valueOf(userId));
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              View toastView;
              TextView msgText;
              LayoutInflater inflater = LayoutInflater.from(context);
              Toast toast = new Toast(context);
              toast.setDuration(Toast.LENGTH_SHORT);

              if (userId != -1){
                toastView = inflater.inflate(R.layout.success_toast_layout, null);
                msgText = toastView.findViewById(R.id.toastMsg);
                msgText.setText("login succeed！");
                userNameSp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
                spEditor = userNameSp.edit();
                spEditor.putInt("userId",userId);
                spEditor.apply();
                toast.setView(toastView);
                toast.show();

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);

              } else {
                toastView = inflater.inflate(R.layout.error_toast_layout, null);
                msgText = toastView.findViewById(R.id.toastMsg);
                msgText.setText("login failed!");
                toast.setView(toastView);
                toast.show();
              }

            }
          });
        }
      }).start();
    }

}
