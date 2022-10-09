package com.example.curriculum1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.curriculum1.databinding.ActivityAddCourseBinding;
import com.example.curriculum1.databinding.ActivityNoteSearchBinding;

public class NoteSearch extends AppCompatActivity {

    private ActivityNoteSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteSearchBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(binding.getRoot());
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        String course=bundle.getString("course");
        String words=bundle.getString("words");
        String time=bundle.getString("time");
        binding.title.setText(course);
        binding.words.setText(words);
        binding.time.setText(time);
        binding.rt.setOnClickListener(view -> {
            finish();
        });

    }
}