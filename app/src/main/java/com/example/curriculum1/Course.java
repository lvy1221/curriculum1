package com.example.curriculum1;

public class Course implements Cloneable {
    int id;//课程id
    String courseName;//课程名
    String teacher;//老师名字
    String classroom;//上课教室
    int day; //星期几
    int classStart; //开始节数
    int classEnd;  //结束节数
    int weekStart; //结束周次
    int weekEnd; //开始周次

    public Course(String courseName,String teacher, String classroom,int classStart,int classEnd){
        this.courseName=courseName;
        this.teacher=teacher;
        this.classroom=classroom;
        this.classStart=classStart;
        this.classEnd=classEnd;
    }
}