package com.example.layoutservice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.example.layoutservice.Receiver.BroadcastReceiver;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

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