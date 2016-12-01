package com.appswallet.jamatfinder.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.appswallet.jamatfinder.R;

/**
 * Created by android on 12/1/16.
 */

public class JamatReceiver extends BroadcastReceiver {
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        showNotification(intent);

    }


    private void showNotification(Intent intent) {


        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.masjid)
                .setContentTitle("Namaz")
                .setContentText("Namaz time is started")
                .setAutoCancel(true)
                .setSound(notificationsound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(),nBuilder.build());

    }
}
