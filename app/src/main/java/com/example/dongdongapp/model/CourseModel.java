package com.example.dongdongapp.model;

public class CourseModel {
  //TODO
  // also need backend have a table for course? or can be done in frontend
  private int id, status;  // 请求的服务类型，比如仰卧起坐对应的那个端口号？status忽略
  private String courseName;  // 比如仰卧起坐
  private String courseDescription; // 描述一下对仰卧起坐的动作分析能干什么
  private String courseImgUrl;  // 忽略，可以前端指定
  private String tips;  // 描述仰卧起坐的动作建议



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
