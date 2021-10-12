package com.example.onetimerequestusingonworkmanager.Comman;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.onetimerequestusingonworkmanager.R;

public class Notifications {

    public void createNotification(String task, String title, Context mContext) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_ID, Constants.NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, Constants.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(task)
                    .setContentText(title)
                    .setSmallIcon(R.mipmap.ic_launcher);

            manager.notify(1, builder.build());
        }
    }
}
