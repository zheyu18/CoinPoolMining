package com.bc.bit.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;


/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 上下文
     */
    private static Context context;

    /**
     * 初始化方法
     *
     * @param context
     */
    public static void init(Context context) {
        ToastUtil.context = context;
    }

    /**
     * 显示短时间错误toast
     *
     * @param id
     */
    public static void errorShortToast(@StringRes int id) {
        Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示短时间错误toast
     *
     * @param message
     */
    public static void errorShortToast(String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长时间错误toast
     *
     * @param id
     */
    public static void errorLongToast(@StringRes int id) {
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示短时间正确toast
     *
     * @param id
     */
    public static void successShortToast(@StringRes int id) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示短时间正确toast
     *
     * @param data
     */
    public static void successShortToast(String data) {
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }
}
