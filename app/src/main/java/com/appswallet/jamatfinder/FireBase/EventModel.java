package com.appswallet.jamatfinder.FireBase;

/**
 * Created by android on 11/11/16.
 */

public class EventModel {
        private String eventName;
        private String eventlocationName;
        private String eventTime;
        private double latitude;
        private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventlocationName() {
        return eventlocationName;
    }

    public void setEventlocationName(String eventlocationName) {
        this.eventlocationName = eventlocationName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }





}
