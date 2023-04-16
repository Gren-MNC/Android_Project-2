package com.example.layoutservice.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.example.layoutservice.Adapter.albumAdapter;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.Singer;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSingerActivity extends AppCompatActivity {
    private List<MusicFiles> listSong = new ArrayList<>();
    public static final int REQUEST_CODE = 1;
    ArrayList<Singer> singers;
    private Button btnBack;

    private ArrayList<Singer> singersList;
    private SingerAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listview_singer);
        btnBack = findViewById(R.id.btn_back);

        RecyclerView view = findViewById(R.id.rcv_data);
        databaseReference = FirebaseDatabase.getInstance().getReference("Singer");
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(context));

        singersList = new ArrayList<>();
        firebaseSongAdapter = new SingerAdapter(context, singersList);
        view.setAdapter(firebaseSongAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Singer singers = dataSnapshot.getValue(Singer.class);
                    singersList.add(singers);
                }
                firebaseSongAdapter = new SingerAdapter(ListSingerActivity.this,singersList);
                view.setAdapter(firebaseSongAdapter);
                firebaseSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
