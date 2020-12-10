package com.bc.bit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.bc.bit.util.ToastUtil;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks{
    /**
     * 上下文
     */
    private static MyApplication context;
    private static String channelValue = "";
    private static int refCount = 0;// 判断应用是否在前台
    public static final String TAG = "MyApplication";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 初始化MultiDex
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MyContext.create(this).loadUser().getBaseUrl();
        //注册界面声明周期监听
        registerActivityLifecycleCallbacks(this);
        // 初始化toast工具类
        ToastUtil.init(getApplicationContext());
        getMetaData();
//        //初始化极光推送
        JPushInterface.init(this);
//        // 推送设备id  魅蓝note3:190e35f7e0c19073622
//        String rid = JPushInterface.getRegistrationID(getApplicationContext());
//        LogUtil.e(TAG,rid);
    }

    private void getMetaData() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("CHANNEL");
            // debug 阶段渠道为华为
            channelValue = channel != null ? channel : "bcbtbhw";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }



    /**
     * 获取当前上下文
     *
     * @return
     */
    public static MyApplication getInstance() {
        return context;
    }


    public static String getChannelValue() {
        return channelValue;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        refCount++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        refCount--;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     *  获取应用当前状态
     * @return
     */
    public static int getRefCount() {
        return refCount;
    }
}
