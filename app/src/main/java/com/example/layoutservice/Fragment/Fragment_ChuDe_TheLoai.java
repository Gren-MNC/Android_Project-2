package com.example.layoutservice.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.layoutservice.Activity.ListSongSingleActivity;
import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Models.Song;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.EventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ChuDe_TheLoai extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<SongFireBase> songFireBaseArrayList;
    FirebaseSongAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chu_de_the_loai, container, false);

        recyclerView = view.findViewById(R.id.firebase_song_recyclerview);
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
                firebaseSongAdapter = new FirebaseSongAdapter(getActivity(),songFireBaseArrayList);
                recyclerView.setAdapter(firebaseSongAdapter);
                firebaseSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}

