package com.example.curriculum1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.curriculum1.databinding.ActivityAddCourseBinding;

public class AddCourse extends AppCompatActivity {

    private ActivityAddCourseBinding binding;
    DataBaseHelper dbHelper = new DataBaseHelper(AddCourse.this, "course.db",null, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View.OnClickListener onClickListener;
        onClickListener = v -> {
            String courseName = binding.courseName.getText().toString();
            String teacher = binding.teacherName.getText().toString();
            String classroom = binding.classroom.getText().toString();
            String day = binding.day.getText().toString();
            String classStart =binding.classStart.getText().toString();
            String classEnd = binding.classEnd.getText().toString();
            String weekStart = binding.weekStart.getText().toString();
            String weekEnd = binding.weekEnd.getText().toString();
            if (courseName.isEmpty() || teacher.isEmpty() || classroom.isEmpty()||day.isEmpty()||classStart.isEmpty()||classEnd.isEmpty()||weekStart.isEmpty()||weekEnd.isEmpty()) {
                Toast.makeText(AddCourse.this, "存在内容数据为空，请完整输入数据。", Toast.LENGTH_SHORT).show();
            }
            else{
                SQLiteDatabase writableDatabase=dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("courseName",courseName);
                values.put("teacher",teacher);
                values.put("classroom",classroom);
                values.put("day",Integer.parseInt(day));
                values.put("classStart",Integer.parseInt(classStart));
                values.put("classEnd",Integer.parseInt(classEnd));
                values.put("weekStart",Integer.parseInt(weekStart));
                values.put("weekEnd",Integer.parseInt(weekEnd));
                writableDatabase.insert("Courses",null,values);
                Toast.makeText(AddCourse.this, "课程添加成功。", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        binding.courseAdd.setOnClickListener(onClickListener);
    }
}