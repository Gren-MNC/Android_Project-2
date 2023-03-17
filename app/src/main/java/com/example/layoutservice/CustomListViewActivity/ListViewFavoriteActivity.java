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

public class ListViewFavoriteActivity extends AppCompatActivity {
    private List<Song> listSong;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_favorite);

        listView = (ListView) findViewById(R.id.list_view_favorite);
        listSong = new ArrayList<>();
        listSong.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên em đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single Khánh Ngân",R.drawable.ic_music,12));
        listSong.add(new Song("Quên em đi","Single A",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single A",R.drawable.ic_music,12));
        listSong.add(new Song("Quên em đi","Single A",R.drawable.ic_music,12));
        listSong.add(new Song("Quên anh đi","Single A",R.drawable.ic_music,12));
        SongFavoriteAdapter adapter = new SongFavoriteAdapter(this, R.layout.layout_listview, listSong);
        listView.setAdapter(adapter);
    }
}
