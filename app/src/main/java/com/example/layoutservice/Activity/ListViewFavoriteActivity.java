package com.example.layoutservice.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.layoutservice.Adapter.SongFavoriteAdapter;
import com.example.layoutservice.R;
import com.example.layoutservice.Models.Song;

import java.util.ArrayList;

public class ListViewFavoriteActivity extends AppCompatActivity {
    private ArrayList<Song> listSong;
    private ListView listView;
    private Button btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_favorite);

        btnBack = findViewById(R.id.btn_back_favorite);

        listView = (ListView) findViewById(R.id.list_view_favorite);
        listSong = new ArrayList<>();
        listSong.add(new Song("Cô đơn trên sofa(Cover)","Trung Quân Idol",R.drawable.ic_music,R.raw.codontrensofa_cover_trungquan));
        listSong.add(new Song("Cứu vãn kịp không","Vương Anh Tú",R.drawable.ic_music,R.raw.cuuvankipkhong_vuonganhtu));
        listSong.add(new Song("Nếu lúc đó","Tlinh",R.drawable.ic_music,R.raw.neulucdo_tlinh));
        listSong.add(new Song("Ngủ một mình","HieuThuHai",R.drawable.ic_music,R.raw.ngumotminh_hieuthuhai));

        SongFavoriteAdapter adapter = new SongFavoriteAdapter(this, R.layout.layout_listview, listSong);
        listView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
