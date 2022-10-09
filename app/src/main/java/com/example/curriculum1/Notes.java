package com.example.curriculum1;

public class Notes {
    public int id;
    public String courseName;
    public String words;
    public int year;
    public int mon;
    public int day;
    public int hour;
    public int min;

    public Notes(int id,String courseName, String words, int year, int mon, int day, int hour, int min){
        this.id=id;
        this.courseName=courseName;
        this.words=words;
        this.year=year;
        this.mon=mon;
        this.day=day;
        this.hour=hour;
        this.min=min;
    }
}
