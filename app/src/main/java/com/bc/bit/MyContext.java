package com.bc.bit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.bc.bit.activity.LoginActivity;
import com.bc.bit.api.BaseUrlConfig;
import com.bc.bit.bean.BaseUrlConfigBean;
import com.bc.bit.bean.UserBean;
import com.bc.bit.util.ActivityCollector;
import com.bc.bit.util.Constant;
import com.bc.bit.util.JsonUtil;
import com.bc.bit.util.NextActivityRequest;
import com.bc.bit.util.SharePreferce;


public class MyContext {
    private static MyContext _instance;
    private static Context mContext;
    private UserBean mUser;
    private BaseUrlConfigBean baseUrlConfig;

    private MyContext(Context context) {
        this.mContext = context;
    }

    public static MyContext create(Context context) {
        if (_instance == null) {
            _instance = new MyContext(context);
            mContext = context;
        }
        return _instance;

    }

    public static MyContext context() {
        return _instance;
    }


    public MyContext loadUser() {
        try {
            String str = SharePreferce.getInstance(mContext).getString("_uj");
            if (!TextUtils.isEmpty(str)) {
                mUser = JsonUtil.toUser(str);
            } else {
                mUser = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public void save(UserBean bean) {
        if (bean != null) {
            mUser = bean;
        }
        try {
            String str = JsonUtil.toJson(bean);
            SharePreferce.getInstance(mContext).setCache("_uj", str);
        } catch (Exception e) {
            //
        }
    }

    public UserBean getUser() {
        return mUser;
    }

    public String userId() {
        return mUser != null ? String.valueOf(mUser.getId()) : "";
    }

    public void goReLogin(Context context) {
        // 清空本地数据
        MyContext.context().clear();
        // UI
        NextActivityRequest.with(context, LoginActivity.class).put(Constant.ID,Constant.LOGOUT).go();
        ActivityCollector.finishAll();

    }

    public void clear() {
        mUser = null;
        SharePreferce.getInstance(mContext).setCache("_uj", "");
    }


    /**
     *  获取当前版本号
     * @return
     */
    public String version() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionName;// + "." + packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
        }

        return "0.0.0";
    }

    public MyContext getBaseUrl() {
        BaseUrlConfig.getBaseUrlConfig();
        return this;
    }
    public void saveBaseUrl(BaseUrlConfigBean bean) {
        if (bean != null) {
            baseUrlConfig = bean;
        }
    }


    public BaseUrlConfigBean getBaseUrlConfig() {
        return baseUrlConfig;
    }
}
