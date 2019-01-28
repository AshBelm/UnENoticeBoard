package com.mcmo.z.unenoticeboard.library.info;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class InfoUtil {
    public static ScreenInfo createScreenInfo(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            wm.getDefaultDisplay().getRealMetrics(displayMetrics);
        }else {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return new ScreenInfo(displayMetrics);
    }

    public static String createBaseInfoString(ExceptionInfo exceptionInfo){
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

}
