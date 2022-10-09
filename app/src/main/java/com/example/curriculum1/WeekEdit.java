package com.example.curriculum1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.curriculum1.databinding.ActivityWeekEditBinding;

public class WeekEdit extends AppCompatActivity {

    private ActivityWeekEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeekEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.nowWeek.setText("当前周数为"+MainActivity.numberWeek+"周");
        View.OnClickListener onClickListener = null;
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int week = Integer.parseInt(binding.xiugai.getText().toString());
                MainActivity.numberWeek=week;
                finish();
            }
        };
        binding.button.setOnClickListener(onClickListener);

    }
}