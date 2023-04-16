package com.example.layoutservice.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Adapter.MusicFileAdapter;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.Singer;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Receiver.BroadcastReceiver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSongSingleActivity extends AppCompatActivity {
    private List<SongFireBase> listSong = new ArrayList<>();
    public static final int REQUEST_CODE = 1;

    private Button btnBack;

    private boolean isPlaying;
    private int positionSelect;
    View view;
    RecyclerView recyclerView;
    private Singer singer;
    private TextView view_name_singer;
    ArrayList<SongFireBase> songFireBaseArrayList;
    FirebaseSongAdapter firebaseSongAdapter;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    RelativeLayout background_singer;
    Context context;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single);
        recyclerView = findViewById(R.id.rcv_data);
        btnBack = findViewById(R.id.btn_back_singer);
        view_name_singer = findViewById(R.id.view_name_singer);
        background_singer = findViewById(R.id.background_single);
        positionSelect = 1;
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            positionSelect = bundle.getInt("position_key");
            singer = (Singer) bundle.getSerializable("objectName");
            listSong = (ArrayList<SongFireBase>) bundle.getSerializable("Song_List");
            view_name_singer.setText(singer.getNameSinger());

            ArrayList<SongFireBase> songs = new ArrayList<>(listSong);

            FirebaseSongAdapter firebaseSongAdapter = new FirebaseSongAdapter(this, songs);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(firebaseSongAdapter);

            Picasso.with(ListSongSingleActivity.this)
                    .load(singer.getImageSinger())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                background_singer.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            } else {
                                background_singer.setBackground(new BitmapDrawable(getResources(), bitmap));
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Toast.makeText(ListSongSingleActivity.this, "Error in img", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            // use placeholder drawable if desired
                            Toast.makeText(ListSongSingleActivity.this, "Error in img", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
//    }
}
