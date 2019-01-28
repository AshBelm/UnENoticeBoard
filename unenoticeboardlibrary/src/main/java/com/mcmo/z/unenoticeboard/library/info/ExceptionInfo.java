package com.mcmo.z.unenoticeboard.library.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionInfo implements Parcelable ,FormatAble {

    private String fullInfo;
    private String clazzName;
    private String methodName;
    private String fileName;
    private int lineNum;
    private String exceptionType;
    private String exceptionMsg;
    private long time;


    public ExceptionInfo(){
    }

    protected ExceptionInfo(Parcel in) {
        fullInfo = in.readString();
        clazzName = in.readString();
        methodName = in.readString();
        fileName = in.readString();
        lineNum = in.readInt();
        exceptionType = in.readString();
        exceptionMsg = in.readString();
        time = in.readLong();
    }

    public static final Creator<ExceptionInfo> CREATOR = new Creator<ExceptionInfo>() {
        @Override
        public ExceptionInfo createFromParcel(Parcel in) {
            return new ExceptionInfo(in);
        }

        @Override
        public ExceptionInfo[] newArray(int size) {
            return new ExceptionInfo[size];
        }
    };

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public void setFullInfo(String fullInfo) {
        this.fullInfo = fullInfo;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public long getTime() {
        return time;
    }
    public String getTimeFormat(){
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(time));
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullInfo);
        dest.writeString(clazzName);
        dest.writeString(methodName);
        dest.writeString(fileName);
        dest.writeInt(lineNum);
        dest.writeString(exceptionType);
        dest.writeString(exceptionMsg);
        dest.writeLong(time);
    }

    @Override
    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append("崩溃信息：\n");
        builder.append(exceptionMsg);
        builder.append("\n");
        builder.append("类名：").append(fileName).append("\n");
        builder.append("方法：").append(methodName).append("\n");
        builder.append("行数：").append(lineNum).append("\n");
        builder.append("错误类型：").append(exceptionType).append("\n");
        builder.append("全部信息：").append(fullInfo).append("\n");
        return builder.toString();
    }
}
