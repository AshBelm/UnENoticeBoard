package com.mcmo.z.unenoticeboard;

import android.app.Application;
import android.content.ContextWrapper;

import com.mcmo.z.unenoticeboard.library.UncaughtHandler;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在release版本中不使用日志记录
        if(BuildConfig.DEBUG){
            UncaughtHandler.init(this);
        }

    }
}
