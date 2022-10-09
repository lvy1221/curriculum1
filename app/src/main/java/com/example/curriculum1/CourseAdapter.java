package com.example.curriculum1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//用于将上下文、listview 子项布局的 id 和数据都传递过来
public class CourseAdapter extends ArrayAdapter<Course> {
    public CourseAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Course course=getItem(position);//得到当前项的 Course 实例
        //为每一个子项加载设定的布局
        View view=LayoutInflater.from(getContext()).inflate(R.layout.today_course_item,parent,false);
        //分别获取 image view 和 textview 的实例
        TextView courseName =view.findViewById(R.id.courseName);
        TextView teacherName =view.findViewById(R.id.teacherName);
        TextView courseTime =view.findViewById(R.id.courseTime);
        TextView classroom =view.findViewById(R.id.classroom);
        // 设置要显示的图片和文字
        courseName.setText(course.courseName+"•");
        teacherName.setText(course.teacher);
        courseTime.setText("第"+course.classStart+"-"+course.classEnd+"节");
        classroom.setText(course.classroom);
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(300,200);
        view.setLayoutParams(param);
        return view;
    }
}