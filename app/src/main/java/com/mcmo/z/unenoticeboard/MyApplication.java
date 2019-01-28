package com.mcmo.z.unenoticeboard;

import android.app.Application;
import android.content.ContextWrapper;

import com.mcmo.z.unenoticeboard.library.UncaughtHandler;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            UncaughtHandler.init(this);
        }

    }
}
