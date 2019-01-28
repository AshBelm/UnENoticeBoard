package com.mcmo.z.unenoticeboard.library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Process;
import android.util.Log;

import com.mcmo.z.unenoticeboard.library.info.ExceptionInfo;
import com.mcmo.z.unenoticeboard.library.info.InfoUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class UncaughtHandler implements Thread.UncaughtExceptionHandler {
    private final String ACTION = "com.mcmo.z.unenoticeboard.ERRORACT";
    private Context context;
    private boolean showInfoActivity;
    private Thread.UncaughtExceptionHandler mSystemDefaultUncaughtExceptionHandler;
    public static final String logFolder = "uncaughtError";
    private String logBasePath;
    public static final String KEY = "ErrorInfo";

    public static void init(Application context) {
        init(context, true);
    }

    public static void init(Application context, boolean showInfoActivity) {
        UncaughtHandler uncaughtHandler = new UncaughtHandler(context, showInfoActivity);
        uncaughtHandler.setToDefaultHandler();
    }

    /**
     * @param context
     * @param showInfoActivity 是否显示错误信息界面
     */
    private UncaughtHandler(Application context, boolean showInfoActivity) {
        this.context = context;
        this.showInfoActivity = showInfoActivity;
        this.logBasePath = context.getExternalCacheDir().getPath() + File.separator + logFolder;
        mSystemDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    private void setToDefaultHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        final ExceptionInfo exceptionInfo = parserExceptionInfo(e);
        saveErrorLog(exceptionInfo);
        if (showInfoActivity) {
            startErrorActivity(exceptionInfo);

        }
        //如果这里使用默认的handler处理无法启动上面的activity? ??
        if (!showInfoActivity && mSystemDefaultUncaughtExceptionHandler != null) {
            mSystemDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void startErrorActivity(ExceptionInfo exceptionInfo) {
        if (context != null) {
            Intent intent = new Intent(context, ErrorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ErrorActivity.ERROR_KEY, exceptionInfo);
            context.startActivity(intent);
        }
    }

    private void saveErrorLog(final ExceptionInfo exceptionInfo) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                String content = InfoUtil.createBaseInfoString(exceptionInfo);
                FileIOUtils.writeFileFromString(logBasePath + File.separator + "ErrorLog_" + exceptionInfo.getTimeFormat(), content);
                return null;
            }
        };
        task.execute();
    }

    private ExceptionInfo parserExceptionInfo(Throwable e) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setExceptionMsg(e.getMessage());
        exceptionInfo.setTime(new Date().getTime());
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.flush();
        exceptionInfo.setFullInfo(stringWriter.toString());
        if (e.getCause() != null) {
            e = e.getCause();
        }

        exceptionInfo.setExceptionType(e.getClass().getName());
        if (e.getStackTrace() != null && e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            exceptionInfo.setMethodName(element.getMethodName());
            exceptionInfo.setLineNum(element.getLineNumber());
            exceptionInfo.setFileName(element.getFileName());
            exceptionInfo.setClazzName(element.getClassName());
        }
        return exceptionInfo;
    }
}
