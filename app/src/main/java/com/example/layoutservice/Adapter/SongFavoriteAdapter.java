package com.example.layoutservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;

public class SongFavoriteAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private ArrayList<Song> listSong;
    private int positionSelect = -1;

    public SongFavoriteAdapter(Context context, int idLayout, ArrayList<Song> listSong){
        this.context = context;
        this.idLayout = idLayout;
        this.listSong = listSong;
    }

    @Override
    public int getCount() {
        if (listSong.size() != 0 && !listSong.isEmpty()){
            return listSong.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }
        TextView textViewName = (TextView) convertView.findViewById(R.id.tvName);
        TextView textViewSingle = (TextView) convertView.findViewById(R.id.tvSingle);
        final RelativeLayout layoutListView = (RelativeLayout) convertView.findViewById(R.id.idListViewLayout);
        final Song song = listSong.get(position);

        if(listSong != null && !listSong.isEmpty()){
            textViewName.setText(song.getTitle());
            textViewSingle.setText(song.getSinger());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionSelect = position;

                Intent intent = new Intent(context, ListenToMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_song",song);
                bundle.putSerializable("listSong_key", listSong);
                bundle.putInt("position_key", position);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return convertView;
    }


}
