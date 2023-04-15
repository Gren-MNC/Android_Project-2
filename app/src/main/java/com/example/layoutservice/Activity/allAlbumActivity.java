package com.example.layoutservice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Adapter.albumAdapter;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allAlbumActivity extends AppCompatActivity {
    private ArrayList<Album> albumArrayList;
    private albumAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album);

        Button back_all_album = findViewById(R.id.btn_all_album);
        back_all_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView view = findViewById(R.id.rcv_all_data_allAlbum);
        databaseReference = FirebaseDatabase.getInstance().getReference("Album");
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(context));

        albumArrayList = new ArrayList<>();
        firebaseSongAdapter = new albumAdapter(context, albumArrayList);
        view.setAdapter(firebaseSongAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Album album = dataSnapshot.getValue(Album.class);
                    albumArrayList.add(album);
                }
                firebaseSongAdapter = new albumAdapter(allAlbumActivity.this,albumArrayList);
                view.setAdapter(firebaseSongAdapter);
                firebaseSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}