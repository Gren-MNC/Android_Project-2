package com.example.layoutservice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layoutservice.Adapter.FirebaseSongAdapter;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class ViewAlbumActivity extends AppCompatActivity {

    private int positionSelect;
    private ArrayList<Album> listAlbum;
    private Album album;
    private RelativeLayout imgAlbum;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        positionSelect = 1;
        TextView albumName = findViewById(R.id.Name_album_tv);
        TextView albumSinger = findViewById(R.id.Name_singer_tv);
        Button btn_back = findViewById(R.id.btn_items_back_album);
        RelativeLayout imgAlbum = findViewById(R.id.background_album);
        RecyclerView view = findViewById(R.id.view_items_album);
        ArrayList<SongFireBase> songList;
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (bundle != null) {
            positionSelect = bundle.getInt("position_key");
            album = (Album) bundle.getSerializable("objectName");
            songList = (ArrayList<SongFireBase>) bundle.getSerializable("Song_List");
            albumName.setText(album.getTenAlbum());
            albumSinger.setText(album.getTenCaSiAlbum());

            ArrayList<SongFireBase> songs = new ArrayList<>(songList);

            FirebaseSongAdapter firebaseSongAdapter = new FirebaseSongAdapter(this, songs);
            view.setHasFixedSize(true);
            view.setLayoutManager(new LinearLayoutManager(context));
            view.setAdapter(firebaseSongAdapter);

            Picasso.with(ViewAlbumActivity.this)
                    .load(album.getHinhAlbum())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                imgAlbum.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            } else {
                                imgAlbum.setBackground(new BitmapDrawable(getResources(), bitmap));
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Toast.makeText(ViewAlbumActivity.this, "Error in img", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            // use placeholder drawable if desired
                            Toast.makeText(ViewAlbumActivity.this, "Error in img", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}