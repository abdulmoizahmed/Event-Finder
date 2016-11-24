package com.appswallet.jamatfinder.firebase_services;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import com.appswallet.jamatfinder.MainActivity;
import com.appswallet.jamatfinder.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Maaz on 11/16/2016.
 */
public class FirebaseMessageService extends FirebaseMessagingService {

    private static String TAG="MessageRecieved";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // super.onMessageReceived(remoteMessage);
        Log.d(TAG,remoteMessage.getFrom());


        //check if message contains data

        if(remoteMessage.getData().size() > 0){
            displayNotification(" "+remoteMessage.getData());
        }

//        if(remoteMessage.getNotification().getBody() != null) {
//            Log.d(TAG,"Mesage Data"+remoteMessage.getData());
//            displayNotification(remoteMessage.getNotification().getBody());
//        }

    }

private void displayNotification(String body)
{
    Intent intent=new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
    Uri notificationsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.g_shine)
            .setContentTitle("New Delivery Request")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationsound)
            .setContentIntent(pendingIntent);
    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify((int) System.currentTimeMillis(),nBuilder.build());


}

}
