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
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAllSongActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SongFireBase> songFireBaseArrayList;
    FirebaseSongAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Button btnback;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_song);

        recyclerView = findViewById(R.id.rcv_all_data);
        databaseReference = FirebaseDatabase.getInstance().getReference("SongFireBase");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        songFireBaseArrayList = new ArrayList<>();
        firebaseSongAdapter = new FirebaseSongAdapter(context, songFireBaseArrayList);

        recyclerView.setAdapter(firebaseSongAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SongFireBase songFireBase = dataSnapshot.getValue(SongFireBase.class);
                    songFireBaseArrayList.add(songFireBase);
                }
                firebaseSongAdapter = new FirebaseSongAdapter(ListAllSongActivity.this,songFireBaseArrayList);
                recyclerView.setAdapter(firebaseSongAdapter);
                firebaseSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}