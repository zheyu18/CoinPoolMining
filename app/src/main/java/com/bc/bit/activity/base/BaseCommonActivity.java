package com.bc.bit.activity.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bc.bit.MyContext;
import com.bc.bit.activity.LoginActivity;
import com.bc.bit.util.ActivityCollector;
import com.bc.bit.util.Constant;
import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * 通用界面逻辑
 */
public class BaseCommonActivity extends BaseActivity {
    public static final int TIMEOUT = 60 * 1000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivty(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //初始化注解找控件
        //绑定方法框架
        if (isBindView()) {
            bindView();
        }
        initBlackBar();

    }

    protected void initBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    /**
     *  黑色字体
     */
    protected void initBlackBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .init();
    }

    /**
     *  白色字体
     */
    protected void initWhiteBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    /**
     * 绑定View
     */
    protected void bindView() {
        ButterKnife.bind(this);
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
     * 全屏
     */
    protected void fullScreen() {
        //设置界面全屏

        //获取decorView
        View decorView = getWindow().getDecorView();

        //判断版本
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            //11~18版本
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //19及以上版本
            //SYSTEM_UI_FLAG_HIDE_NAVIGATION:隐藏导航栏
            //SYSTEM_UI_FLAG_IMMERSIVE_STICKY:从状态栏下拉会半透明悬浮显示一会儿状态栏和导航栏
            //SYSTEM_UI_FLAG_FULLSCREEN:全屏
            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_FULLSCREEN;

            //设置到控件
            decorView.setSystemUiVisibility(options);
        }
    }

    public String readTxt(EditText editText) {
        return editText.getText().toString().trim().replaceAll(" ","") ;
    }

    public String readTxt(TextView tv) {
        return tv.getText().toString().trim().replaceAll(" ","") ;
    }
    public void showTxt(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 状态栏文字显示白色
     * 内容显示到状态栏下面
     */
    protected void lightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //获取window
            Window window = getWindow();

            //设置状态栏背景颜色为透明
            window.setStatusBarColor(Color.TRANSPARENT);

            //去除半透明效果(如果有)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：让内容显示到状态栏
            //SYSTEM_UI_FLAG_LAYOUT_STABLE：状态栏文字显示白色
            //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR：状态栏文字显示黑色
            window
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 界面显示了
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取页面id
     *
     * @return
     */
    protected String pageId() {
        return null;
    }

    /**
     * 页面暂停了
     */
    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 界面停止了
     */
    @Override
    protected void onStop() {
        super.onStop();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivty(this);
    }
    /**
     * 启动界面
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        //创建Intent
        Intent intent = new Intent(getMainActivity(), clazz);

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
     * 启动界面，可以传递一个Serializable参数
     *
     * @param clazz
     * @param data
     */
    protected void startActivityExtraData(Class<?> clazz, Serializable data) {
        //创建intent
        Intent intent = new Intent(getMainActivity(), clazz);

        //传递数据
        intent.putExtra(Constant.DATA, data);

        //启动界面
        startActivity(intent);
    }

    /**
     * 启动界面并关闭当前界面
     *
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(clazz);

        //关闭当前界面
        finish();
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
     * 获取字符串
     *
     * @param key
     * @return
     */
    protected String extraString(String key) {
        return getIntent().getStringExtra(key);
    }

    /**
     * 获取data对象
     *
     * @return
     */
    protected Serializable extraData() {
        return getIntent().getSerializableExtra(Constant.DATA);
    }

    /**
     * 获取int值
     *
     * @param key
     * @return
     */
    protected int extraInt(String key) {
        return getIntent().getIntExtra(key, -1);
    }

    /**
     * 获取界面方法
     *
     * @return
     */
    protected BaseCommonActivity getMainActivity() {
        return this;
    }


    /**
     * 是否登录
     *
     * @return
     */
    public boolean checkLogin() {
        if (TextUtils.isEmpty(MyContext.context().userId())) {
            LoginActivity.navigation(this);
            return false;
        } else {
            return true;
        }
    }
}
