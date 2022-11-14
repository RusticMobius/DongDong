package com.example.dongdongapp.model;

import com.example.dongdongapp.enumeration.State;

public class AnalyseResultModel {
    public int taskId;
    public String advice;
    public String data;
    public String fileAddress;
    public boolean isCompleted;
    public int progress;
    public State status;

    public void setStatus(String status){
        if(status.equals("RUNNING")){
            this.status=State.RUNNING;
        }
        if(status.equals("FINISHED")){
            this.status=State.FINISHED;
        }
    }
}
