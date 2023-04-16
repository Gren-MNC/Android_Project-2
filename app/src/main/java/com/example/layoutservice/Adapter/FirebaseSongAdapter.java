package com.example.layoutservice.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Activity.ListSongSingleActivity;
import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Activity.MainActivity;
import com.example.layoutservice.Fragment.Fragment_ChuDe_TheLoai;
import com.example.layoutservice.Models.Song;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirebaseSongAdapter extends RecyclerView.Adapter<FirebaseSongAdapter.FirebaseHolder> {

    private Context context;
    private ArrayList<SongFireBase> songFireBasesList;
    private int positionSelect = -1;


    @NonNull
    @Override
    public FirebaseSongAdapter.FirebaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_single, parent,false);
        return new FirebaseSongAdapter.FirebaseHolder(view, parent);
    }
    public FirebaseSongAdapter(Context context,ArrayList<SongFireBase> songFireBasesList){
        this.songFireBasesList = songFireBasesList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseSongAdapter.FirebaseHolder holder, int position) {

        SongFireBase songFireBase = songFireBasesList.get(position);
        if(songFireBase == null){
            return;
        }

        holder.txtSong.setText(songFireBase.getTitle());
        holder.txtSingle.setText(songFireBase.getSinger());
        Picasso.with(holder.imgAva.getContext()).load(songFireBase.getImage()).into(holder.imgAva);


        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                positionSelect = holder.getAdapterPosition();
                Intent intent = new Intent(context, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song",songFireBase);
                bundle.putSerializable("listSong_key", songFireBasesList);
                bundle.putInt("position_key", holder.getAdapterPosition());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));

                downloadSong(context, songFireBase.getTitle(),".mp3",
                        path, songFireBase.getSongUri());

            }
        });
    }
    public void downloadSong(Context context1, String filename, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context1.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(destinationDirectory, filename + fileExtension);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return songFireBasesList.size();
    }
    public static class FirebaseHolder extends  RecyclerView.ViewHolder{

        private ImageView imgAva;
        private TextView txtSong;
        private TextView txtSingle;
        private LinearLayout layout_item;
        private Button btnDownload;

        public FirebaseHolder(@NonNull View v, ViewGroup viewGroup) {
            super(v);

            layout_item = v.findViewById(R.id.layout_item_song_single);
            txtSingle = v.findViewById(R.id.tv_single);
            txtSong = v.findViewById(R.id.tv_song);
            imgAva = v.findViewById(R.id.img_song);
            btnDownload = v.findViewById(R.id.btn_download);
        }
    }
}
