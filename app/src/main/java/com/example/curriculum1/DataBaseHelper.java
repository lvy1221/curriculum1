package com.example.curriculum1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_TABLE="create table Courses("+
            "id integer primary key autoincrement,"+
            "courseName text,"+
            "teacher text,"+
            "classroom text,"+
            "day integer,"+
            "classStart integer,"+
            "classEnd integer,"+
            "weekStart integer,"+
            "weekEnd integer)";

    public static final String CREATE_TABLE2="create table Notes("+
            "id integer primary key autoincrement,"+
            "courseName text,"+
            "words text,"+
            "year integer,"+
            "mon integer,"+
            "day integer,"+
            "hour integer,"+
            "min integer)";

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Courses");
        sqLiteDatabase.execSQL("drop table if exists Notes");
        onCreate(sqLiteDatabase);
    }
}