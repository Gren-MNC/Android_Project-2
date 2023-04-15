package com.example.layoutservice.Fragment;

import static com.example.layoutservice.Activity.MainActivity.IS_PLAYING;
import static com.example.layoutservice.Activity.MainActivity.SHOW_MINI_PLAYER;
import static com.example.layoutservice.Activity.MainActivity.SONG_NAME;
import static com.example.layoutservice.Activity.MainActivity.STATUS_MUSIC;
import static com.example.layoutservice.MyService.ACTION_NEXT;
import static com.example.layoutservice.MyService.ACTION_PAUSE;
import static com.example.layoutservice.MyService.ACTION_RESUME;
import static com.example.layoutservice.MyService.ACTION_START;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Receiver.BroadcastReceiver;

import java.util.ArrayList;

public class Fragment_Miniplayer extends Fragment {
    ImageView btn_pause, btn_cancel;
    TextView txtSong, txtSinger;
    ArrayList<SongFireBase> listSong;

    public static final int REQUEST_CODE = 1;
    private SongFireBase mSong;
    private boolean isPlaying = true;
    RelativeLayout bottom_mini;
    static MediaPlayer mediaPlayer = new MediaPlayer();
    private static int action;

    View view;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle == null)
            {
                return;
            }
            mSong = (SongFireBase) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_music");
            action = bundle.getInt("action_music");

            handleLayoutMusic(action);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miniplayer,container,false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));
        bottom_mini = view.findViewById(R.id.bottom_layout);
        txtSinger = view.findViewById(R.id.tv_single);
        txtSong = view.findViewById(R.id.tv_song);
        btn_pause = view.findViewById(R.id.img_pause_or_play);
        btn_cancel = view.findViewById(R.id.img_cancel);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SHOW_MINI_PLAYER){

            txtSong.setText(SONG_NAME);

            if(STATUS_MUSIC==1)
            {
                bottom_mini.setVisibility(View.VISIBLE);
                handleLayoutMusic(1);
                btn_pause.setImageResource(R.drawable.ic_play2);
                btn_pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        handleLayoutMusic(2);
                    }
                });
                sendActionToService(ACTION_PAUSE);

            }
            if(STATUS_MUSIC==2)
            {
                bottom_mini.setVisibility(View.VISIBLE);
                sendActionToService(ACTION_RESUME);
            }
            btn_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(isPlaying)
                  {
                      sendActionToService(ACTION_PAUSE);
                  }
                  else {
                      sendActionToService(ACTION_RESUME);
                  }
                }
            });
            if(STATUS_MUSIC==4)
            {
                handleLayoutMusic(STATUS_MUSIC);
            }
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendActionToService(MyService.ACTION_CLEAR);
                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void handleLayoutMusic(int action) {

        switch (action)
        {
            case ACTION_START:
                bottom_mini.setVisibility(View.VISIBLE);
                showInfoSong();
                break;
            case MyService.ACTION_PAUSE:
                setStatusButtonPauseOrPlay();
                break;
            case MyService.ACTION_NEXT:
                btn_pause.setImageResource(R.drawable.ic_pause);
                break;
            case MyService.ACTION_PRE:
                btn_pause.setImageResource(R.drawable.ic_pause);
                break;
            case MyService.ACTION_RESUME:
                setStatusButtonPauseOrPlay();
                break;
            case  MyService.ACTION_CLEAR:
                bottom_mini.setVisibility(View.GONE);
                break;
        }
    }
    private void setStatusButtonPauseOrPlay(){
        if(isPlaying){
            btn_pause.setImageResource(R.drawable.ic_pause);
        }
        else {
            btn_pause.setImageResource(R.drawable.ic_play2);
        }
    }


    private void showInfoSong(){
        if(mSong == null)
        {
            return;
        }

        txtSong.setText(mSong.getTitle());
        txtSinger.setText(mSong.getSinger());

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    sendActionToService(MyService.ACTION_PAUSE);
                }
                else {
                    sendActionToService(MyService.ACTION_RESUME);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(MyService.ACTION_CLEAR);
            }
        });
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(getActivity(),MyService.class);
        intent.putExtra("action_music_service",action);
        getActivity().startService(intent);
    }
}