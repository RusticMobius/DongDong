package com.example.dongdongapp.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
TODO:封装HTTP请求过程
 */
public class DongHTTPClient {

    private String result=null;

    /**
     * 进行GET请求
     * @param url 请求的url
     * @return 返回的响应的body部分（字符串形式）
     */
    public String doGet(String url){
        //String result=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                Call call=okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("dongHTTPClient","msg"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result=response.body().string();
                    }
                });
            }
        }).start();
        while (this.result.length()==0){
            try{
                TimeUnit.MILLISECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return  this.result;
    }

    /**
     * POST请求
     * @param url 目标url
     * @param form 需要POST的表单
     * @return
     */
    public String doPost(String url,FormBody form){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .post(form)
                        .build();

                Call call=okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("dongHTTPClient","msg"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result=response.body().string();
                    }
                });
            }
        }).start();
        while (this.result.length()==0){
            try{
                TimeUnit.MILLISECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return  this.result;
    }

    /**
     * POST请求（上传视频用）
     * @param url 目标url
     * @param form 需要POST的表单
     * @return
     */
    public String doPost(String url, MultipartBody form){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .post(form)
                        .build();

                Call call=okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("dongHTTPClient","msg"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result=response.body().string();
                    }
                });
            }
        }).start();
        while (this.result.length()==0){
            try{
                TimeUnit.MILLISECONDS.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return  this.result;
    }
}
