package com.example.layoutservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class SongPlayingAdapter extends RecyclerView.Adapter<SongPlayingAdapter.SongPlayingHolder> {

    private ArrayList<SongFireBase> musicFilesArrayList;
    private Context context;
    private int positionSelect = -1;

    @NonNull
    @Override
    public SongPlayingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listview_playing, parent,false);
        return new SongPlayingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongPlayingHolder holder, int position) {
        SongFireBase mFiles = musicFilesArrayList.get(position);
        if(mFiles == null){
            return;
        }
        holder.txtSong.setText(mFiles.getTitle());
        holder.txtSingle.setText(mFiles.getSinger());
        Picasso.with(holder.imgAva.getContext()).load(mFiles.getImage()).into(holder.imgAva);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionSelect = holder.getAdapterPosition();

                Intent intent = new Intent(context, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song",mFiles);
                bundle.putSerializable("listSong_key", musicFilesArrayList);
                bundle.putInt("position_key", positionSelect);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
    public SongPlayingAdapter(Context context, ArrayList<SongFireBase> musicFiles){
        this.context = context;
        this.musicFilesArrayList = musicFiles;
    }
    @Override
    public int getItemCount() {
        if(musicFilesArrayList != null)
        {
            return musicFilesArrayList.size();
        }
        return 0;
    }
    public static class SongPlayingHolder extends RecyclerView.ViewHolder{
        private ImageView imgAva;
        private TextView txtSong;
        private TextView txtSingle;
        private LinearLayout layout_item;
        public SongPlayingHolder(@NonNull View v){
            super(v);

            layout_item = v.findViewById(R.id.idListViewPlayingLayout);
            txtSingle = v.findViewById(R.id.tvSingle);
            txtSong = v.findViewById(R.id.tvName);
            imgAva = v.findViewById(R.id.imv_music);

        }
    }



}
