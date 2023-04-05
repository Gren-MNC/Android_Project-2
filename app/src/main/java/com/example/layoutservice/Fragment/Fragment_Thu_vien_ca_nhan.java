package com.example.layoutservice.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.layoutservice.Activity.ListSingerActivity;
import com.example.layoutservice.Activity.ListSongSingleActivity;
import com.example.layoutservice.Activity.ListViewDownActivity;
import com.example.layoutservice.Activity.ListViewFavoriteActivity;
import com.example.layoutservice.R;


public class Fragment_Thu_vien_ca_nhan extends Fragment {

    LinearLayout btnFavoriteSongs;
    LinearLayout btnDownloadedSongs;
    LinearLayout btnListFavoriteSinger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thu_vien_ca_nhan, container, false);
        btnFavoriteSongs = view.findViewById(R.id.bai_hat_yeu_thich);
        btnFavoriteSongs.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListViewFavoriteActivity.class);
            startActivity(intent);
        });
        btnDownloadedSongs = view.findViewById(R.id.bai_hat_down);
        btnDownloadedSongs.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), ListViewDownActivity.class);
            startActivity(intent);
        });
        btnListFavoriteSinger = view.findViewById(R.id.favorite_singer);
        btnListFavoriteSinger.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), ListSingerActivity.class);
            startActivity(intent);
        });
        return view;
    }
}