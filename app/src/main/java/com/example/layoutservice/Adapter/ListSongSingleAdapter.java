package com.example.layoutservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;

public class ListSongSingleAdapter extends RecyclerView.Adapter<ListSongSingleAdapter.ListSongSingleHolder> {
    private ArrayList<Song> songList;
    private Context context;

    private int idLayout;
    private ArrayList<Song> listSong;
    private int positionSelect = -1;


    @NonNull
    @Override
    public ListSongSingleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_single, parent,false);
        return new ListSongSingleHolder(view);
    }
    public ListSongSingleAdapter(Context context,ArrayList<Song> songList){
        this.songList = songList;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ListSongSingleHolder holder, int position) {

        Song song = songList.get(position);
        if(song == null){
            return;
        }
        holder.txtSong.setText(song.getTitle());
        holder.txtSingle.setText(song.getSinger());
        holder.imgAva.setImageResource(song.getImage());
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positionSelect = holder.getAdapterPosition();
                Intent intent = new Intent(context, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song",song);
                bundle.putSerializable("listSong_key", songList);
                bundle.putInt("position_key", holder.getAdapterPosition());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    private void clickStarService() {
        Song song = new Song("Eye nose lips","Teayang",R.drawable.eyenoselips, R.raw.lethergo);
        Intent intent = new Intent(context, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song",song);
        intent.putExtras(bundle);
        context.startService(intent);
    }
    @Override
    public int getItemCount() {
        if(songList != null)
        {
            return songList.size();
        }
        return 0;
    }

    public class ListSongSingleHolder extends RecyclerView.ViewHolder{


        private ImageView imgAva;
        private TextView txtSong;
        private TextView txtSingle;
        private RelativeLayout layout_item;
        public ListSongSingleHolder(@NonNull View v){
            super(v);

            layout_item = v.findViewById(R.id.layout_item_song_single);
            txtSingle = v.findViewById(R.id.tv_single);
            txtSong = v.findViewById(R.id.tv_song);
            imgAva = v.findViewById(R.id.img_song);

        }
    }


}
