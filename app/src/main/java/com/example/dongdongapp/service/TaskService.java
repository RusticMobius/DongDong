package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.model.AnalyseResultModel;
import com.example.dongdongapp.model.OpenposeResultModel;
import com.example.dongdongapp.util.DongHTTPClient;
import com.google.gson.Gson;

import org.json.JSONObject;

public class TaskService {
    final String openposeUrl= dongdongappConfiguration.openposeUrl;
    final String backendUrl=dongdongappConfiguration.backendUrl+"/task";

    /**
     * 从AI端获取返回的结果（暂时不要用这个函数先）
     * @param uuid 任务的uuid
     * @return
     */
    @Deprecated
    public OpenposeResultModel getResult(String uuid){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String resultJsonStr=dongHTTPClient.doGet(openposeUrl+"/v1/result/{"+uuid+"}");
        Gson gson=new Gson();
        OpenposeResultModel openposeResult=new OpenposeResultModel();
        try {
            JSONObject jsonObject=new JSONObject(resultJsonStr);
            openposeResult.setUuid((String) jsonObject.get("uuid"));
            openposeResult.setType((String) jsonObject.get("type"));
            openposeResult.setStatus((String) jsonObject.get("status"));
            if(jsonObject.has("progress")){
                openposeResult.setProgress((int) jsonObject.get("progress"));
            }
            if(jsonObject.has("result")){
                //TODO:处理AI返回的结果
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return openposeResult;
    }

    /**
     * 获取分析结果
     * @param videoId 视频id
     * @return 分析结果（详见AnalyseResultModel或参考后端接口文档）
     */
    public AnalyseResultModel getAnalyseResult(int videoId){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doGet(backendUrl+"/get/{"+videoId+"}");
        AnalyseResultModel resultModel=new AnalyseResultModel();
        try{
            JSONObject jsonObject=new JSONObject(result);
            JSONObject jsonObject1=(JSONObject) jsonObject.get("data");
            if(jsonObject1.has("advice"))  resultModel.advice=(String) jsonObject1.get("advice");
            if(jsonObject1.has("data"))  resultModel.data=(String) jsonObject1.get("data");
            if(jsonObject1.has("fileAddress")) resultModel.fileAddress=(String) jsonObject1.get("fileAddress");
            resultModel.taskId=(int) jsonObject1.get("taskId");
            resultModel.progress=(int) jsonObject1.get("progress");
            resultModel.setStatus((String) jsonObject1.get("status"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * 对视频进行分析
     * @param videoId 视频id
     * @return 是否成功以及相应信息
     */
    public String analyseVideoData(int videoId){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doGet(backendUrl+"/analysis/{"+videoId+"}");
        return result;
    }

    /**
     * 对视频进行分析
     * @param videoId 视频id
     * @return 是否成功
     */
    public boolean analyseVideo(int videoId){
        String res=analyseVideoData(videoId);
        boolean success=false;
        try {
            JSONObject jsonObject=new JSONObject(res);
            int status=(int) jsonObject.get("status");
            if (status==1) success=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }


}
