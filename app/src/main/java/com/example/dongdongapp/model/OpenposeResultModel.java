package com.example.dongdongapp.model;

public class OpenposeResultModel {
    public String uuid;
    public String type;
    public String status;
    public int progress;
    public AIResult result;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public AIResult getResult() {
        return result;
    }

    public void setResult(AIResult result) {
        this.result = result;
    }
}
