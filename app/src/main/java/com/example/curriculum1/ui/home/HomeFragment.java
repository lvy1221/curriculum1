package com.example.curriculum1.ui.home;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.curriculum1.AddCourse;
import com.example.curriculum1.AddNote;
import com.example.curriculum1.AlarmBroadcastReceiver;
import com.example.curriculum1.Course;
import com.example.curriculum1.CourseAdapter;
import com.example.curriculum1.DataBaseHelper;
import com.example.curriculum1.MainActivity;
import com.example.curriculum1.WeekEdit;
import com.example.curriculum1.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment<val> extends Fragment {

    private FragmentHomeBinding binding;
    List<Course> courseList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        binding.date.setText(simpleDateFormat.format(date));
        binding.xingqi.setText(getWeek());
        View.OnClickListener onClickListener = null;
        onClickListener = v -> {
            Toast.makeText(getActivity(),"开始添加课程",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(getActivity(),AddCourse.class);
            startActivity(intent);
        };
        binding.add.setOnClickListener(onClickListener);
        View.OnClickListener onClickListener1 = null;
        onClickListener1 = v -> {
            Toast.makeText(getActivity(),"开始修改周数",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(getActivity(), WeekEdit.class);
            startActivity(intent);
        };
        binding.edit.setOnClickListener(onClickListener1);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.weekth.setText("第"+ MainActivity.numberWeek +"周");
        init();
        if (courseList.isEmpty()){                                                         // //判断今日有没有课程，没课程显示“今日没课”，反之显示课程。
            binding.todayClassList1.setVisibility(View.VISIBLE);
        }
        else {
            CourseAdapter adapter = new CourseAdapter(
                    getActivity(), android.R.layout.simple_list_item_1, courseList
            );
            binding.todayClassList.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getWeek(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if(i == 1)
            return "星期日";
        else if (i == 2)
            return "星期一";
        else if (i == 3)
            return "星期二";
        else if (i == 4)
            return "星期三";
        else if (i == 5)
            return "星期四";
        else if (i == 6)
            return "星期五";
        else if (i == 7)
            return "星期六";
        return null;
    }

    public int getWeekNumber(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        if(i == 1)
            return 7;
        else if (i == 2)
            return 1;
        else if (i == 3)
            return 2;
        else if (i == 4)
            return 3;
        else if (i == 5)
            return 4;
        else if (i == 6)
            return 5;
        else if (i == 7)
            return 6;
        return 0;
    }
    @SuppressLint("Range")
    public void init(){
        courseList.clear();
        DataBaseHelper dbHelper = new DataBaseHelper(getContext(), "course.db",null, 2);
        SQLiteDatabase writableDatabase=dbHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query("Courses",null,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                String courseName =cursor.getString(cursor.getColumnIndex("courseName"));
                String teacher =cursor.getString(cursor.getColumnIndex("teacher"));
                String classroom =cursor.getString(cursor.getColumnIndex("classroom"));
                int day =Integer.parseInt(cursor.getString(cursor.getColumnIndex("day")));
                int classStart =Integer.parseInt(cursor.getString(cursor.getColumnIndex("classStart")));
                int classEnd =Integer.parseInt(cursor.getString(cursor.getColumnIndex("classEnd")));
                int weekStart =Integer.parseInt(cursor.getString(cursor.getColumnIndex("weekStart")));
                int weekEnd =Integer.parseInt(cursor.getString(cursor.getColumnIndex("weekEnd")));
                if(day==getWeekNumber()&&weekStart<=MainActivity.numberWeek&&weekEnd>=MainActivity.numberWeek){
                    Course class1 = new Course(courseName,teacher,classroom,classStart,classEnd);
                    courseList.add(class1);
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    if(hour==0&&minute==0){
                        sendNotice(classStart);
                    }

                }
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void sendNotice(int classStart){
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = 0;
        int min = 0;
        if(classStart ==1){
            hour=7;
            min=40;
        }
        else if (classStart == 3){
            hour=9;
            min=20;
        }
        else if (classStart == 6){
            hour=13;
            min=20;
        }
        else if (classStart == 10){
            hour=18;
            min=10;
        }
        else{
            Toast.makeText(getActivity(),"课程数据错误",Toast.LENGTH_SHORT).show();
            return;
        }
        long secondsNextMorning = 0;
        try {
            secondsNextMorning = getSecondsNext(year, month, day, hour, min);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intentMorning = new Intent(getActivity(), AlarmBroadcastReceiver.class);
        intentMorning.setAction("CLOCK_IN");
        PendingIntent piMorning = PendingIntent.getBroadcast(getActivity(), 0, intentMorning, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP,secondsNextMorning,piMorning);
    }

    private long getSecondsNext(int year,int mon,int day,int hour,int min) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = dateFormat.parse("1970-01-01 00:00:00");
        String end = year+"-"+mon+"-"+day+" "+hour+":"+min+":00";
        Date endTime = dateFormat.parse(end);
        long diff = endTime.getTime() - startTime.getTime();
        return diff;
    }
}
