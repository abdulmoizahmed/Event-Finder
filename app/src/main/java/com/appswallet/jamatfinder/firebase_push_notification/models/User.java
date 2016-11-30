package com.appswallet.jamatfinder.firebase_push_notification.models;

import java.io.Serializable;

/**
 * Created by Maaz on 11/16/2016.
 */
public class User implements Serializable{

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userRefreshToken;

    public User(){

    }

    public User(String userEmail, String userPassword, String userRefreshToken) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRefreshToken = userRefreshToken;
    }

    public User(String userEmail, String userRefreshToken) {
        this.userEmail = userEmail;
        this.userRefreshToken = userRefreshToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRefreshToken() {
        return userRefreshToken;
    }

    public void setUserRefreshToken(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

}
