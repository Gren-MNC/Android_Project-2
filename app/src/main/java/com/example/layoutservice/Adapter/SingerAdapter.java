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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> {
    private ArrayList<Singer> listSinger;
    private Context context;

    private int idLayout;
    private int positionSelect = -1;


    public SingerAdapter(Context context,ArrayList<Singer> listSinger){
        this.listSinger = listSinger;
        this.context = context;
    }


    @NonNull
    @Override
    public SingerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_singer, parent,false);
        return new SingerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerHolder holder, int position) {
        Singer mSinger = listSinger.get(position);
        if(mSinger == null){
            return ;
        }

        holder.txtArtist.setText(mSinger.getNameSinger());
        Picasso.with(holder.imgAva.getContext()).load(mSinger.getImageSinger()).into(holder.imgAva);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListSongSingleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objectName",mSinger);
                bundle.putSerializable("Song_List",mSinger.getListSong());
                bundle.putInt("position_key",holder.getAdapterPosition() );
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
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



}