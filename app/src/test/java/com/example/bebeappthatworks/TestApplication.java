package com.example.bebeappthatworks;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
