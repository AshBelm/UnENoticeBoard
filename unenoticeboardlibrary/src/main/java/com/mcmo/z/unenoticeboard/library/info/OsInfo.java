package com.mcmo.z.unenoticeboard.library.info;

import android.os.Build;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.Locale;

public class OsInfo implements FormatAble {
    String deviceSerial;//硬件序列号
    int sdkInt;//
    String androidVersion;//Android版本
    String[] abis;
    String language;
    public OsInfo() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            deviceSerial = Build.getSerial();
//        }
        deviceSerial = Build.SERIAL;
        sdkInt = Build.VERSION.SDK_INT;
        androidVersion = Build.VERSION.RELEASE;
        language = Locale.getDefault().getLanguage();
        abis = getABIs();
    }

    /**
     * 获取cpu架构
     * @return
     */
    public static String[] getABIs(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        }else{
            if(!TextUtils.isEmpty(Build.CPU_ABI2)){
                return new String[]{Build.CPU_ABI,Build.CPU_ABI2};
            }
            return new String[]{Build.CPU_ABI};
        }
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public int getSdkInt() {
        return sdkInt;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public String[] getAbis() {
        return abis;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append("序列号 ： ").append(deviceSerial).append("\n");
        builder.append("系统版本 ： ").append(androidVersion).append("\n");
        builder.append("sdk ： ").append(sdkInt).append("\n");
        builder.append("cpu ： ").append(Arrays.toString(abis)).append("\n");
        return builder.toString();
    }
}
