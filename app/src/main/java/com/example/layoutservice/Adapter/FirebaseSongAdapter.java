package com.example.layoutservice.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseSongAdapter extends RecyclerView.Adapter<FirebaseSongAdapter.FirebaseHolder> implements Filterable {

    private Context context;
    private ArrayList<SongFireBase> songFireBasesList;
    private ArrayList<SongFireBase> filterList;
    private int positionSelect = -1;
    DatabaseReference databaseReference;


    @NonNull
    @Override
    public FirebaseSongAdapter.FirebaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sub_single, parent, false);
        return new FirebaseSongAdapter.FirebaseHolder(view, parent);
    }

    public FirebaseSongAdapter(Context context, ArrayList<SongFireBase> songFireBasesList) {
        this.songFireBasesList = songFireBasesList;
        this.filterList = songFireBasesList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseSongAdapter.FirebaseHolder holder, int position) {

        SongFireBase songFireBase = songFireBasesList.get(position);
        if (songFireBase == null) {
            return;
        }

        holder.txtSong.setText(songFireBase.getTitle());
        holder.txtSingle.setText(songFireBase.getSinger());
        Picasso.with(holder.imgAva.getContext()).load(songFireBase.getImage()).into(holder.imgAva);

        if(songFireBase.isFavorite()){
            holder.btnFavorite.setBackgroundResource(R.drawable.ic_like2);
        }
        else {
            holder.btnFavorite.setBackgroundResource(R.drawable.ic_like1);
        }

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songFireBase.isFavorite()){
                    holder.btnFavorite.setBackgroundResource(R.drawable.ic_like1);
                    songFireBase.setFavorite(false);
                }
                else{
                    holder.btnFavorite.setBackgroundResource(R.drawable.ic_like2);
                    songFireBase.setFavorite(true);
                }
                databaseReference = FirebaseDatabase.getInstance().getReference("SongFireBase");
                HashMap song = new HashMap<>();
                song.put("title", songFireBase.getTitle());
                song.put("singer", songFireBase.getSinger());
                song.put("image", songFireBase.getImage());
                song.put("songUri", songFireBase.getSongUri());
                song.put("isFavorite", songFireBase.isFavorite());
                databaseReference.child(songFireBase.getTitle()).updateChildren(song).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Updated!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        if (songFireBase.isFavorite()) {
            holder.btnFavorite.setBackgroundResource(R.drawable.ic_like2);
        }

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                positionSelect = holder.getAdapterPosition();
                Intent intent = new Intent(context, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song", songFireBase);
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

                downloadSong(context, songFireBase.getTitle(), ".mp3",
                        path, songFireBase.getSongUri());

            }
        });
    }

    public void downloadSong(Context context1, String filename, String fileExtension, String destinationDirectory, String url) {
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



    public static class FirebaseHolder extends RecyclerView.ViewHolder {

        private ImageView imgAva;
        private TextView txtSong;
        private TextView txtSingle;
        private LinearLayout layout_item;
        private Button btnDownload;
        private Button btnFavorite;

        public FirebaseHolder(@NonNull View v, ViewGroup viewGroup) {
            super(v);

            layout_item = v.findViewById(R.id.layout_item_song_single);
            txtSingle = v.findViewById(R.id.tv_single);
            txtSong = v.findViewById(R.id.tv_song);
            imgAva = v.findViewById(R.id.img_song);
            btnDownload = v.findViewById(R.id.btn_download);
            btnFavorite = v.findViewById(R.id.btn_favorite);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    songFireBasesList = filterList;
                }
                else {
                    ArrayList<SongFireBase> list = new ArrayList<>();
                    for(SongFireBase song : filterList){
                        if(song.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(song);
                        }
                    }

                    songFireBasesList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = songFireBasesList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                songFireBasesList = (ArrayList<SongFireBase>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
