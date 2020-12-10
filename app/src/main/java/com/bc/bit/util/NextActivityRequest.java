package com.bc.bit.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/14.
 */

public class NextActivityRequest {

    private Context context;
    private Intent intent;
    private int requestCode;
    private boolean closeCurrent;

    private NextActivityRequest(Context context, Class<?> cls) {
        intent = new Intent(context, cls);
        requestCode = Integer.MAX_VALUE;
        closeCurrent = false;
        this.context = context;
    }

    public static NextActivityRequest with(Context context, Class<?> cls) {
        return new NextActivityRequest(context, cls);
    }

    public NextActivityRequest put(String key, String value) {
        intent.putExtra(key, value);
        return this;
    }

    public NextActivityRequest put(String key, Serializable value) {
        intent.putExtra(key, value);
        return this;
    }

    public NextActivityRequest put(String key, int value) {
        intent.putExtra(key, value);
        return this;
    }

    public NextActivityRequest requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public NextActivityRequest closeCurrent(boolean close) {
        this.closeCurrent = close;
        return this;
    }

    public void go() {

        if (requestCode != Integer.MAX_VALUE) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }

        if (closeCurrent) {
            ((Activity) context).finish();
        }
    }
}
