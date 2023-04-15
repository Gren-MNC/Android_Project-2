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
    public static String value = null;
    public static boolean SHOW_MINI_PLAYER = false;
    public static int STATUS_MUSIC;
    public static int actionM;
    public static String SONG_NAME = null;
    private int actionService, mCurrentPosition, durationTotal, positionSelect;
    ArrayList<SongFireBase> listSong;
    private SongFireBase mSong;
    public static boolean IS_PLAYING;
    public static boolean isIsPlaying;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle == null)
            {
                return;
            }

            value = bundle.getString("tenbaihat");
            actionM = bundle.getInt("action_music");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));
                Log.e("Test",""+STATUS_MUSIC);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(value!=null){
            SONG_NAME = value;
            SHOW_MINI_PLAYER = true;
            STATUS_MUSIC=actionM;
            Log.e("Mess",""+STATUS_MUSIC);
        }
        else {
            SHOW_MINI_PLAYER = false;
            SONG_NAME = null;
        }
    }

}