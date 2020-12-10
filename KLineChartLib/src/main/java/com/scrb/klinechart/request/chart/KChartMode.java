package com.scrb.klinechart.request.chart;

import com.scrb.klinechart.request.base.KRequestManager;
import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.request.bean.KLineBean;

import java.util.List;

import io.reactivex.Observable;

public class KChartMode implements KChartConcart.mode {
    @Override
    public Observable<List<KChartBean>> getMarketData() {
        return KRequestManager.getInstance().getCKApi.getMarketData();
    }

    @Override
    public Observable<List<KLineBean>> getKData(String ticker,String period ) {
        return KRequestManager.getInstance().getCKApi.getKData(ticker,period);
    }
}
