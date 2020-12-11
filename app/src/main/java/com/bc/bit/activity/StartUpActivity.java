package com.bc.bit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.bc.bit.MainActivity;
import com.bc.bit.MyApplication;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.RequestDataBean;
import com.bc.bit.util.AESUtil;
import com.bc.bit.util.Constant;
import com.bc.bit.util.NextActivityRequest;
import com.bc.bit.util.SharePreferce;
import com.bc.bit.view.AgreementPouupDialog;
import com.bc.bit.view.OrdinaryPopupDialog;
import com.google.gson.Gson;

/**
 *  启动页面
 */
public class StartUpActivity extends BaseCommonActivity {
    private static final String TAG = "StartUpActivity";
    private Handler mHandler = new Handler();
    private boolean protocol;
    private AgreementPouupDialog mAgreementPouupDialog;
    private OrdinaryPopupDialog mOrdinaryPopupDialog;
    private String mWebUrlSpare;// 缓存地址
    private String mPool; // 备用地址
    private RequestDataBean requestDataBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initWhiteBar();
        protocol = SharePreferce.getInstance(StartUpActivity.this).getBoolean(Constant.PROTOCOL);
        mWebUrlSpare = SharePreferce.getInstance(StartUpActivity.this).getString(Constant.TRIPARTITE_URL_SPARE);
        mPool = SharePreferce.getInstance(StartUpActivity.this).getString(Constant.TRIPARTITE_URL_SPARE_POOL);
        mHandler.postDelayed(() -> {
//            showAgreement();
            goMain();
        }, 1000);
    }

    private void showAgreement() {
        if (protocol) {
            sendTalk();
        } else {
            mAgreementPouupDialog = new AgreementPouupDialog(getMainActivity(), R.style.RequestCodeDialogStyle);
            mAgreementPouupDialog.setOnItemClickAgreementDialog(new AgreementPouupDialog.AgreementDialogCallback() {
                @Override
                public void logoutCallback() {
                    showDisagreementDialog();
                }
                @Override
                public void confirmCallback() {
                    SharePreferce.getInstance(StartUpActivity.this).setCache(Constant.PROTOCOL, true);
                    sendTalk();
                }
            });
            mAgreementPouupDialog.show();
            mAgreementPouupDialog.setOnKeyListener((arg0, keyCode, event) -> {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            });

        }
    }


    private void showDisagreementDialog() {
        mOrdinaryPopupDialog = new OrdinaryPopupDialog(getMainActivity(), R.style.RequestCodeDialogStyle);
        mOrdinaryPopupDialog.show();
        mOrdinaryPopupDialog.setOnItemClickOrdinaryPopupDialog(() -> {
            if (mAgreementPouupDialog != null) {
                mAgreementPouupDialog.show();
            }
        });
        mOrdinaryPopupDialog.setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * 版本验证
     */
    private void sendTalk() {
        if (TextUtils.isEmpty(mWebUrlSpare)){
            Api.getInstance().sendTalk(MyApplication.getChannelValue(), Constant.SD)
                    .subscribe(new ObserverAdapter<String>() {
                        @Override
                        public void onNext(String data) {
                            parseRequestData(data);
                        }
                        @Override
                        public void onError(Throwable e) {
                            showTxt(getString(R.string.error_network_unknown_host));
                            finish();
                        }
                    });
        }else {
            goTripartiteUrl();
        }

    }

    /**
     * 解析请求数据
     *
     * @param data
     */
    private void parseRequestData(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        try {
            String dataStr = AESUtil.decrypt(data);
            requestDataBean = new Gson().fromJson(dataStr, RequestDataBean.class);
            if (requestDataBean != null) {
                if (requestDataBean.getStatus() == 1) {
                    mWebUrlSpare =  requestDataBean.getUrl();
                    mPool = requestDataBean.getDPool().toString();
                    SharePreferce.getInstance(StartUpActivity.this).setCache(Constant.TRIPARTITE_URL_SPARE, mWebUrlSpare);
                    SharePreferce.getInstance(StartUpActivity.this).setCache(Constant.TRIPARTITE_URL_SPARE_POOL, mPool);
                    goTripartiteUrl();
                } else {
                    goMain();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转第三方网页
     */
    private void goTripartiteUrl() {
        NextActivityRequest.with(getMainActivity(), AgentWebActivity.class)
                .put("url", mWebUrlSpare)
                .put("dPool",mPool)
                .go();
        finish();
    }

    /**
     *  进入首页
     */
    private void goMain() {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
