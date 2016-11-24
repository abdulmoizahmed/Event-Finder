package com.appswallet.jamatfinder.firebase_services;

import com.appswallet.jamatfinder.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Maaz on 11/16/2016.
 */
public class FireBaseInstantceService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshToken= FirebaseInstanceId.getInstance().getToken();  // Get updated InstanceID token or Current registration token or Referesh token
        //You can save this token in to app server for further work
        sendRefreshToken(refreshToken);
    }

    private void sendRefreshToken(String refreshToken) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user =auth.getCurrentUser();

        if (user!=null) {
//        User userFb = new User(email,password,refreshToken);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("user").child(user.getUid()).child("userRefreshToken");
            myRef.setValue(refreshToken);

        }
    }
}
