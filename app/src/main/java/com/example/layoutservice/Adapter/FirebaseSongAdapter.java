package com.example.layoutservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSongAdapter extends RecyclerView.Adapter<FirebaseSongAdapter.MyViewHolder> {

    Context context;
    ArrayList<SongFireBase> songFireBasesList;

    public FirebaseSongAdapter(Context context, ArrayList<SongFireBase> songFireBasesList) {
        this.context = context;
        this.songFireBasesList = songFireBasesList;
    }

    @NonNull
    @Override
    public FirebaseSongAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_music_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseSongAdapter.MyViewHolder holder, int position) {

        SongFireBase songFireBase = songFireBasesList.get(position);

        holder.songName.setText(songFireBase.getTitle());
        holder.singerName.setText(songFireBase.getSinger());

    }

    @Override
    public int getItemCount() {
        return songFireBasesList.size();
    }
    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView songName, singerName, imgUri, songUri;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.tvSearchMusic);
            singerName = itemView.findViewById(R.id.tvSearchMusicSinger);

        }
    }
}
