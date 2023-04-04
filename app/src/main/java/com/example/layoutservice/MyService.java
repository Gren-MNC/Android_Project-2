package com.example.layoutservice;

import static com.example.layoutservice.MyApp.Chanel_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.layoutservice.Activity.ListenToMusicActivity;
import com.example.layoutservice.Models.Song;
import com.example.layoutservice.Receiver.BroadcastReceiver;

public class MyService extends Service {
    private boolean isPlaying;
    private Song mSong;
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
            Song song = (Song) bundle.get("object_song");
            actionFromActivity = bundle.getInt("action_music_to_service");
            if(song!=null)
            {
                mSong = song;
                handleActionMusic(actionFromActivity);
                sendNotification(song);
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
        sendNotification(mSong);
        sendActionToActivity(ACTION_START);
    }
    private void pauseMusic() {
        if(isPlaying == true){
            Log.e("Mess","Music On Pause");
            isPlaying = false;
            sendNotification(mSong);
            sendActionToActivity(ACTION_PAUSE);
        }
    }
    private void resumeMusic() {
        if(isPlaying == false){
            Log.e("Mess","Music On Resume");
            isPlaying = true;
            sendNotification(mSong);
            sendActionToActivity(ACTION_RESUME);
        }
    }
    private void nextMusic(){
        Log.e("Mess","Next Music");
        sendNotification(mSong);
        sendActionToActivity(ACTION_NEXT);
    }
    private void preMusic(){
        Log.e("Mess","Pre Music");
        sendNotification(mSong);
        sendActionToActivity(ACTION_PRE);
    }
    private void sendNotification(Song song) {
        Intent intent = new Intent(this, ListenToMusicActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),song.getImage());

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_service);
        remoteViews.setTextViewText(R.id.tv_song,song.getTitle());
        remoteViews.setTextViewText(R.id.tv_single,song.getSinger());
        //remoteViews.setImageViewBitmap(R.id.img_song,bitmap);

        remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_pause);

        if(isPlaying == true)
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_play,getPendingIntent(this,ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_pause);
        }
        else
        {
            remoteViews.setOnClickPendingIntent(R.id.btn_pause_or_play,getPendingIntent(this,ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.btn_pause_or_play, R.drawable.ic_play);
        }
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, getPendingIntent(this,ACTION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next, getPendingIntent(this,ACTION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.img_cancel,getPendingIntent(this,ACTION_CLEAR));

        Notification notification = new NotificationCompat.Builder(this,Chanel_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();
        startForeground(1, notification);
    }
    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, BroadcastReceiver.class);
        intent.putExtra("action_music",action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /*@Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("Mess","My Service OnDestroy");
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }*/
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