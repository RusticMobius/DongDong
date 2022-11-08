package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.model.VideoItemModel;
import com.example.dongdongapp.util.DongFTPClient;
import com.example.dongdongapp.util.DongHTTPClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VideoService {

    private final String backendUrl= dongdongappConfiguration.backendUrl+"/video";
    private final String localVideoPath=dongdongappConfiguration.localVideoUrl;
    private final String ftpVideoPath=dongdongappConfiguration.ftpBasePath;
    private final String ftpHost=dongdongappConfiguration.ftpHost;
    private final int ftpPort=dongdongappConfiguration.ftpPort;

    /**
     * 上传拍摄的运动视频
     * @param fileName 文件名
     * @param type 运动类型
     * @param uid 用户id
     * @return 后端返回的json格式字符串
     */
    public String uploadVideo(String fileName,String type,int uid){
        File file=new File(localVideoPath+"/"+fileName);
        RequestBody requestBody=RequestBody.create(MediaType.parse("video/*"),file);
        MultipartBody formBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),requestBody)
                .addFormDataPart("type",type)
                .addFormDataPart("uid",uid+"")
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doPost(backendUrl,formBody);
        return result;
    }

    /**
     * 从FTP服务器下载所有本地没有的视频
     * @param userParam 用户参数（预留）
     * @return 是否成功
     */
    public boolean downloadMyVideos(String userParam){
        boolean isSuccess=false;
        DongFTPClient dongFTPClient=new DongFTPClient(ftpHost,ftpPort);
        dongFTPClient.connectFTPServer();
        isSuccess=dongFTPClient.downloadAllUnExistFile(localVideoPath,ftpVideoPath);
        return isSuccess;
    }

    /**
     * 获取用户历史视频列表（TODO:开发中）
     * @param uid 用户id
     */
    @Deprecated
    public void getVideoList(int uid){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String res=dongHTTPClient.doGet(backendUrl+"/getVideos/{"+uid+"}");
        //TODO:应用一个model来处理返回数据（没有就自己造！）
        Gson gson=new Gson();
        ArrayList<VideoItemModel> videoList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(res);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
