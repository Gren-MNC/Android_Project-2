package com.example.layoutservice.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Adapter.ListSongSingleAdapter;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Models.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSongSingleActivity extends AppCompatActivity {
    private List<Song> listSong = new ArrayList<>() ;
    ArrayAdapter<String> adapter;
    private static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    private ListView listView;
    private Button btnStart,btnStop, btnBack;
    private Song mSong;
    private ImageView imgPauseOrPlay;
    private ImageView imgCancel, imgSong;
    private RelativeLayout relativeLayout;
    private TextView tvSong, tvSingle;
    private boolean isPlaying;
    private RecyclerView recyclerView;
    private ListSongSingleAdapter listSongSingleAdapter;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle == null)
            {
                return;
            }
            mSong = (Song) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_music");
            int action = bundle.getInt("action_music");

            handleLayoutMusic(action);
        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));

        relativeLayout = findViewById(R.id.bottom_layout);
        imgPauseOrPlay = findViewById(R.id.img_pause_or_play);
        imgCancel = findViewById(R.id.img_cancel);
        imgSong = findViewById(R.id.img_song);
        recyclerView = findViewById(R.id.rcv_data);
        tvSong = findViewById(R.id.tv_song);
        tvSingle = findViewById(R.id.tv_single);
        btnBack = findViewById(R.id.btn_back_singer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        listSongSingleAdapter = new ListSongSingleAdapter(this,getListUser());
        recyclerView.setAdapter(listSongSingleAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListSongSingleActivity.this, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song",mSong);
                sendActionToService(MyService.ACTION_RESUME);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }




    private ArrayList<Song> getListUser() {
        ArrayList<Song> list = new ArrayList<>();
        list.add(new Song("Let her go","Single", R.drawable.eyenoselips, R.raw.lethergo));
        list.add(new Song("Muon roi ma sao con","Single", R.drawable.ic_music, R.raw.muonroimasaocon_sontungmtp));
        list.add(new Song("Neu luc do","Single", R.drawable.ic_music, R.raw.neulucdo_tlinh));
        list.add(new Song("Ngu mot minh","Single", R.drawable.ic_music, R.raw.ngumotminh_hieuthuhai));
        return list;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
    private void handleLayoutMusic(int action) {

        switch (action)
        {
            case MyService.ACTION_START:
                relativeLayout.setVisibility(View.VISIBLE);
                showInfoSong();
                break;
            case MyService.ACTION_PAUSE:
                setStatusButtonPauseOrPlay();
                break;
            case MyService.ACTION_RESUME:
                setStatusButtonPauseOrPlay();
                break;
            case  MyService.ACTION_CLEAR:
                relativeLayout.setVisibility(View.GONE);
                break;
        }
    }
    private void setStatusButtonPauseOrPlay(){
        if(isPlaying){
            imgPauseOrPlay.setImageResource(R.drawable.ic_pause);
        }
        else {
            imgPauseOrPlay.setImageResource(R.drawable.ic_play2);
        }
    }
    private void showInfoSong(){
        if(mSong == null)
        {
            return;
        }
        imgSong.setImageResource(mSong.getImage());
        tvSong.setText(mSong.getTitle());
        tvSingle.setText(mSong.getSinger());
        imgPauseOrPlay.setImageResource(R.drawable.ic_pause);

        imgPauseOrPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    sendActionToService(MyService.ACTION_PAUSE);
                }
                else {
                    sendActionToService(MyService.ACTION_RESUME);
                }
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(MyService.ACTION_CLEAR);
            }
        });
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("action_music_service",action);
        startService(intent);
    }



}
