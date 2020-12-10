package com.bc.bit.api;


import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.util.HttpUtil;
import com.bc.bit.util.LoadingUtils;
import com.bc.bit.util.LogUtil;

import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * 网络请求Observer
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {
    private static final String TAG = "HttpObserver";

    /**
     * 是否显示加载对话框
     */
    private boolean isShowLoading;

    /**
     * 界面
     */
    private BaseCommonActivity activity;

    /**
     * 无参构造方法
     */
    public HttpObserver() {
    }

    /**
     * 构造方法
     *
     * @param activity
     * @param isShowLoading
     */
    public HttpObserver(BaseCommonActivity activity, boolean isShowLoading) {
        this.activity = activity;
        this.isShowLoading = isShowLoading;
    }

    /**
     * 请求成功
     *
     * @param data
     */
    public abstract void onSucceeded(T data);

    /**
     * 请求失败
     *
     * @param data
     * @param e
     * @return
     */
    public boolean onFailed(T data, Throwable e) {

        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (isShowLoading) {
            //显示加载对话框
            LoadingUtils.showLoading(activity);
        }
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        LogUtil.d(TAG, "onNext:" + t);
        //检查是否需要隐藏加载提示框
        checkHideLoading();
        if (isSucceeded(t)) {
            //请求正常
            onSucceeded(t);
        } else {
            //请求出错了
            handlerRequest(t, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        LogUtil.d(TAG, "onError:" + e.getLocalizedMessage());

        //检查是否需要隐藏加载提示框
        checkHideLoading();

        //处理错误
        handlerRequest(null, e);
    }

    /**
     * 网络请求是否成功了
     *
     * @param t
     * @return
     */
    private boolean isSucceeded(T t) {
        if (t instanceof Response) {
            //retrofit里面的响应对象

            //获取响应对象
            Response response = (Response) t;

            //获取响应码
            int code = response.code();

            //判断响应码
            if (code >= 200 && code <= 299) {
                //网络请求正常
                return true;
            }

        } else if (t instanceof BaseResponse) {
            //判断具体的业务请求是否成功
            BaseResponse response = (BaseResponse) t;

            return response.isSuccess();
        }

        return false;
    }

    /**
     * 处理错误网络请求
     *
     * @param data
     * @param error
     */
    private void handlerRequest(T data, Throwable error) {
        if (onFailed(data, error)) {
            //回调了请求失败方法
            //并且该方法返回了true
        } else {
            HttpUtil.handlerRequest(data, error);
        }

    }

    /**
     * 检查是否需要隐藏加载提示框
     */
    private void checkHideLoading() {
        if (isShowLoading) {
            LoadingUtils.hideLoading();
        }
    }
}
