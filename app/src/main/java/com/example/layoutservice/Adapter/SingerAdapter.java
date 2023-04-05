package com.example.layoutservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Activity.ListSingerActivity;
import com.example.layoutservice.Activity.ListSongSingleActivity;
import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.Singer;
import com.example.layoutservice.R;

import java.io.IOException;
import java.util.ArrayList;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> {
    private ArrayList<Singer> listSinger;
    private Context context;

    private int idLayout;
    private int positionSelect = -1;


    @NonNull
    @Override
    public SingerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_singer, parent,false);
        return new SingerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerHolder holder, int position) {
        Singer mSinger = listSinger.get(position);
        holder.txtArtist.setText(mSinger.getName());
        byte[] image = getAlbumArt(mSinger.getPath());
        if(image != null){
            Glide.with(context).asBitmap()
                    .load(image)
                    .into(holder.imgAva);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.ic_music)
                    .into(holder.imgAva);
        }

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListSongSingleActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public SingerAdapter(Context context,ArrayList<Singer> listSinger){
        this.listSinger = listSinger;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        if(listSinger != null)
        {
            return listSinger.size();
        }
        return 0;
    }

    public class SingerHolder extends RecyclerView.ViewHolder{


        private ImageView imgAva;
        private TextView txtArtist;
        private TextView txtSingle;
        private RelativeLayout layout_item;
        public SingerHolder(@NonNull View v){
            super(v);

            layout_item = v.findViewById(R.id.layout_item_song_single);
            txtArtist = v.findViewById(R.id.tv_artist);
            imgAva = v.findViewById(R.id.img_song);

        }
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return art;
    }


}