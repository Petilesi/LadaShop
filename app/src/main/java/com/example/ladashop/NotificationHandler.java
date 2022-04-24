package com.example.ladashop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "shop_notification_channel";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager vNotiffManager;
    private Context vContext;

    public NotificationHandler(Context context) {
       this.vContext = context;
       this.vNotiffManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       createChannel();
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "LadaShop notification", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.YELLOW);
        channel.setDescription("A LadaSHop értesítései");
        this.vNotiffManager.createNotificationChannel(channel);
    }

    public void send(String message){
        Intent intent = new Intent(vContext, ListingsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(vContext,0,intent, PendingIntent.FLAG_MUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(vContext, CHANNEL_ID)
                .setContentTitle("LadaShop")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_directions_car_24)
                .setContentIntent(pendingIntent);
        this.vNotiffManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void cancel(){
        this.vNotiffManager.cancel(NOTIFICATION_ID);
    }
}
