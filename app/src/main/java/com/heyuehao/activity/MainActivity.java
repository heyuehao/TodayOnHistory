package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cengalabs.flatui.FlatUI;
import com.heyuehao.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化FlatUI
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.SKY);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.SKY, false));
        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, FlatUI.SKY, false));
    }
}