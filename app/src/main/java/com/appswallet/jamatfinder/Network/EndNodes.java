package com.appswallet.jamatfinder.Network;

/**
 * Created by android on 11/30/16.
 */

//http://api.aladhan.com/timings/1480491069?latitude=24.8573473&longitude=67.0455685&timezonestring=Asia/Karachi&method=1&school=1

public class EndNodes  {
    private  EndNodes() {}
    public static EndNodes instance = null;
    public String fullUrl;
    //public static final String BASE_URI = "http://api.aladhan.com/timings/1480491069?latitude=24.8573473&longitude=67.0455685&timezonestring=Asia/Karachi&method=1&school=1";
    public static final String BASE_URI = "http://api.aladhan.com/" ;
    public static final String Endpoint_URI = "timings/"+"1480491069";

    public String BASE_URL= "http://api.aladhan.com/timings/";
    public String LATITUDE= "?latitude=";
    public String LONGITUDE= "&longitude=";
    public String TIMEZONE= "&timezonestring=Asia/Karachi";
    public String EXTRA= "&method=1&school=1";

    public static EndNodes getInstance()
    {
        if(instance == null)
        {
            instance = new EndNodes();
        }

        return instance;
    }
        public void createUrl(String timeStamp,String latitude,String longitude)
    {
        fullUrl = BASE_URL+timeStamp+LATITUDE+latitude+LONGITUDE+longitude+TIMEZONE+EXTRA;
    }
    public String getUrl()
    {
        return fullUrl;
    }

}
