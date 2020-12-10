package com.scrb.klinechart.request.base;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.utils.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 创建时间: 2018/6/20
 * 作者: xiaoHou
 * E-mail: 605322850@qq.com
 * Blog: www.xiaohoutongxue.cn
 * 描述: KRequestManager
 **/
public class KRequestManager {

    private static final String TAG = "网络请求：";
    private Retrofit mRetrofit;
    public KApi getCKApi;
    private static class RequestManagerHodler {
        private static KRequestManager instance = new KRequestManager();
    }

    public static KRequestManager getInstance() {
        return RequestManagerHodler.instance;
    }

    //私有化构造方法
    private KRequestManager() {
        mRetrofit = createRetrofit();
        getCKApi = createRequestApi(KApi.class);
    }

    private <T> T createRequestApi(Class<T> apiClass) {
        return mRetrofit.create(apiClass);
    }

    private Retrofit createRetrofit() {
        try {
            return new Retrofit.Builder()
                    .baseUrl("http://api.coindog.com/api/v1/")
                    // RxJava2
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    // 字符串
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .client(initOkhttpClient())

                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 每次请求都会走拦截器
     * <p>
     * 只需要修改Constants.TOKEN就可以
     */
    private OkHttpClient initOkhttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptorHttp = new HttpLoggingInterceptor(message -> {
            LogUtils.e(TAG + message);
        });
        interceptorHttp.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptorHttp);
        builder.connectTimeout(5, TimeUnit.MINUTES).
                readTimeout(5, TimeUnit.MINUTES).
                writeTimeout(5, TimeUnit.MINUTES);

        return builder.build();
    }
}