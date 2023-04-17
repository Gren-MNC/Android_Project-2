package com.example.layoutservice.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.layoutservice.Adapter.FavoriteSongAdapter;
import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Adapter.SongFavoriteAdapter;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.Song;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFavoriteActivity extends AppCompatActivity {
    private List<MusicFiles> listSong = new ArrayList<>();
    public static final int REQUEST_CODE = 1;
    ArrayList<MusicFiles> musicFiles;
    private Button btnBack;
    private RecyclerView recyclerView;
    private SongFavoriteAdapter songFavoriteAdapter;
    ArrayList<SongFireBase> songFireBaseArrayList;
    FavoriteSongAdapter Adapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_favorite);

        btnBack = findViewById(R.id.btn_back_favorite);
        recyclerView = findViewById(R.id.rcv_data_favorite);

        databaseReference = FirebaseDatabase.getInstance().getReference("FavoriteMusic");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        songFireBaseArrayList = new ArrayList<>();
        Adapter = new FavoriteSongAdapter(context, songFireBaseArrayList);

        recyclerView.setAdapter(Adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SongFireBase songFireBase = dataSnapshot.getValue(SongFireBase.class);

                    songFireBaseArrayList.add(songFireBase);
                }
//                Toast.makeText(ListFavoriteActivity.this, String.valueOf(songList.size()), Toast.LENGTH_SHORT).show();

                Adapter = new FavoriteSongAdapter(ListFavoriteActivity.this, songFireBaseArrayList);
                recyclerView.setAdapter(Adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ListFavoriteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            // Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            musicFiles = getAllAudio(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                musicFiles = getAllAudio(this);
            } else {
                ActivityCompat.requestPermissions(ListFavoriteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    public static ArrayList<MusicFiles> getAllAudio(Context context) {
        ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                MusicFiles musicFiles = new MusicFiles(path, title, artist, album, duration);
                Log.e("Path: " + path, "Album: " + album);
                tempAudioList.add(musicFiles);
            }
            cursor.close();
        }
        return tempAudioList;
    }


}
