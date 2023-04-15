package com.example.layoutservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Adapter.SongPlayingAdapter;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;

import java.util.ArrayList;

public class ListSongPlayingActivity extends AppCompatActivity {
    private ArrayList<SongFireBase> listSong;
    private Button btnBack;
    private RecyclerView recyclerView;
    private SongPlayingAdapter songPlayingAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_music_playing);

        recyclerView = findViewById(R.id.rcv_data_playing);
        btnBack = findViewById(R.id.btn_back);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            listSong = (ArrayList<SongFireBase>) bundle.getSerializable("listSong_key");
        }
        songPlayingAdapter = new SongPlayingAdapter(this,  listSong);
        recyclerView.setAdapter(songPlayingAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
