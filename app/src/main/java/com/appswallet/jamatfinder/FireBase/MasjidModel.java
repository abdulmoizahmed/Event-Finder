package com.appswallet.jamatfinder.FireBase;

/**
 * Created by android on 11/18/16.
 */

public class MasjidModel {

    private String masjidName;
    private String masjidlocationName;
    private String fajarTime;
    private String zuharTime;
    private String asarTime;
    private String magribTime;
    private String ishaTime;
    private double latitude;
    private double longitude;


    public String getMasjidName() {
        return masjidName;
    }

    public void setMasjidName(String masjidName) {
        this.masjidName = masjidName;
    }

    public String getMasjidlocationName() {
        return masjidlocationName;
    }

    public void setMasjidlocationName(String masjidlocationName) {
        this.masjidlocationName = masjidlocationName;
    }

    public String getFajarTime() {
        return fajarTime;
    }

    public void setFajarTime(String fajarTime) {
        this.fajarTime = fajarTime;
    }

    public String getZuharTime() {
        return zuharTime;
    }

    public void setZuharTime(String zuharTime) {
        this.zuharTime = zuharTime;
    }

    public String getAsarTime() {
        return asarTime;
    }

    public void setAsarTime(String asarTime) {
        this.asarTime = asarTime;
    }

    public String getMagribTime() {
        return magribTime;
    }

    public void setMagribTime(String magribTime) {
        this.magribTime = magribTime;
    }

    public String getIshaTime() {
        return ishaTime;
    }

    public void setIshaTime(String ishaTime) {
        this.ishaTime = ishaTime;
    }

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
}
