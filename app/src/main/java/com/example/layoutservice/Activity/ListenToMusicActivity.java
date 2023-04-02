package com.example.layoutservice.Activity;

import static com.example.layoutservice.MyService.isPlaying;
import static com.example.layoutservice.MyService.mediaPlayer;

import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Receiver.BroadcastReceiver;
import com.example.layoutservice.Song;

import java.util.ArrayList;

public class ListenToMusicActivity extends AppCompatActivity {

    Button btnPlay, btnNext, btnPre, btnList, btnBack;
    TextView tvName, tvSinger, tvStart, tvStop;
    ArrayList<Song> listSong;
    private Song mSong;
    int positionSelect;
    SeekBar seekMusic;
    private int actionMusic;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null){
                return;
            }
            mSong = (Song) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_music");
            actionMusic = bundle.getInt("action_music");
            if (actionMusic == MyService.ACTION_NEXT){
                btnNext.performClick();
            }
            handleLayoutMusic(actionMusic);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_play_music);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));

        btnPlay = findViewById(R.id.btn_play_main);
        btnNext = findViewById(R.id.btn_next_main);
        btnPre = findViewById(R.id.btn_previous_main);
        btnList = findViewById(R.id.btn_list);
        btnBack = findViewById(R.id.btn_back_playing);
        tvName = findViewById(R.id.title_song);
        tvSinger = findViewById(R.id.single);
        tvStart = findViewById(R.id.tv_time);
        tvStop = findViewById(R.id.tv_time1);
        seekMusic = findViewById(R.id.seekbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            if ((mediaPlayer != null && isPlaying) || (mediaPlayer != null && !isPlaying)){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            positionSelect = bundle.getInt("position_key");
            listSong = (ArrayList<Song>) bundle.getSerializable("listSong_key");

            mSong = (Song) bundle.get("object_song");

            tvName.setText(mSong.getTitle());
            tvSinger.setText(mSong.getSinger());

            clickStartService();
            btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // resume
                if(mediaPlayer != null && !isPlaying){

                    btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
                    sendActionToService(MyService.ACTION_RESUME);
                }
                // pause
                else if(mediaPlayer != null && isPlaying){

                    btnPlay.setBackgroundResource(R.drawable.ic_play_main);
                    sendActionToService(MyService.ACTION_PAUSE);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                positionSelect = (positionSelect+1) % listSong.size();
                mSong = listSong.get(positionSelect);
                clickStartService();
                tvName.setText(mSong.getTitle());
                tvSinger.setText(mSong.getSinger());
                btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
            }
        });
        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                positionSelect = ((positionSelect-1)<0) ? (listSong.size()-1):(positionSelect-1);
                mSong = listSong.get(positionSelect);
                clickStartService();
                tvName.setText(mSong.getTitle());
                tvSinger.setText(mSong.getSinger());
                btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListenToMusicActivity.this, ListSongPlayingActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("listSong_key", listSong);
                i.putExtras(b);
                startActivity(i);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void clickStartService() {

        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", mSong);
        intent.putExtras(bundle);
        startService(intent);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
    private void handleLayoutMusic(int action){
        switch (action){
            case MyService.ACTION_START:
                btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
                break;
            case MyService.ACTION_PAUSE:
                btnPlay.setBackgroundResource(R.drawable.ic_play_main);
                break;
            case MyService.ACTION_RESUME:
                btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
                break;
        }
    }
    private void sendActionToService(int action){
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action_music_service", action);
        startService(intent);
    }
}


