package com.example.layoutservice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.layoutservice.R;

public class Fragment_MiniPlayer extends Fragment {
    ImageView btn_pause, btn_cancel;
    TextView txtSong, txtSinger;
    View view;
    public Fragment_MiniPlayer(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miniplayer,container,false);

        txtSinger = view.findViewById(R.id.tv_single);
        txtSong = view.findViewById(R.id.tv_song);
        btn_pause = view.findViewById(R.id.btn_pause_or_play);
        btn_cancel = view.findViewById(R.id.img_cancel);

        return view;
    }
}
