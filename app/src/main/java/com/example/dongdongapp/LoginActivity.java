package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


          if (userName.length() == 0){
            msg = "Please enter your username";
            toastView = getLayoutInflater().inflate(R.layout.info_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast.setView(toastView);
            toast.show();
          } else if(password.length() == 0){
            msg = "Please enter your password";
            toastView = getLayoutInflater().inflate(R.layout.info_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast.setView(toastView);
            toast.show();
          } else {
            // loginThread(userName,password);
            int userId = 0;
            userNameSp = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
            spEditor = userNameSp.edit();
            spEditor.putInt("userId",userId);
            spEditor.apply();

          }
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
    private String loginThread(String userName, String password){
      new Thread(new Runnable() {
        @Override
        public void run() {
          accountService.login(userName,password);
        }
      });
      return null;
    }

}
