package com.example.layoutservice.Receiver;

import android.content.Context;
import android.content.Intent;

import com.example.layoutservice.MyService;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        int actionService = intent.getIntExtra("action_from_service",0);
        Intent intentService = new Intent(context, MyService.class);
        intentService.putExtra("action_music_service",actionService);
        context.startService(intentService);

    }
}

