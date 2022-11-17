package com.example.dongdongapp.service;

import android.util.Log;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.model.CourseModel;
import com.example.dongdongapp.model.RecordModel;
import com.example.dongdongapp.model.VideoItemModel;
import com.example.dongdongapp.util.DongFTPClient;
import com.example.dongdongapp.util.DongHTTPClient;
import com.google.gson.Gson;

import org.json.JSONArray;
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
     * @return 视频信息（详见VideoItemModel或参考后端接口文档）
     */
    public int uploadVideo(String fileName,String type,int uid){
        // File file=new File(localVideoPath+"/"+fileName);
        File file = new File(fileName);
        RequestBody requestBody=RequestBody.create(MediaType.parse("video/*"),file);
        MultipartBody formBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", file.getName(),requestBody)
                .addFormDataPart("type",type)
                .addFormDataPart("uid",uid+"")
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doPost(backendUrl + "/upload",formBody);
        VideoItemModel videoItemModel=new VideoItemModel();
        videoItemModel.video=file;
        try {
            JSONObject object=new JSONObject(result);
            videoItemModel.videoId=(int) object.get("videoId");
            videoItemModel.videoAddress=(String) object.get("videoAddress");
            videoItemModel.coverAddress=(String) object.get("coverAddress");
            videoItemModel.createTime=(String) object.get("createTime");
            videoItemModel.isAnalysis=(String) object.get("isAnalysis");
            videoItemModel.taskId=(int) object.get("taskId");
            videoItemModel.type=(String) object.get("type");
            videoItemModel.uid=(int) object.get("uid");
            videoItemModel.uuid=(String) object.get("uuid");
            Log.d("uploadVideoTest",(String) object.get("status"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return videoItemModel.taskId;
    }

    /**
     * 上传拍摄的运动视频
     * @param file 视频文件
     * @param type 运动类型
     * @param uid 用户id
     * @return 视频信息（详见VideoItemModel或参考后端接口文档）
     */
    public VideoItemModel uploadVideo(File file,String type,int uid){
        RequestBody requestBody=RequestBody.create(MediaType.parse("video/*"),file);
        MultipartBody formBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", file.getName(),requestBody)
                .addFormDataPart("type",type)
                .addFormDataPart("uid",uid+"")
                .build();

        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doPost(backendUrl+"/upload",formBody);
        VideoItemModel videoItemModel=new VideoItemModel();
        videoItemModel.video=file;
        try {
            JSONObject object=new JSONObject(result);
            videoItemModel.videoId=(int) object.get("videoId");
            videoItemModel.videoAddress=(String) object.get("videoAddress");
            videoItemModel.coverAddress=(String) object.get("coverAddress");
            videoItemModel.createTime=(String) object.get("createTime");
            videoItemModel.isAnalysis=(String) object.get("isAnalysis");
            videoItemModel.taskId=(int) object.get("taskId");
            videoItemModel.type=(String) object.get("type");
            videoItemModel.uid=(int) object.get("uid");
            videoItemModel.uuid=(String) object.get("uuid");
        }catch (Exception e){
            e.printStackTrace();
        }
        return videoItemModel;
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
     * 获取用户历史视频列表
     * @param uid 用户id
     * @return 视频列表
     */
    public ArrayList<VideoItemModel> getVideoList(int uid){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String res=dongHTTPClient.doGet(backendUrl+"/getVideos/"+uid);
        Gson gson=new Gson();
        ArrayList<VideoItemModel> videoList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(res);
            JSONArray jsonArray=(JSONArray) jsonObject.get("data");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=(JSONObject) jsonArray.get(i);
                VideoItemModel videoItemModel=new VideoItemModel();
                videoItemModel.videoId=(int) object.get("videoId");
                videoItemModel.videoAddress=(String) object.get("videoAddress");
                videoItemModel.coverAddress=(String) object.get("coverAddress");
                videoItemModel.createTime=(String) object.get("createTime");
                videoItemModel.isAnalysis=(String) object.get("isAnalysis");
                videoItemModel.taskId=(int) object.get("taskId");
                videoItemModel.type=(String) object.get("type");
                videoItemModel.uid=(int) object.get("uid");
                videoItemModel.uuid=(String) object.get("uuid");
                videoList.add(videoItemModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return videoList;
    }

    /**
     * 根据VideoId获取视频对象
     * @param videoId 视频id
     * @return 视频对象（参考VideoItemModel或后端文档）
     */
    public VideoItemModel getVideoById(int videoId){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String res=dongHTTPClient.doGet(backendUrl+"/getVideo/"+videoId);
        VideoItemModel videoItemModel=new VideoItemModel();
        try {
            JSONObject object=new JSONObject(res);
            videoItemModel.videoId=(int) object.get("videoId");
            videoItemModel.videoAddress=(String) object.get("videoAddress");
            videoItemModel.coverAddress=(String) object.get("coverAddress");
            videoItemModel.createTime=(String) object.get("createTime");
            videoItemModel.isAnalysis=(String) object.get("isAnalysis");
            videoItemModel.taskId=(int) object.get("taskId");
            videoItemModel.type=(String) object.get("type");
            videoItemModel.uid=(int) object.get("uid");
            videoItemModel.uuid=(String) object.get("uuid");
        }catch (Exception e){
            e.printStackTrace();
        }
        videoItemModel.video=new File(getVideo(videoItemModel.videoAddress));
        return videoItemModel;
    }

    /**
     * 根据视频在ftp服务器上的地址下载视频（视频地址在相应VideoItemModel的videoAddress属性里）
     * @param videoAddress 视频在ftp服务器上的地址
     * @return 本地视频名（包括路径，示例：local/video/src/video.mp4）
     */
    public String getVideo(String videoAddress){
        String localVideoSrc="";
        String fileName=getFileName(videoAddress);
        DongFTPClient ftpClient=new DongFTPClient();
        ftpClient.connectFTPServer();
        ftpClient.downloadFileByName(localVideoPath,videoAddress,fileName);
        localVideoSrc=localVideoPath+"/"+fileName;
        return localVideoSrc;
    }

    /**
     * 根据缩略图在ftp服务器上的地址下载缩略图（缩略图地址在相应VideoItemModel的coverAddress属性里）
     * @param coverAddress 缩略图在ftp服务器上的地址
     * @return 本地缩略图名（包括路径，示例：local/cover/src/cover.jpg）
     */
    public String getCover(String coverAddress){
        String localVideoSrc="";
        String fileName=getFileName(coverAddress);
        DongFTPClient ftpClient=new DongFTPClient();
        ftpClient.connectFTPServer();
        ftpClient.downloadFileByName(localVideoPath,coverAddress,fileName);
        localVideoSrc=localVideoPath+"/"+fileName;
        return localVideoSrc;
    }

    /**
     * 将视频列表中的每一个视频项与相应的视频绑定（绑定到video属性上）
     * @param videoList 视频列表
     * @return 完成绑定的视频列表
     */
    public ArrayList<VideoItemModel> fulfillVideoItemList(ArrayList<VideoItemModel> videoList){
        for (VideoItemModel videoItemModel:videoList){
            String videoFileName=getVideo(videoItemModel.videoAddress);
            videoItemModel.video=new File(videoFileName);
        }
        return videoList;
    }

    /**
     * 根据运动类型获取运动记录
     * @param uid 用户id
     * @param type 运动类型
     * @return 运动记录列表
     */
    public ArrayList<RecordModel> getRecordByType(int uid,String type){
        ArrayList<RecordModel> recordModelArrayList=new ArrayList<>();
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String res=dongHTTPClient.doGet(backendUrl+"/getVideoByType/"+uid+"/"+type);
        try {
            JSONObject jsonObject=new JSONObject(res);
            JSONArray jsonArray=(JSONArray) jsonObject.get("data");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=(JSONObject) jsonArray.get(i);
                RecordModel recordModel=new RecordModel();
                recordModel.setRecordDate(parseRecordDate((String) object.get("createTime")));
                if (object.has("advice"))
                    recordModel.setRecordAdvice((String) object.get("advice"));
                recordModel.setCourseType((String) object.get("type"));
                recordModel.setUserId((int) object.get("uid"));
                recordModel.setRecordId((int) object.get("taskId"));
                if(object.has("data"))
                    recordModel.setRank(parseScore((String) object.get("data")));
                recordModel.setVideoUrl((String) object.get("fileAddress"));
                recordModel.setStatus((String) object.get("status"));
                recordModelArrayList.add(recordModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return recordModelArrayList;
    }

    private String getFileName(String filePath){
        String[] params=filePath.split("/");
        return params[params.length-1];
    }

    private String parseRecordDate(String createTime){
        String[] params1=createTime.split(" ");
        return params1[0].replace("-",".");
    }

    private int parseScore(String dataStr){
        //TODO:将data内的内容转化为分数（逻辑待验证）
        String dataContent=dataStr
                .replace("[","")
                .replace("]","")
                .replace("{","")
                .replace("}","");
        String scoreContent=dataContent.split(",")[0];
        return Integer.parseInt(scoreContent.split("=")[1]);
    }

}
