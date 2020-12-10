package com.scrb.klinechart.request.chart;

import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.request.bean.KLineBean;
import com.winks.base_utils.ui.mvp.KBaseView;

import java.util.List;

import io.reactivex.Observable;

public interface KChartConcart {
    interface mode {
        Observable<List<KChartBean>> getMarketData();

        /**
         * 获取K线数据
         * @param ticker  : 交易所与交易对信息
         * @return
         */
        Observable<List<KLineBean>> getKData(String ticker,String period );
    }

    interface view<T> extends KBaseView {
        void onSuccess(List<T> data);
    }

    interface persenter {
        void getMarketData();
        void getKData(String ticker,String period );
    }
}
