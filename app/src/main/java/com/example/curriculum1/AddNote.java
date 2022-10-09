package com.example.curriculum1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.curriculum1.databinding.ActivityAddNoteBinding;

public class AddNote extends AppCompatActivity {

    private ActivityAddNoteBinding binding;
    DataBaseHelper dbHelper = new DataBaseHelper(AddNote.this, "course.db",null, 2);
    int year;
    int month;
    int dayOfMonth;
    int hour;
    int min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.timepicker.setIs24HourView(true);
        binding.date.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            //设置日期简略显示 否则详细显示 包括:星期\周
            datePicker.setCalendarViewShown(false);
            //设置date布局
            builder.setView(view);
            builder.setTitle("选择出生日期");
            builder.setPositiveButton("确 定", (dialog, which) -> {
                //日期格式
                int year1 = datePicker.getYear();
                year =year1;
                int month1 = datePicker.getMonth()+1;
                month=month1;
                int dayOfMonth1 = datePicker.getDayOfMonth();
                dayOfMonth=dayOfMonth1;
                binding.date.setText(year+"年"+month+"月"+dayOfMonth+"日");
                dialog.cancel();
            });
            builder.setNegativeButton("取 消", (dialog, which) -> dialog.cancel());
            builder.create().show();
        });


        View.OnClickListener onClickListener;
        onClickListener = v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hour = binding.timepicker.getHour();
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                min = binding.timepicker.getMinute();
            }
            String course = binding.title.getText().toString();
            String words = binding.words.getText().toString();
            if (course.isEmpty()||words.isEmpty()){
                Toast.makeText(AddNote.this, "存在内容数据为空，请完整输入数据。", Toast.LENGTH_SHORT).show();
            }
            else {
                    SQLiteDatabase writableDatabase=dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("courseName",course);
                    values.put("words",words);
                    values.put("year",year);
                    values.put("mon",month);
                    values.put("day",dayOfMonth);
                    values.put("hour",hour);
                    values.put("min",min);
                    writableDatabase.insert("Notes",null,values);
                    Toast.makeText(AddNote.this, "笔记添加成功。", Toast.LENGTH_SHORT).show();
                    finish();
            }
        };
        binding.button.setOnClickListener(onClickListener);
    }
}