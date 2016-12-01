package com.appswallet.jamatfinder.firebase_push_notification.services;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import com.appswallet.jamatfinder.R;
import com.appswallet.jamatfinder.Screens.MainActivity;
import com.appswallet.jamatfinder.Utils.MyLocation;
import com.appswallet.jamatfinder.firebase_push_notification.models.Data;
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



//        Location location = MyLocation.getInstance().getLocation();
//        location.setLatitude(latitude);
//        location.setLongitude(longitude);


        if(remoteMessage.getData().size() > 0){
            displayNotification(remoteMessage.getData());
        }

    }

private void displayNotification(Map<String, String> body)
{
    Intent intent=new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    Bundle bundle = new Bundle();

    bundle.putDouble("latitude", Double.parseDouble(body.get("latitude")));
    bundle.putDouble("longitude", Double.parseDouble(body.get("longitude")));
    intent.putExtras(bundle);

    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
    Uri notificationsound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder nBuilder=new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.masjid)
            .setContentTitle("New Masjid Created")
            .setContentText(body.get("message"))
            .setAutoCancel(true)
            .setSound(notificationsound)
            .setContentIntent(pendingIntent);
    NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify((int) System.currentTimeMillis(),nBuilder.build());


}

}
