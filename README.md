# UnENoticeBoard
未捕获异常记录工具。会在发生异常时记录日志并弹出错误信息提示。

##初始化
在项目的Application中调用初始化方法
```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在release版本中不使用日志记录
        if(BuildConfig.DEBUG){
            UncaughtHandler.init(this);
        }
    }
}
```