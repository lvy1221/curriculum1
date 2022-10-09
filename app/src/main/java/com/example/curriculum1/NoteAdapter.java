package com.example.curriculum1;

import android.annotation.SuppressLint;
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
public class NoteAdapter extends ArrayAdapter<Notes> {
    public NoteAdapter(@NonNull Context context, int resource, @NonNull List<Notes> objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notes notes=getItem(position);//得到当前项的 Course 实例
        //为每一个子项加载设定的布局
        View view=LayoutInflater.from(getContext()).inflate(R.layout.note_item,parent,false);
        //分别获取 image view 和 textview 的实例
        TextView course=view.findViewById(R.id.course);
        TextView time =view.findViewById(R.id.time);
        // 设置要显示的图片和文字
        course.setText(notes.courseName+":  ");
        time.setText(notes.year+"年"+notes.mon+"月"+notes.day+"日"+notes.hour+":"+ notes.min+":00");
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(1500,200);
        view.setLayoutParams(param);
        return view;
    }
}