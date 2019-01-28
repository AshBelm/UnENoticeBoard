package com.mcmo.z.unenoticeboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mcmo.z.unenoticeboard.library.ErrorActivity;
import com.mcmo.z.unenoticeboard.library.info.HardWareInfo;
import com.mcmo.z.unenoticeboard.library.info.InfoUtil;
import com.mcmo.z.unenoticeboard.library.info.OsInfo;
import com.mcmo.z.unenoticeboard.library.info.ScreenInfo;
import com.mcmo.z.unenoticeboard.library.info.StorageInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        int a = 5/0;
    }
}
