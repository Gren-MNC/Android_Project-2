package com.example.layoutservice.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.layoutservice.Adapter.SongDownAdapter;
import com.example.layoutservice.R;
import com.example.layoutservice.Song;

import java.util.ArrayList;

public class ListViewDownActivity extends AppCompatActivity {
    private ArrayList<Song> listSong;
    private ListView listView;
    private Button btnBack;
    private Song mSong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_download);
        btnBack = findViewById(R.id.btn_back_down);

        listView = (ListView) findViewById(R.id.list_view_download);
        listSong = new ArrayList<>();
        listSong.add(new Song("Muộn rồi mà sao còn","Sơn Tùng MTP",R.drawable.ic_music,R.raw.muonroimasaocon_sontungmtp));
        listSong.add(new Song("Cứu vãn kịp không","Vương Anh Tú",R.drawable.ic_music,R.raw.cuuvankipkhong_vuonganhtu));
        listSong.add(new Song("Nếu lúc đó","Tlinh",R.drawable.ic_music,R.raw.neulucdo_tlinh));
        listSong.add(new Song("Ngủ một mình","HieuThuHai",R.drawable.ic_music,R.raw.ngumotminh_hieuthuhai));

        SongDownAdapter adapter = new SongDownAdapter(this, R.layout.layout_listview_down, listSong);
        listView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
