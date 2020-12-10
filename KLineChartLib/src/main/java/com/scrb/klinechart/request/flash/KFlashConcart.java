package com.scrb.klinechart.request.flash;

import com.scrb.klinechart.request.bean.KFlashDataBean;
import com.winks.base_utils.ui.mvp.KBaseView;

import java.util.List;

import io.reactivex.Observable;

public interface KFlashConcart {
    interface mode{
        Observable<String> getFlashData();
    }
    interface view extends KBaseView {
        void requestSuccess(List<KFlashDataBean> dataBean);
    }
    interface persenter{
        void getFlashData();
    }
}
