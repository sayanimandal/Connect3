package com.sayanimandal.connect3;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sayanimandal.connect3.databinding.ActivityFirstBinding;

/**
 * Created by jaisw on 9/18/2018.
 */

public class FirstActivity extends AppCompatActivity {

    private ActivityFirstBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first);
        binding.tvFirst.setText("Welcome to Connect3");
    }

    public void onClick(View view){
        Intent i = new Intent(view.getContext(), MainActivity.class);
        startActivity(i);
    }

    public void onExitGame(View view) {
        finish();
        System.exit(0);
    }
}
