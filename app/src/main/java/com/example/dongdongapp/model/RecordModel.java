package com.example.dongdongapp.model;

public class RecordModel implements Comparable<RecordModel> {
  // TODO reverse 1-5 to rank ABC
  // 5-A+ 4-A 3-B 2,1-C
  private String recordRank;  // data 里的评分 1～5分
  // TODO construct format just like "in 2022.10.28"
  private String recordDate;  //  返回里的createTime，需要转换成2022.10.01这样
  private String recordAdvice;  // advice


  // TODO use this key to sort record list. latest in top or use timestamp
  private int recordId;   //  taskId


  // TODO
  //  which course is this record belong to
  private String courseType;  //t ype
  private int userId;   //  uid

  private String videoUrl;  // fileAddress

  private boolean isAnalyzeFinish;  // status RUNNING : false FINISH : true

  private final String[] rankTable={"C","C","B","A","A+","A+"};


  public boolean isAnalyzeFinish() {
    return isAnalyzeFinish;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public int getRecordId() {
    return recordId;
  }

  public String getCourseType() {
    return courseType;
  }


  public int getUserId() {
    return userId;
  }

  public String getRecordRank() {
    return recordRank;
  }

  public String getRecordDate() {
    return recordDate;
  }

  public String getRecordAdvice() {
    return recordAdvice;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setRecordDate(String recordDate) {
    this.recordDate = recordDate;
  }

  public void setRecordRank(String recordRank) {
    this.recordRank = recordRank;
  }

  public void setRecordId(int recordId) {
    this.recordId = recordId;
  }

  public void setRecordAdvice(String recordAdvice) {
    this.recordAdvice = recordAdvice;
  }

  @Override
  public int compareTo(RecordModel o) {
    if(this.recordId > o.recordId){
      return -1;
    }else{
      return 0;
    }
  }

  /**
   * 根据得分生成等级
   * @param score 得分
   */
  public void setRank(int score){
    if(score>=1&&score<=5){
      this.recordRank=this.rankTable[score-1];
    }
  }

  /**
   * 根据得分生成等级
   * @param score 得分
   */
  public void setRank(double score){
    if(score>=1&&score<=5){
      this.recordRank=this.rankTable[(int) (Math.round(score)-1)];
    }
  }
}
