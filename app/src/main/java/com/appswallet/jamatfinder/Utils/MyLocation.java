package com.appswallet.jamatfinder.Utils;

import android.location.Location;

/**
 * Created by android on 11/17/16.
 */

public class MyLocation {

    public static MyLocation instance = null;
    private Location location;
    private boolean accessPermission = true;

    private MyLocation(){

    }

    public static MyLocation getInstance()
    {
        if(instance == null)
        {
            instance = new MyLocation();
        }


        return instance;
    }


    public Location getLocation() {

        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAccessPermission() {
        return accessPermission;
    }

    public void setAccessPermission(boolean accessPermission) {
        this.accessPermission = accessPermission;
    }
}
