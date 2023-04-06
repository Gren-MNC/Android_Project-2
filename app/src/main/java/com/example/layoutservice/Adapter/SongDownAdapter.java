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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.R;

import java.io.IOException;
import java.util.ArrayList;

public class SongDownAdapter extends RecyclerView.Adapter<SongDownAdapter.SongDownHolder> {
    private ArrayList<MusicFiles> musicFilesArrayList;
    private Context context;
    private int positionSelect = -1;
    @NonNull
    @Override
    public SongDownHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listview_down, parent,false);
        return new SongDownHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SongDownHolder holder, int position) {
        MusicFiles mFiles = musicFilesArrayList.get(position);
        if(mFiles == null){
            return;
        }
        holder.txtSong.setText(mFiles.getTitle());
        holder.txtSingle.setText(mFiles.getArtist());
        byte[] image = getAlbumArt(mFiles.getPath());
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

    public SongDownAdapter(Context context,ArrayList<MusicFiles> musicFilesArrayList){
        this.musicFilesArrayList = musicFilesArrayList;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        if(musicFilesArrayList != null)
        {
            return musicFilesArrayList.size();
        }
        return 0;
    }
    public class SongDownHolder extends RecyclerView.ViewHolder{
        private ImageView imgAva;
        private TextView txtSong;
        private TextView txtSingle;
        private LinearLayout layout_item;
        public SongDownHolder(@NonNull View v){
            super(v);

            layout_item = v.findViewById(R.id.idListViewDownLayout);
            txtSingle = v.findViewById(R.id.tvSingle);
            txtSong = v.findViewById(R.id.tvName);
            imgAva = v.findViewById(R.id.imv_music);

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