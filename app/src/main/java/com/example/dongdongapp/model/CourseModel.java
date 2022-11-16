package com.example.dongdongapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CourseModel {
  //TODO
  // also need backend have a table for course? or can be done in frontend
  private int id, status;  // 请求的服务类型，比如仰卧起坐对应的那个端口号？status忽略
  private String courseName;  // 比如仰卧起坐
  private String courseDescription; // 描述一下对仰卧起坐的动作分析能干什么
  private String courseImgUrl;  // 忽略，可以前端指定
  private String tips;  // 描述仰卧起坐的动作建议
  private String courseType;
  private final String[] courseTypeTable= {
          "VIDEO_HIGHKNEES", //0
          "VIDEO_CRUNCH",  //1
          "VIDEO_STANDINGLONGJUMP", //2
          "VIDEO_MULTIJUMP", //3
          "VIDEO_SITANDREACH", //4
          "VIDEO_RUNBACKANDFORTH", //5
          "VIDEO_TENNISTHROW", //6
          "VIDEO_BALANCEBEAM", //7
          "VIDEO_SINGLELEGSTAND", //8
          "VIDEO_PLANK", //9
          "VIDEO_SQUAT" //10
  };//注意课程id和运动类型的对应关系！举例：id=2则运动类型为exerciseTypeTable[2]，即"VIDEO_ STANDINGLONGJUMP"
  //TODO:如果需要对应的中文表单的话可自行编写或联系我（191250100陆健成）
  private final String[] courseTipsTable = {

  };

  private final String[] courseNameTable = {
    "High Knees", "Crunch", "Standing Long Jump","Multi-Jump",
    "Sit And Reach", "Run Back And Forth", "Tennis Throw",
    "Balance Beam", "Single Leg Stand", "Plank", "Squat"
  };

  private final String[] courseDescriptionTable = {

  };

  public int getId() {
    return id;
  }



  public int getStatus() {
    return status;
  }

  public String getCourseType(){
    return courseType;
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

  public void setCourseType(String courseType) {
    this.courseType = courseType;
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



  public List<CourseModel> getCourseModelList (){
    List<CourseModel> courseModelList = new ArrayList<>();
    // TODO finish description table and modify this method
    // TODO finish tips table and modify this method

    Random random = new Random();
    for(int i = 0; i < courseTypeTable.length; i++){
      CourseModel courseModel = new CourseModel();
      // TODO modify it
      String description = "In " + courseNameTable[i] + ", you can take a video of your exercise and upload it, we will analyze it and give you some exercise suggestions";
      int tipsNum = 1 + random.nextInt(7);
      String tips = "";
      for(int j = 0; j < tipsNum; j++ ){
        tips += "Tips" + (j+1) + ": this is a tips for " + courseNameTable[i] + "\n\n\n";
      }

      courseModel.setId(i);
      courseModel.setCourseType(courseTypeTable[i]);
      courseModel.setCourseName(courseNameTable[i]);
      courseModel.setCourseDescription(description);
      courseModel.setTips(tips);

      courseModelList.add(courseModel);
    }
    return courseModelList;
  }


}
