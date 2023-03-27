package com.example.layoutservice.CustomListViewActivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.layoutservice.Adapter.SongFavoriteAdapter;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;
import java.util.List;

public class ListViewDownActivity extends AppCompatActivity {
    private List<Song> listSong;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_download);

        listView = (ListView) findViewById(R.id.list_view_download);
        listSong = new ArrayList<>();
        listSong.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single Khánh Ngân",R.drawable.ic_music,12));
        SongFavoriteAdapter adapter = new SongFavoriteAdapter(this, R.layout.layout_listview_down, listSong);
        listView.setAdapter(adapter);
    }
    void displaySongs(){

    }
}
