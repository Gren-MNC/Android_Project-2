package com.example.layoutservice.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Adapter.SongDownAdapter;

import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;

import java.util.ArrayList;
import java.util.List;

public class ListDownActivity extends AppCompatActivity {


    private List<SongFireBase> listSong = new ArrayList<>();
    public static final int REQUEST_CODE = 1;
    ArrayList<SongFireBase> musicFiles;
    private Button btnBack;
    private RecyclerView recyclerView;
    private SongDownAdapter songDownAdapter;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_download);
        permission();

        btnBack = findViewById(R.id.btn_back_down);
        recyclerView = findViewById(R.id.rcv_data_down);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        songDownAdapter = new SongDownAdapter(context, musicFiles);

        recyclerView.setAdapter(songDownAdapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ListDownActivity.this,new String[]{READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        else {
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            musicFiles = getAllAudio(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                musicFiles = getAllAudio(this);
            }
            else {
                ActivityCompat.requestPermissions(ListDownActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }
    }
    public static ArrayList<SongFireBase> getAllAudio(Context context){
        ArrayList<SongFireBase> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,
                null,null,null);
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                String title = cursor.getString(0);
                String artist = cursor.getString(1);
                String album = cursor.getString(2);
                String path = cursor.getString(3);
                SongFireBase musicFiles = new SongFireBase(title,artist,album,path);
                Log.e("Path: "+path,"Album: "+album);
                tempAudioList.add(musicFiles);
            }
            cursor.close();
        }
        return tempAudioList;
    }


}
