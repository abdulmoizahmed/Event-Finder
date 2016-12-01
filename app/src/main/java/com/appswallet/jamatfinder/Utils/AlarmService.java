package com.appswallet.jamatfinder.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.appswallet.jamatfinder.Network.BLL.TimeBLL;

public class AlarmService extends Service {
    public AlarmService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FetchNamaz();
        return START_STICKY;
    }

    private void FetchNamaz() {
        //TimeBLL.getTiming(MyLocation.getInstance().getLocation().getLatitude(),MyLocation.getInstance().getLocation().getLongitude(),"Asia/Karachi");
        CommonUtils.getInstance().fetchNamazTime();
        Toast.makeText(getApplicationContext(), CommonUtils.getInstance().getTimes().getFajr(),Toast.LENGTH_SHORT).show();


    }
}
