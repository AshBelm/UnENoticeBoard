package com.mcmo.z.unenoticeboard.library.info;

import android.util.DisplayMetrics;

public class ScreenInfo implements FormatAble {
    int width, height;//屏幕像素尺寸
    float density;//屏幕密度
    int densityDpi;//屏幕dip
    float xDpi, yDpi;//横向和竖向的dip
    float scaleDensity;//字体的密度

    public ScreenInfo(DisplayMetrics displayMetrics) {
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        density = displayMetrics.density;
        densityDpi = displayMetrics.densityDpi;
        xDpi = displayMetrics.xdpi;
        yDpi = displayMetrics.ydpi;
        scaleDensity = displayMetrics.scaledDensity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getDensity() {
        return density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public float getxDpi() {
        return xDpi;
    }

    public float getyDpi() {
        return yDpi;
    }

    public float getScaleDensity() {
        return scaleDensity;
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append("屏幕尺寸 ： ").append(width + " x " + height + " px\n");
        builder.append("屏幕密度 ： ").append(density + "\n");
        builder.append("屏幕dpi ： " + densityDpi + "  ,横向dpi ： " + xDpi + "  ,竖向dpi ： " + yDpi + "\n");
        builder.append("字体密度 ：").append(scaleDensity).append("\n");
        return builder.toString();
    }
}
