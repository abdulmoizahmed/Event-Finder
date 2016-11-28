package com.appswallet.jamatfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.appswallet.jamatfinder.firebase_push_notification.main.MainCallin;
import com.appswallet.jamatfinder.firebase_push_notification.screens.LogInScreen;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button backBtn, getUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = (Button)findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LogInScreen.class));
            }
        });

        getUsers = (Button)findViewById(R.id.getUserButton);
        getUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainCallin.getAndNotifyUser(getApplicationContext(),"Chalo beta jaldi se ye message parho !");
            }
        });
    }
}
