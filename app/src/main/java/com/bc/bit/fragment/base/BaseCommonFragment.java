package com.bc.bit.fragment.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.bc.bit.MyContext;
import com.bc.bit.activity.LoginActivity;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.util.Constant;
import com.gyf.immersionbar.ImmersionBar;
import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 通用公共Fragment
 */
public abstract class BaseCommonFragment extends BaseFragment {
    public static final int TIMEOUT = 60 * 1000;
    /** 状态栏沉浸 */
    private ImmersionBar mImmersionBar;

    @Override
    protected void initViews() {
        super.initViews();

        //初始化注解找控件
        //绑定方法框架
        if (isBindView()) {
            bindView();
        }
    }

    /**
     * 绑定View
     */
    protected void bindView() {
        ButterKnife.bind(this, getView());
    }

    /**
     * 是否绑定View
     *
     * @return
     */
    protected boolean isBindView() {
        return true;
    }

    @Override
    protected void initDatum() {
        super.initDatum();
    }

    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //启动界面
        startActivity(intent);
    }


    protected void startActivityExtraData(Class<?> clazz, Serializable data) {
        //创建intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //传递数据
        intent.putExtra(Constant.DATA, data);

        //启动界面
        startActivity(intent);
    }


    /**
     * 启动界面，可以传递一个字符串参数
     *
     * @param clazz
     * @param id
     */
    protected void startActivityExtraId(Class<?> clazz, String id) {
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //传递数据
        if (!TextUtils.isEmpty(id)) {
            //不为空才传递
            intent.putExtra(Constant.ID, id);
        }

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);

        //关闭当前界面
        getMainActivity().finish();
    }

    /**
     * 获取界面方法
     * @return
     */
    protected BaseCommonActivity getMainActivity() {
        return (BaseCommonActivity) getActivity();
    }

    /**
     * 获取字符串类型Id
     *
     * @return
     */
    protected String extraId() {
        return extraString(Constant.ID);
    }

    /**
     * 获取int值
     *
     * @param key
     * @return
     */
    protected int extraInt(String key) {
        return getArguments().getInt(key, -1);
    }


    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    protected String extraString(String key) {
        return getArguments().getString(key);
    }


    /**
     * 获取data对象
     *
     * @return
     */
    protected Serializable extraData() {
        return getArguments().getSerializable(Constant.DATA);
    }

    public String readTxt(EditText editText) {
        return editText.getText().toString().trim().replaceAll(" ","") ;
    }


    public void showTxt(String txt) {
        Toast.makeText(getMainActivity(), txt, Toast.LENGTH_SHORT).show();
    }


    /**
     *  检查是否有网络
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean checkLogin() {
        if (TextUtils.isEmpty(MyContext.context().userId())) {
            LoginActivity.navigation(getMainActivity());
            return false;
        } else {
            return true;
        }
    }

}
