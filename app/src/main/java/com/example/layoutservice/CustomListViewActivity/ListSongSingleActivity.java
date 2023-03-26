package com.example.layoutservice.CustomListViewActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.layoutservice.Adapter.SongFavoriteAdapter;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSongSingleActivity extends AppCompatActivity {
    private List<Song> listSong;
    private ListView listView;
    private Button btnStart;
    private Button btnStop;
    private Song mSong;
    private ImageView imgPauseOrPlay;
    private ImageView imgCancel, imgSong;
    private RelativeLayout relativeLayout;
    private TextView tvSong, tvSingle;
    private boolean isPlaying;
    private RecyclerView recyclerView;
    private ListSongSingleAdapter listSongSingleAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single);





        recyclerView = findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        listSongSingleAdapter = new ListSongSingleAdapter(this,getListUser());
        recyclerView.setAdapter(listSongSingleAdapter);
    }

    private List<Song> getListUser() {
        List<Song> list = new ArrayList<>();
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.eyenoselips,12));
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        list.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        return list;
    }


    private void clickStopService() {
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }

}
