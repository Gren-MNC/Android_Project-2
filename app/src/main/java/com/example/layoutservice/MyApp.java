package com.example.layoutservice;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApp extends Application {
    public static final String Chanel_ID = "chanel_service_example";
    @Override
    public void onCreate(){
        super.onCreate();
        createChanelNotification();
    }
    private void createChanelNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(Chanel_ID,"Chanel Service Example", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null)
            {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
