package com.example.layoutservice.CustomListViewActivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.layoutservice.Adapter.ListSongSingleAdapter;
import com.example.layoutservice.Adapter.SongFavoriteAdapter;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSongSingleActivity extends AppCompatActivity {
    private List<Song> listSong;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_single);

        listView = (ListView) findViewById(R.id.list_view_song_single);
        listSong = new ArrayList<>();
        listSong.add(new Song("Quên em đi","Teayang",R.drawable.teayang,12));
        listSong.add(new Song("Quên anh đi","Teayang",R.drawable.teayang,12));
        listSong.add(new Song("Quên em đi","Teayang",R.drawable.teayang,12));
        listSong.add(new Song("Quên anh đi","Teayang",R.drawable.teayang,12));
        ListSongSingleAdapter adapter = new ListSongSingleAdapter(this, R.layout.layout_sub_single, listSong);
        listView.setAdapter(adapter);
    }
}
