package com.example.layoutservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutservice.Fragment.Fragment_ChuDe_TheLoai;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
        setImage(holder, songFireBase.getTitle());
    }

    private void setImage(FirebaseSongAdapter.MyViewHolder holder, String ImgName){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getImage = databaseReference.child("/SongFireBase/"+ImgName+"/image");
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(
                        String.class);
                Picasso.with(holder.imgView.getContext()).load(link).into(holder.imgView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error when load song Image ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songFireBasesList.size();
    }
    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView songName, singerName;
        ImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.tvSearchMusic);
            singerName = itemView.findViewById(R.id.tvSearchMusicSinger);
            imgView = itemView.findViewById(R.id.imageViewSearchMusic);
        }
    }
}
