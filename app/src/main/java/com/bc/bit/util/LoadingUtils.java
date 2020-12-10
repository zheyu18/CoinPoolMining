package com.bc.bit.util;

import android.app.Activity;

import com.bc.bit.R;
import com.bc.bit.view.CustomProgressDialog;


/**
 * 加载提示框
 */
public class LoadingUtils {

    private static CustomProgressDialog progressDialog;

    /**
     * 显示一个加载对话框，使用默认提示文字
     * @param activity
     */
    public static void showLoading(Activity activity) {
        showLoading(activity,activity.getString(R.string.loading));
    }

    /**
     * 显示一个加载对话框
     * @param activity
     * @param message
     */
    public static void showLoading(Activity activity, String message) {
        //activity为空或者已经销毁了
        if (activity == null || activity.isFinishing()) {
            return;
        }

        //判断是否显示了
        if (progressDialog == null) {
            //创建一个进度对话框
            progressDialog = new CustomProgressDialog(activity,message);
        }

        //点击弹窗外部不会自动隐藏
        progressDialog.setCancelable(false);

        //显示
        progressDialog.show();

    }

    /**
     * 显示一个加载对话框
     *
     * @param activity
     * @param resourceId
     */
    public static void showLoading(Activity activity, int resourceId) {
        String data = activity.getResources().getString(resourceId);
        showLoading(activity, data);
    }

    /**
     * 隐藏加载提示对话框
     */
    public static void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}
