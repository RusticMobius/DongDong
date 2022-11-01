package com.example.dongdongapp.service;

import com.example.dongdongapp.config.dongdongappConfiguration;
import com.example.dongdongapp.model.OpenposeResultModel;
import com.example.dongdongapp.util.DongHTTPClient;
import com.google.gson.Gson;

import org.json.JSONObject;

public class OpenposeService {
    final String openposeUrl= dongdongappConfiguration.openposeUrl;
    final String backendUrl=dongdongappConfiguration.backendUrl+"/task";

    /**
     * 从AI端获取返回的结果
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

    public void getAnalyseResult(int videoId){
        DongHTTPClient dongHTTPClient=new DongHTTPClient();
        String result=dongHTTPClient.doGet(backendUrl+"/analysis/{"+videoId+"}");
        //TODO:处理结果
    }
}
