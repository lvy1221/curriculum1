package com.example.curriculum1.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.curriculum1.AddNote;
import com.example.curriculum1.DataBaseHelper;
import com.example.curriculum1.NoteAdapter;
import com.example.curriculum1.NoteSearch;
import com.example.curriculum1.Notes;
import com.example.curriculum1.R;
import com.example.curriculum1.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    List<Notes> noteList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        View.OnClickListener onClickListener2 = null;
        onClickListener2 = v -> {
            Toast.makeText(getActivity(),"开始新建笔记",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(getActivity(), AddNote.class);
            startActivity(intent);
        };
        binding.note.setOnClickListener(onClickListener2);
        binding.noteList.setOnItemClickListener((adapterView, view, i, l) -> showNormalDialog(i));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        NoteAdapter adapter = new NoteAdapter(getActivity(), android.R.layout.simple_list_item_1, noteList);
        binding.noteList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("Range")
    public void init(){
        noteList.clear();
        DataBaseHelper dbHelper = new DataBaseHelper(getContext(), "course.db",null, 2);
        SQLiteDatabase writableDatabase=dbHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query("Notes",null,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                int id =Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String courseName =cursor.getString(cursor.getColumnIndex("courseName"));
                String words =cursor.getString(cursor.getColumnIndex("words"));
                int year =Integer.parseInt(cursor.getString(cursor.getColumnIndex("year")));
                int mon  =Integer.parseInt(cursor.getString(cursor.getColumnIndex("mon")));
                int day =Integer.parseInt(cursor.getString(cursor.getColumnIndex("day")));
                int hour =Integer.parseInt(cursor.getString(cursor.getColumnIndex("hour")));
                int min =Integer.parseInt(cursor.getString(cursor.getColumnIndex("min")));
                Notes note1 = new Notes(id,courseName,words,year,mon,day,hour,min);
                noteList.add(note1);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void showNormalDialog(int i){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.curriculum);
        normalDialog.setTitle("笔记操作");
        normalDialog.setMessage("你要查看还是修改这项笔记呢?");
        normalDialog.setPositiveButton("取消",
                (dialog, which) -> {
                    //...To-do
                });
        normalDialog.setNegativeButton("删除",
                (dialog, which) -> {
                    Notes notes = noteList.get(i);
                    DataBaseHelper dbHelper = new DataBaseHelper(getContext(), "course.db",null, 2);
                    SQLiteDatabase writableDatabase=dbHelper.getWritableDatabase();
                    writableDatabase.delete("Notes","id=?",new String[]{notes.id+""});
                    onResume();
                    //...To-do
                });
        normalDialog.setNeutralButton("查看",
                (dialog, which) -> {
                    //...To-do
                    Notes notes = noteList.get(i);
                    String time=""+notes.year+"年"+notes.mon+"月"+notes.day+"日"+notes.hour+":"+notes.min+":00";
                    Intent intent =new Intent(getActivity(), NoteSearch.class);
                    Bundle bundle =new Bundle();
                    bundle.putString("course",notes.courseName);
                    bundle.putString("words",notes.words);
                    bundle.putString("time",time);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
        );
        // 显示
        normalDialog.show();
    }
}
