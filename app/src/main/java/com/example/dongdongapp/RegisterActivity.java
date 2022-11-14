package com.example.dongdongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongdongapp.service.AccountService;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private ImageButton retButton;
    private EditText userNameText;
    private EditText passwordText;
    private EditText repeatPasswordText;
    private TextView loginLink;
    private AccountService accountService;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountService = new AccountService();
        registerButton = findViewById(R.id.registerButton);
        retButton = findViewById(R.id.retButton);
        userNameText = findViewById(R.id.editTextUserName);
        passwordText = findViewById(R.id.editTextPassword);
        repeatPasswordText = findViewById(R.id.editTextRepeatPassword);
        loginLink = findViewById(R.id.loginText);
        context = RegisterActivity.this;
        initButtonListener();
        initLink();

    }

    private void initButtonListener(){
      retButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO
          Intent intent = new Intent(context, MainActivity.class);
          startActivity(intent);
        }
      });

      registerButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String userName = userNameText.getText().toString();
          String userNameCheck = userName.replaceAll(" ","");
          String password = passwordText.getText().toString();
          String repeatPassword = repeatPasswordText.getText().toString();
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
          } else if (repeatPassword.length() == 0){
            msg = "Please enter the password again";
            toastView = getLayoutInflater().inflate(R.layout.info_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast.setView(toastView);
            toast.show();
          }else if (userNameCheck.length() == 0){
            msg = "Name can not be empty!";
            toastView = getLayoutInflater().inflate(R.layout.warning_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast = new Toast(context);

            toast.setView(toastView);

            toast.show();
          }
          else if (password.contains(" ")){
            msg = "Password cannot contain spaces!";
            toastView = getLayoutInflater().inflate(R.layout.warning_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast = new Toast(context);

            toast.setView(toastView);

            toast.show();
          }
          else if (! password.equals(repeatPassword)){
            msg = "The two entered passwords do not match!";
            toastView = getLayoutInflater().inflate(R.layout.warning_toast_layout, null);
            msgText = toastView.findViewById(R.id.toastMsg);
            msgText.setText(msg);
            toast = new Toast(context);

            toast.setView(toastView);

            toast.show();
          } else {
            // accountService.register(userName, password);
          }
        }
      });
    }

    private void initLink(){
      loginLink.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, LoginActivity.class);
          startActivity(intent);
        }
      });
    }

    private boolean registerThread(String userName, String password){
      // TODO
      new Thread(new Runnable() {
        @Override
        public void run() {
          accountService.register(userName, password);
        }
      }) ;
      return false;
    }
}
