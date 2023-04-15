package com.example.layoutservice.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

import com.example.layoutservice.Activity.allAlbumActivity;
import com.example.layoutservice.Adapter.albumAdapter;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_Album_Hot extends Fragment {
    View view;
    RecyclerView recyclerViewAlbum;
    albumAdapter Adapter;
    ArrayList<Album> albumArrayList;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    TextView textView;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_hot, container, false);

        textView = view.findViewById(R.id.tvXemThemAlbum);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), allAlbumActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewAlbum= view.findViewById(R.id.recylerViewAlbumHot);
        databaseReference = FirebaseDatabase.getInstance().getReference("Album");
        recyclerViewAlbum.setHasFixedSize(true);
        recyclerViewAlbum.setLayoutManager(new LinearLayoutManager(context));

        albumArrayList = new ArrayList<>();
        Adapter = new albumAdapter(context, albumArrayList);

        recyclerViewAlbum.setAdapter(Adapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Album album = dataSnapshot.getValue(Album.class);
                    albumArrayList.add(album);
                }
                Adapter = new albumAdapter(getActivity(),albumArrayList);
                recyclerViewAlbum.setAdapter(Adapter);
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

