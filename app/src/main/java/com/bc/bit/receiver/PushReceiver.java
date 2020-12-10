package com.bc.bit.receiver;

import android.content.Context;
import android.content.Intent;


import com.bc.bit.MyApplication;
import com.bc.bit.activity.StartUpActivity;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;


/**
 * 极光推送广播接收者
 * <p>
 */
public class PushReceiver extends JPushMessageReceiver {

    private static final String TAG = "PushReceiver";

    /**
     * 当后台推送自定义消息时调用
     *
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);

        try {
            //对应控制台自定义消息推送内容
            //对应api的message字段
            String messageString = customMessage.toString();

            //打印日志
//            LogUtil.e(TAG, messageString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        if (MyApplication.getRefCount()==0){
            try {
                Intent intent = new Intent(context, StartUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
