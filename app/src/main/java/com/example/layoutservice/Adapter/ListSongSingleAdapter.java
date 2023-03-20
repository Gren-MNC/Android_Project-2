package com.example.layoutservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.List;

public class ListSongSingleAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Song> listSong;
    private int positionSelect = -1;

    public ListSongSingleAdapter(Context context, int idLayout, List<Song> listSong){
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
        TextView textViewName = (TextView) convertView.findViewById(R.id.tv_song);
        TextView textViewSingle = (TextView) convertView.findViewById(R.id.tv_single);
        final RelativeLayout layoutListView = (RelativeLayout) convertView.findViewById(R.id.idListSongSingle);
        final Song song = listSong.get(position);

        if(listSong != null && !listSong.isEmpty()){
            textViewName.setText(song.getTitle());
            textViewSingle.setText(song.getSinger());
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, song.getTitle(), Toast.LENGTH_LONG).show();
                positionSelect = position;
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}
