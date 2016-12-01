package com.appswallet.jamatfinder.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.appswallet.jamatfinder.R;

/**
 * Created by android on 12/1/16.
 */

public class AlarmReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

                this.context = context;

        fetchNamaz();

      //  Toast.makeText(context,"this is a Time",Toast.LENGTH_SHORT).show();

    }



    private void fetchNamaz() {
        context.startService(new Intent(context, AlarmService.class));
    }
}
