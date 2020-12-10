package com.scrb.klinechart.request.flash;


import com.scrb.klinechart.request.base.KRequestManager;

import io.reactivex.Observable;

public class KFlashMode implements KFlashConcart.mode {
    @Override
    public Observable<String> getFlashData() {
        return KRequestManager.getInstance().getCKApi.getFlashHtmlData();
    }
}
