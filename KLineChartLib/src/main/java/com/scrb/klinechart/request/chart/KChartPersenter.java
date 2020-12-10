package com.scrb.klinechart.request.chart;

import com.scrb.klinechart.request.base.KBaseObjectBean;
import com.scrb.klinechart.request.base.KRequestSubscribe;
import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.request.bean.KLineBean;
import com.winks.base_utils.request.manager.RxLifeCycleUtils;
import com.winks.base_utils.request.manager.RxThreadUtil;
import com.winks.base_utils.ui.mvp.KBasePresenter;

import java.util.List;

public class KChartPersenter extends KBasePresenter<KChartConcart.view> implements KChartConcart.persenter {
    public KChartMode mode;

    public KChartPersenter() {
        mode = new KChartMode();
    }

    @Override
    public void getMarketData() {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        mode.getMarketData()
                .compose(RxLifeCycleUtils.bindToLifecycle(mView))
                .compose(RxThreadUtil.rxObservableSchedulerHelper())
                .subscribe(new KRequestSubscribe<List<KChartBean>>() {
                    @Override
                    protected void onRequestSuccess(List<KChartBean> response) {
                        mView.hideLoading();
                        mView.onSuccess(response);
                    }

                    @Override
                    protected void onRequestError(KBaseObjectBean objectBean) {
                        mView.hideLoading();
                        mView.onError(objectBean);
                    }

                    @Override
                    protected void onNetWorkError() {
                        mView.hideLoading();
                        mView.onNetWorkError();
                    }
                });
    }

    @Override
    public void getKData(String ticker,String period ) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        mode.getKData(ticker,period)
                .compose(RxLifeCycleUtils.bindToLifecycle(mView))
                .compose(RxThreadUtil.rxObservableSchedulerHelper())
                .subscribe(new KRequestSubscribe<List<KLineBean>>() {
                    @Override
                    protected void onRequestSuccess(List<KLineBean> response) {
                        mView.onSuccess(response);
                    }

                    @Override
                    protected void onRequestError(KBaseObjectBean objectBean) {
                        mView.hideLoading();
                        mView.onError(objectBean);
                    }

                    @Override
                    protected void onNetWorkError() {
                        mView.hideLoading();
                       mView.onNetWorkError();
                    }
                });
    }
}
