package com.example.dongdongapp.model;

public class RecordModel implements Comparable<RecordModel> {
  // TODO reverse 1-5 to rank ABC
  // 5-A+ 4-A 3-B 2,1-C
  private String recordRank;
  // TODO construct format just like "in 2022.10.28"
  private String recordDate;
  private String recordAdvice;

  // TODO use this key to sort record list. latest in top or use timestamp
  private int recordId;

  // TODO
  //  which course is this record belong to
  private int courseId;
  private int userId;

  private final String[] rankTable={"C","C","B","A","A+","A+"};

  public int getRecordId() {
    return recordId;
  }

  public int getCourseId() {
    return courseId;
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

  public void setCourseId(int courseId) {
    this.courseId = courseId;
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
