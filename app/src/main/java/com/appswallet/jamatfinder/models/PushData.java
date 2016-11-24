package com.appswallet.jamatfinder.models;

/**
 * Created by Maaz on 11/22/2016.
 */
public class PushData {

    private Data data;
    private String to;

    public PushData() {
    }

    public PushData(Data data, String to) {
        this.data = data;
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
