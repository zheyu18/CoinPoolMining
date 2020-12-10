package com.bc.bit.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;


public class AlipayUtil {
    //拦截特定支付标识
    public static boolean isAlipay(String url) {
        if (url.startsWith("alipays:") || url.startsWith("alipay")|| url.contains("shanlaifu")) {
            return true;
        }
        return false;
    }

    public static void goAlipays(Context context, String url) {
        //判断是否安装支付宝
        if (checkAliPayInstalled(context)) {
            goUrl(context, url);
        } else {
            //安装下载支付宝
            updateAlipayDialog(context);
        }
    }

    //直接隐式调用
    private static void goUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    //判断是否安装支付宝app
    private static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    //安装alipay支付宝  弹框和文案根据实际情况定制
    private static synchronized void updateAlipayDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("未检测到支付宝客户端，请安装后重试。")
                .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri alipayUrl = Uri.parse("https://d.alipay.com");
                        context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                    }
                }).setNegativeButton("取消", null).show();
    }
}
