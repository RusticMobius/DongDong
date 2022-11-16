package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.util.DongHTTPClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AccountService {

    private final String backendBaseUrl=dongdongappConfiguration.backendUrl+"/user";

    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return 后端返回的json格式字符串
     */
    public String getLoginResult(String userName,String passWord){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",passWord);
        }catch (Exception e){
            e.printStackTrace();
        }


        RequestBody body=RequestBody.create(MediaType.parse("application/json;charset=utf8"),jsonObject.toString());


        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result = dongHTTPClient.doPost(backendBaseUrl+"/login",body);
        return result;
    }

    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return 用户id,若登录失败返回0或-1
     */
    public int login(String userName,String passWord){
        String resultStr=getLoginResult(userName,passWord);
        int userId=-1;
        try{
            JSONObject jsonObject=new JSONObject(resultStr);
            userId=(int) jsonObject.get("data");
        }catch (Exception e){
            e.printStackTrace();
        }

        return userId;
    }

    /**
     * 注册
     * @param userName 用户名
     * @param passWord 密码
     * @return 后端返回的json格式字符串
     */
    public String getRegisterResult(String userName,String passWord){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",passWord);
        }catch (Exception e){
            e.printStackTrace();
        }


        RequestBody body=RequestBody.create(MediaType.parse("application/json;charset=utf8"),jsonObject.toString());


        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result=dongHTTPClient.doPost(backendBaseUrl+"/register",body);
        return result;
    }

    /**
     * 注册
     * @param userName 用户名
     * @param passWord 密码
     * @return 用户id,若登录失败返回0或-1
     */
    public int register(String userName,String passWord){
        String resultStr=getRegisterResult(userName,passWord);
        int userId=-1;
        try{
            JSONObject jsonObject=new JSONObject(resultStr);
            userId=(int) jsonObject.get("data");
        }catch (Exception e){
            e.printStackTrace();
        }

        return userId;
    }

    /**
     * 修改密码
     * @param userName 用户名
     * @param passWord 新密码
     * @return 后端返回的json格式字符串
     */
    @Deprecated
    public String changePassword(String userName,String passWord){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",passWord);
        }catch (Exception e){
            e.printStackTrace();
        }


        RequestBody body=RequestBody.create(MediaType.parse("application/json;charset=utf8"),jsonObject.toString());


        DongHTTPClient dongHTTPClient=new DongHTTPClient();

        String result=dongHTTPClient.doPost(backendBaseUrl+"/changePassword",body);
        return result;
    }

}
