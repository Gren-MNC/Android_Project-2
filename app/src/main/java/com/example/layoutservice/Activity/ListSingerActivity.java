package com.example.layoutservice.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Adapter.SingerAdapter;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.Singer;
import com.example.layoutservice.R;

import java.util.ArrayList;
import java.util.List;

public class ListSingerActivity extends AppCompatActivity {
    private List<MusicFiles> listSong = new ArrayList<>();
    public static final int REQUEST_CODE = 1;
    ArrayList<Singer> singers;
    ArrayAdapter<String> adapter;
    private static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    private ListView listView;
    private Button btnStart,btnBack;
    private Button btnStop;
    private MusicFiles mSong;
    private ImageView imgPauseOrPlay;
    private ImageView imgCancel, imgSong;
    private RelativeLayout relativeLayout;
    private TextView tvSong, tvSingle;
    private boolean isPlaying;
    private RecyclerView recyclerView;
    private SingerAdapter singerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview_singer);
        permission();

        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        singerAdapter = new SingerAdapter(this,singers);
        recyclerView.setAdapter(singerAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void permission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ListSingerActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        else {
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            singers = getAllAudio(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                singers = getAllAudio(this);
            }
            else {
                ActivityCompat.requestPermissions(ListSingerActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }
    }
    public static ArrayList<Singer> getAllAudio(Context context){
        ArrayList<Singer> tempSingerList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                int id_artist = cursor.getInt(0);
                String artist = cursor.getString(1);
                String path = cursor.getString(2);

                Singer singer = new Singer(id_artist,artist, path);
                tempSingerList.add(singer);
            }
            cursor.close();
        }
        return tempSingerList;
    }

}
