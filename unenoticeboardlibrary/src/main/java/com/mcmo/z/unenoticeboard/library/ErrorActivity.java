package com.mcmo.z.unenoticeboard.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mcmo.z.unenoticeboard.library.info.ExceptionInfo;
import com.mcmo.z.unenoticeboard.library.info.HardWareInfo;
import com.mcmo.z.unenoticeboard.library.info.InfoUtil;
import com.mcmo.z.unenoticeboard.library.info.OsInfo;
import com.mcmo.z.unenoticeboard.library.info.ScreenInfo;
import com.mcmo.z.unenoticeboard.library.info.StorageInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorActivity extends AppCompatActivity {
    public static final String ERROR_KEY = "error_info";
    private TextView tv_crash_info, tv_class_name, tv_method_name, tv_line_number, tv_error_type, tv_crash_time, tv_full_info;
    private TextView tv_other;
    private ExceptionInfo exceptionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_mcmo);
        Toolbar toolbar = findViewById(R.id.toolBar_mcmo);
        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            exceptionInfo = getIntent().getParcelableExtra(ERROR_KEY);
        }
        if (exceptionInfo != null) {
            tv_crash_info = findViewById(R.id.tv_crash_info);
            tv_class_name = findViewById(R.id.tv_class_name);
            tv_method_name = findViewById(R.id.tv_method_name);
            tv_line_number = findViewById(R.id.tv_line_number);
            tv_error_type = findViewById(R.id.tv_error_type);
            tv_crash_time = findViewById(R.id.tv_crash_time);
            tv_full_info = findViewById(R.id.tv_full_info);
            tv_other = findViewById(R.id.tv_other);
            tv_crash_info.setText(exceptionInfo.getExceptionMsg());
            tv_class_name.setText(exceptionInfo.getClazzName());
            tv_method_name.setText(exceptionInfo.getMethodName());
            tv_line_number.setText(exceptionInfo.getLineNum() + "");
            tv_error_type.setText(exceptionInfo.getExceptionType());
            tv_crash_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(exceptionInfo.getTime())));
            tv_full_info.setText(exceptionInfo.getFullInfo());
        }
        OsInfo osInfo = new OsInfo();
        tv_other.append("系统信息\n");
        tv_other.append(osInfo.format());
        HardWareInfo hardWareInfo = new HardWareInfo(this);
        tv_other.append("\n硬件信息\n");
        tv_other.append(hardWareInfo.format());
        ScreenInfo screenInfo = InfoUtil.createScreenInfo(this);
        tv_other.append("\n屏幕信息\n");
        tv_other.append(screenInfo.format());
        StorageInfo storageInfo = new StorageInfo(this);
        tv_other.append("\n存储信\n");
        tv_other.append(storageInfo.format());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_error, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_copyToClipboard) {
            StringBuilder builder = new StringBuilder();
            if (exceptionInfo != null) {
                builder.append(exceptionInfo.format());
            }
            builder.append("\n");
            builder.append(tv_other.getText().toString());
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", builder.toString());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.menu_showErrorFolderPath) {
            String path = getExternalCacheDir() + File.separator + UncaughtHandler.logFolder;
            new AlertDialog.Builder(this).setTitle("日志地址").setMessage(path).setPositiveButton("确定",null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //回退会导致报错的界面又出现，重复报错，导致无法退出
        Process.killProcess(Process.myPid());
    }
}
