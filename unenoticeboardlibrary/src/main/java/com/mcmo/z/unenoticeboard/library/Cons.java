package com.mcmo.z.unenoticeboard.library;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cons {
    public static final String LOG_FOLDER = "uncaughtError";
    public static final String LOG_FILE_SUBFIX = "ErrorLog_";

    public static String getLogFilePah(Context context) {
        return context.getExternalCacheDir().getPath() + File.separator + LOG_FOLDER;
    }

    public static String getLogFileName(long time) {
        return LOG_FILE_SUBFIX + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(time));
    }

    public static String getOneDayFileSubfix(long time) {
        return LOG_FILE_SUBFIX + new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    public static String getTodayFileSubfix() {
        return LOG_FILE_SUBFIX + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
