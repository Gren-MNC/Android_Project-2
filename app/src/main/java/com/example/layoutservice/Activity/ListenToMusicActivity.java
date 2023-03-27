package com.example.layoutservice.Activity;

import static com.example.layoutservice.MyService.isPlaying;
import static com.example.layoutservice.MyService.mediaPlayer;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

public class ListenToMusicActivity extends AppCompatActivity {

    Button btnPlay;
    TextView tvName, tvSinger, tvStart, tvStop;
    SeekBar seekBarMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_play_music);

        btnPlay = findViewById(R.id.btn_play_main);
        tvName = findViewById(R.id.title_song);
        tvSinger = findViewById(R.id.single);
        tvStart = findViewById(R.id.tv_time);
        tvStop = findViewById(R.id.tv_time1);
        seekBarMusic = findViewById(R.id.seekbar);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start
                if (mediaPlayer == null && !isPlaying ){
                    clickStartService();
                    btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
                }
                // resume
                if(mediaPlayer != null && !isPlaying){
                    mediaPlayer.start();
                    isPlaying = true;
                    btnPlay.setBackgroundResource(R.drawable.ic_pause_main);
                }
                // pause
                else if(mediaPlayer != null && isPlaying){
                    mediaPlayer.pause();
                    isPlaying = false;
                    btnPlay.setBackgroundResource(R.drawable.ic_play_main);
                }
            }
        });

    }
    private void clickStartService() {
        Song song = new Song("Show Me Love", "MCK", R.drawable.img_music, R.raw.lethergo);
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", song);
        intent.putExtras(bundle);
        startService(intent);

        tvName.setText(song.getTitle());
        tvSinger.setText(song.getSinger());

    }
    /*public void implementSeekbar(){
        Thread updateSeekBar = new Thread() {
            @Override
            public void run() {
                super.run();

                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBarMusic.setProgress(currentPosition);
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        seekBarMusic.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekBarMusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black),
                PorterDuff.Mode.MULTIPLY);
        seekBarMusic.getThumb().setColorFilter(getResources().getColor(R.color.teal_200),
                PorterDuff.Mode.SRC_IN);
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        String endTime = createTime(mediaPlayer.getDuration());
        tvStop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                tvStart.setText(currentTime);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
    public String createTime(int duration){
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;

        time += min + ":";
        if (sec<10){
            time += "0";
        }
        time += sec;
        return time;
    }*/
}


