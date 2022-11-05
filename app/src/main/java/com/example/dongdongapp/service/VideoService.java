package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.util.DongFTPClient;
import com.example.dongdongapp.util.DongHTTPClient;

import java.io.File;

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
     * @return 后端返回的json格式字符串
     */
    public String uploadVideo(String fileName,String type){
        File file=new File(localVideoPath+"/"+fileName);
        RequestBody requestBody=RequestBody.create(MediaType.parse("video/*"),file);
        MultipartBody formBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),requestBody)
                .addFormDataPart("type",type)
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doPost(backendUrl,formBody);
        return result;
    }

    public boolean downloadMyVideos(String userParam){
        boolean isSuccess=false;
        DongFTPClient dongFTPClient=new DongFTPClient(ftpHost,ftpPort);
        dongFTPClient.connectFTPServer();
        isSuccess=dongFTPClient.downloadAllUnExistFile(localVideoPath,ftpVideoPath);
        return isSuccess;
    }

}
