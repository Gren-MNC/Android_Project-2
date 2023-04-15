package com.example.layoutservice;

import static com.example.layoutservice.MyApp.Chanel_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Models.MusicFiles;
import com.example.layoutservice.Models.SongFireBase;
import com.example.layoutservice.Receiver.BroadcastReceiver;

import java.io.IOException;

public class MyService extends Service {
    private boolean isPlaying;
    private SongFireBase mSong;
    private int actionFromActivity;
    private int actionService;
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_CLEAR = 3;
    public static final int ACTION_START = 4;
    public static final int ACTION_NEXT = 5;
    public static final int ACTION_PRE = 6;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.e("Mess","My Service OnCreate");
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            SongFireBase song = (SongFireBase) bundle.get("object_song");
            actionFromActivity = bundle.getInt("action_music_to_service");
            if(song!=null)
            {
                mSong = song;
                handleActionMusic(actionFromActivity);
                sendNotificationMedia(song);
            }
        }
        actionService = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionService);
        return START_NOT_STICKY;
    }
    private void handleActionMusic(int action)
    {
        switch (action)
        {
            case  ACTION_START:
                startMusic();
                break;

            case ACTION_PAUSE:
                pauseMusic();
                break;

            case ACTION_RESUME:
                resumeMusic();
                break;

            case ACTION_NEXT:
                nextMusic();
                break;

            case ACTION_PRE:
                preMusic();
                break;
            case ACTION_CLEAR:
                stopSelf();
                sendActionToActivity(ACTION_CLEAR);
                break;
        }
    }
    private  void startMusic(){
        isPlaying = true;
        sendNotificationMedia(mSong);
        sendActionToActivity(ACTION_START);
    }
    private void pauseMusic() {
        if(isPlaying == true){
            Log.e("Mess","Music On Pause");
            isPlaying = false;
            sendNotificationMedia(mSong);
            sendActionToActivity(ACTION_PAUSE);
        }
    }
    private void resumeMusic() {
        if(isPlaying == false){
            Log.e("Mess","Music On Resume");
            isPlaying = true;
            sendNotificationMedia(mSong);
            sendActionToActivity(ACTION_RESUME);
        }
    }
    private void nextMusic(){
        Log.e("Mess","Next Music");
        sendNotificationMedia(mSong);
        sendActionToActivity(ACTION_NEXT);
    }
    private void preMusic(){
        Log.e("Mess","Pre Music");
        sendNotificationMedia(mSong);
        sendActionToActivity(ACTION_PRE);
    }
//    private void sendNotification(MusicFiles song) {
//        Intent intent = new Intent(this, ListenToMusicActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),song.getImage());
//
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_service);
//        remoteViews.setTextViewText(R.id.tv_song,song.getTitle());
//        remoteViews.setTextViewText(R.id.tv_single,song.getArtist());
//        //remoteViews.setImageViewBitmap(R.id.img_song,bitmap);
//
//        remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_pause);
//
//        if(isPlaying == true)
//        {
//            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_play,getPendingIntent(this,ACTION_PAUSE));
//            remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_pause);
//        }
//        else
//        {
//            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_play,getPendingIntent(this,ACTION_RESUME));
//            remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_play);
//        }
//        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getPendingIntent(this,ACTION_PRE));
//        remoteViews.setOnClickPendingIntent(R.id.btn_next, getPendingIntent(this,ACTION_NEXT));
//        remoteViews.setOnClickPendingIntent(R.id.img_cancel,getPendingIntent(this,ACTION_CLEAR));
//
//        Notification notification = new NotificationCompat.Builder(this,Chanel_ID)
//                .setSmallIcon(R.drawable.ic_home)
//                .setContentIntent(pendingIntent)
//                .setCustomContentView(remoteViews)
//                .setSound(null)
//                .build();
//        startForeground(1, notification);
//    }
    private void sendNotificationMedia(SongFireBase mSong){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_music);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this,"tag");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,Chanel_ID)
                .setSmallIcon(R.drawable.ic_music)
                .setSubText("Name App")
                .setLargeIcon(bitmap)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle(mSong.getTitle())
                .setContentText(mSong.getSinger())
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(bitmap))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                       .setShowActionsInCompactView(0,1,2 /* #1: pause button */)
                       .setMediaSession(mediaSessionCompat.getSessionToken()));


        if(isPlaying){
            notificationBuilder
                    .addAction(R.drawable.ic_pre,"Previous",getPendingIntent(this,ACTION_PRE))
                    .addAction(R.drawable.ic_pause,"Pause",getPendingIntent(this,ACTION_PAUSE))
                    .addAction(R.drawable.ic_next,"Next",getPendingIntent(this,ACTION_NEXT));
        }
        else{
            notificationBuilder
                    .addAction(R.drawable.ic_pre,"Previous",getPendingIntent(this,ACTION_PRE))
                    .addAction(R.drawable.ic_play2,"Pause",getPendingIntent(this,ACTION_RESUME))
                    .addAction(R.drawable.ic_next,"Next",getPendingIntent(this,ACTION_NEXT));
        }
        Notification notification = notificationBuilder
                .setPriority(NotificationCompat.PRIORITY_MAX).build();
        startForeground(1,notification);
    }
    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, BroadcastReceiver.class);
        intent.putExtra("action_music",action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
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

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Log.e("Mess","My Service OnDestroy");
//        if(mediaPlayer != null)
//        {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
    private void sendActionToActivity(int action){
        Intent intent = new Intent("send_data_to_activity");
        Bundle bundle = new Bundle();
        bundle.putInt("action_music",action);
        bundle.putSerializable("object_song",mSong);
        bundle.putBoolean("status_music",isPlaying);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}