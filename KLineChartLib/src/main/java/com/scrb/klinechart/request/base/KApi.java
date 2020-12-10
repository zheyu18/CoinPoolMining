package com.scrb.klinechart.request.base;

import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.request.bean.KLineBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface KApi {
    /**
     * 获取行情数据
     * @return
     */
    @GET("ticks/Binance?unit=cny")
    Observable<List<KChartBean>> getMarketData();

    /**
     * 获取K线数据
     * @param ticker  交易所与交易对信息
     * @param period    K线周期，支持的值：M1/M5/M15/M30/H1/H4/D1/W1/MONTH ,
     *                  分别对应：1分钟、5分钟、15分钟、30分钟、1小时、4小时、1天、1周、1月
     * @return
     */
    @GET("klines/{ticker}/{period}")
    Observable<List<KLineBean>> getKData(@Path("ticker") String ticker, @Path("period") String period);

    /**
     * 获取快讯数据
     * @return
     */
    @GET("https://news.huoxing24.com/flash")
    Observable<String> getFlashHtmlData();
}
