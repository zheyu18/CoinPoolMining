package com.bc.bit.api;

import com.bc.bit.MyContext;
import com.bc.bit.bean.BaseUrlConfigBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * BaseUrl配置
 */
public class BaseUrlConfig {
    private static final String baseUrl = "http://rap2api.taobao.org/app/mock/272472/qklcl";

    public static void getBaseUrlConfig() {
        OkHttpUtils.post().url(baseUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        BaseUrlConfigBean baseUrlConfigBean = gson.fromJson(response, BaseUrlConfigBean.class);
                        MyContext.context().saveBaseUrl(baseUrlConfigBean);
                    }
                });
    }
}
