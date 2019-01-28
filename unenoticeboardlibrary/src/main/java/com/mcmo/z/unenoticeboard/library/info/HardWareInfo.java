package com.mcmo.z.unenoticeboard.library.info;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class HardWareInfo implements FormatAble {
    String facturer;//厂商信息
    String product;//产品名
    String brand;//手机品牌
    String model;//手机型号
    String board;//主板名
    String device;//设备名
    String imei;//IMEI

    public HardWareInfo(@Nullable Context context) {
        facturer = Build.MANUFACTURER;
        product = Build.PRODUCT;
        brand = Build.BRAND;
        model = Build.MODEL;
        board = Build.BOARD;
        device = Build.DEVICE;
        if (context != null) {
            int checkPermission = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                imei = getIMEI(context);
            }
        }
    }

    /**
     * 获取设备唯一表示，需要"android.permission.READ_Phone_STATE"权限
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId == null ? "Unknown" : deviceId;
    }

    public String getFacturer() {
        return facturer;
    }

    public String getProduct() {
        return product;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getBoard() {
        return board;
    }

    public String getDevice() {
        return device;
    }

    public String getImei() {
        return imei;
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append("厂商信息 ： ").append(facturer).append("\n");
        builder.append("产品名 ： ").append(product).append("\n");
        builder.append("手机品牌 ： ").append(brand).append("\n");
        builder.append("手机型号 ： ").append(model).append("\n");
        builder.append("主板 ： ").append(board).append("\n");
        builder.append("设备名 ： ").append(device).append("\n");
        builder.append("IMEI ： ").append(imei).append("\n");
        return builder.toString();
    }
}
