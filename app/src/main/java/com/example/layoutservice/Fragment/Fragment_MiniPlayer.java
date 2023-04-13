package com.example.layoutservice.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Activity.ListSongSingleActivity;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.MyService;
import com.example.layoutservice.R;
import com.example.layoutservice.Receiver.BroadcastReceiver;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_MiniPlayer extends Fragment {
    ImageView btn_pause, btn_cancel;
    public static final int REQUEST_CODE = 1;
    ArrayList<MusicFiles> musicFiles;
    RelativeLayout bottom_mini;
    TextView txtSong, txtSinger;
    private MusicFiles mSong;
    private boolean isPlaying;
    private ImageView imgCancel, imgSong;
    View view;
    public Fragment_MiniPlayer(){}

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle == null)
            {                return;
            }
            mSong = (MusicFiles) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_music");
            int action = bundle.getInt("action_music");

            handleLayoutMusic(action);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_miniplayer,container,false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("send_data_to_activity"));
        bottom_mini = view.findViewById(R.id.bottom_layout);
        txtSinger = view.findViewById(R.id.tv_single);
        txtSong = view.findViewById(R.id.tv_song);
        imgSong = view.findViewById(R.id.img_song);
        btn_pause = view.findViewById(R.id.img_pause_or_play);
        btn_cancel = view.findViewById(R.id.img_cancel);
        permission();

        return view;
    }
    private void handleLayoutMusic(int action) {

        switch (action)
        {
            case MyService.ACTION_START:
                bottom_mini.setVisibility(View.VISIBLE);
                showInfoSong();
                break;
            case MyService.ACTION_PAUSE:
                setStatusButtonPauseOrPlay();
                break;
            case MyService.ACTION_RESUME:
                setStatusButtonPauseOrPlay();
                break;
            case  MyService.ACTION_CLEAR:
                bottom_mini.setVisibility(View.GONE);
                break;
        }
    }
    private void permission() {
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        else {
            Toast.makeText(getActivity(),"Permission Granted",Toast.LENGTH_SHORT).show();
            musicFiles = getAllAudio(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(),"Permission Granted",Toast.LENGTH_SHORT).show();
                musicFiles = getAllAudio(getActivity());
            }
            else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
        }
    }
    public static ArrayList<MusicFiles> getAllAudio(Context context){
        ArrayList<MusicFiles> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                MusicFiles musicFiles = new MusicFiles(path,title,artist,album,duration);
                Log.e("Path: "+path,"Album: "+album);
                tempAudioList.add(musicFiles);
            }
            cursor.close();
        }
        return tempAudioList;
    }
    private void setStatusButtonPauseOrPlay(){
        if(isPlaying){
            btn_pause.setImageResource(R.drawable.ic_pause);
        }
        else {
            btn_pause.setImageResource(R.drawable.ic_play2);
        }
    }
    private void showInfoSong(){
        if(mSong == null)
        {
            return;
        }
        byte[] image = getAlbumArt(mSong.getPath());
        if(image != null){
            Glide.with(this).asBitmap()
                    .load(image)
                    .into(imgSong);
        }
        else {
            Glide.with(this)
                    .load(R.drawable.ic_music)
                    .into(imgSong);
        }

        txtSong.setText(mSong.getTitle());
        txtSinger.setText(mSong.getArtist());

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    sendActionToService(MyService.ACTION_PAUSE);
                }
                else {
                    sendActionToService(MyService.ACTION_RESUME);
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(MyService.ACTION_CLEAR);
            }
        });
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return art;
    }
    private void sendActionToService(int action) {
        Intent intent = new Intent(getActivity(),MyService.class);
        intent.putExtra("action_music_service",action);
        getActivity().startService(intent);
    }
}
