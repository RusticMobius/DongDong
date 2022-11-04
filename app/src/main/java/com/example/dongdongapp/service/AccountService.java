package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.util.DongHTTPClient;

import okhttp3.FormBody;

public class AccountService {

    private final String backendBaseUrl=dongdongappConfiguration.backendUrl+"/user";

    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return 后端返回的json格式字符串
     */
    public String login(String userName,String passWord){
        FormBody formBody=new FormBody.Builder()
                .add("username",userName)
                .add("password",passWord)
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result=dongHTTPClient.doPost(backendBaseUrl+"/login",formBody);
        return result;
    }

    /**
     * 注册
     * @param userName 用户名
     * @param passWord 密码
     * @return 后端返回的json格式字符串
     */
    public String register(String userName,String passWord){
        FormBody formBody=new FormBody.Builder()
                .add("username",userName)
                .add("password",passWord)
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result=dongHTTPClient.doPost(backendBaseUrl+"/register",formBody);
        return result;
    }

    /**
     * 修改密码
     * @param userName 用户名
     * @param passWord 新密码
     * @return 后端返回的json格式字符串
     */
    public String changePassword(String userName,String passWord){
        FormBody formBody=new FormBody.Builder()
                .add("username",userName)
                .add("password",passWord)
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result=dongHTTPClient.doPost(backendBaseUrl+"/changePassword",formBody);
        return result;
    }

}
