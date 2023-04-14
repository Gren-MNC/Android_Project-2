package com.example.layoutservice.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.layoutservice.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnStop;
    private TabLayout mtabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
    }
}