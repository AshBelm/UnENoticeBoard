package com.mcmo.z.unenoticeboard.library.info;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class InfoUtil {
    public static ScreenInfo createScreenInfo(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.getDefaultDisplay().getRealMetrics(displayMetrics);
            } else {
                wm.getDefaultDisplay().getMetrics(displayMetrics);
            }
        } else {
            displayMetrics = Resources.getSystem().getDisplayMetrics();
        }
        return new ScreenInfo(displayMetrics);
    }

    public static String createBaseInfoString(ExceptionInfo exceptionInfo) {
        OsInfo osInfo = new OsInfo();
        HardWareInfo hardWareInfo = new HardWareInfo(null);
        StringBuilder builder = new StringBuilder();
        builder.append(exceptionInfo.format());
        builder.append("\t\n\t\n");
        builder.append("-------------------------设备信息-------------------------").append("\n");
        builder.append("\n");
        builder.append("手机品牌 ： ").append(hardWareInfo.brand).append("\n");
        builder.append("手机型号 ： ").append(hardWareInfo.model).append("\n");
        builder.append("系统版本 ： ").append(osInfo.androidVersion).append("\n");
        builder.append("sdk ： ").append(osInfo.sdkInt).append("\n");
        return builder.toString();
    }

    public static String createFullInfoString(Context context, ExceptionInfo exceptionInfo) {
        StringBuilder builder = new StringBuilder();
        if (exceptionInfo != null) {
            builder.append(exceptionInfo.format());
        }
        OsInfo osInfo = new OsInfo();
        builder.append("------------------------系统信息------------------------\n");
        builder.append(osInfo.format());
        HardWareInfo hardWareInfo = new HardWareInfo(context);
        builder.append("\n------------------------硬件信息------------------------\n");
        builder.append(hardWareInfo.format());
        ScreenInfo screenInfo = InfoUtil.createScreenInfo(context);
        builder.append("\n------------------------屏幕信息------------------------\n");
        builder.append(screenInfo.format());
        StorageInfo storageInfo = new StorageInfo(context);
        builder.append("\n------------------------存储信息------------------------\n");
        builder.append(storageInfo.format());
        return builder.toString();
    }

}
