package com.example.layoutservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Activity.ViewAlbumActivity;
import com.example.layoutservice.Models.Album;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class albumAdapter extends RecyclerView.Adapter<albumAdapter.FirebaseHolder> {

    private Context context;
    private ArrayList<Album> albumArrayList;

    public albumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public FirebaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_album_items,parent,false);
        return new albumAdapter.FirebaseHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseHolder holder, int position) {
        Album album = albumArrayList.get(position);
        if(album == null){
            return ;
        }
        holder.tvAllAlbum.setText(album.getTenAlbum());
        holder.tvAllAlbumSinger.setText(album.getTenCaSiAlbum());
        Picasso.with(holder.imageViewAllAlbum.getContext()).load(album.getHinhAlbum()).into(holder.imageViewAllAlbum);
        holder.linearLayout_album_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAlbumActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objectName",album);
                bundle.putSerializable("Song_List",album.getAlbumList());
                bundle.putInt("position_key",holder.getAdapterPosition() );
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public static class FirebaseHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout_album_items;
        private ImageView imageViewAllAlbum;
        private TextView tvAllAlbum;
        private TextView tvAllAlbumSinger;
        public FirebaseHolder(@NonNull View itemView, ViewGroup viewGroup) {
            super(itemView);

            linearLayout_album_items = itemView.findViewById(R.id.linearLayout_album_items);
            imageViewAllAlbum = itemView.findViewById(R.id.imageViewAllAlbum);
            tvAllAlbum= itemView.findViewById(R.id.tvAllAlbum);
            tvAllAlbumSinger= itemView.findViewById(R.id.tvAllAlbumSinger);
        }
    }
}
