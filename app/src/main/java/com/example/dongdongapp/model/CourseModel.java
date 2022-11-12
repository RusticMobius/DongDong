package com.example.dongdongapp.model;

public class CourseModel {
  //TODO
  // also need backend have a table for course? or can be done in frontend
  private int id, status;  // 请求的服务类型，比如仰卧起坐对应的那个端口号？status忽略
  private String courseName;  // 比如仰卧起坐
  private String courseDescription; // 描述一下对仰卧起坐的动作分析能干什么
  private String courseImgUrl;  // 忽略，可以前端指定
  private String tips;  // 描述仰卧起坐的动作建议
  private final String[] exerciseTypeTable= {
          "VIDEO_ HIGHKNEES","VIDEO_ CRUNCH", "VIDEO_ STANDINGLONGJUMP",
          "VIDEO_ MULTIJUMP","VIDEO_ SITANDREACH","VIDEO_ RUNBACKANDFORTH",
          "VIDEO_ TENNISTHROW","VIDEO_ BALANCEBEAM","VIDEO_ SINGLELEGSTAND",
          "VIDEO_ PLANK","VIDEO_ SQUAT"
  };//注意课程id和运动类型的对应关系！举例：id=2则运动类型为exerciseTypeTable[2]，即"VIDEO_ STANDINGLONGJUMP"
  //TODO:如果需要对应的中文表单的话可自行编写或联系我（191250100陆健成）



  public int getId() {
    return id;
  }



  public int getStatus() {
    return status;
  }


  public String getTips() {
    return tips;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getCourseDescription() {
    return courseDescription;
  }

  public String getCourseImgUrl() {
    return courseImgUrl;
  }

  public String getExerciseType(){
    return exerciseTypeTable[id];
  }

  public void setTips(String tips) {
    this.tips = tips;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setCourseImgUrl(String courseImgUrl) {
    this.courseImgUrl = courseImgUrl;
  }
}
