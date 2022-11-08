package com.example.dongdongapp.model;

public class CourseModel {
  //TODO
  // also need backend have a table for course? or can be done in frontend
  private int id, status;  //status for whether this course is collected
  private String courseName;
  private String courseDescription;
  private String courseImgUrl;

  public int getId() {
    return id;
  }

  public int getStatus() {
    return status;
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
