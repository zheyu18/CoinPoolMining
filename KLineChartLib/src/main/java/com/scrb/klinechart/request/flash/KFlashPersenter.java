package com.scrb.klinechart.request.flash;

import com.scrb.klinechart.request.base.KBaseObjectBean;
import com.scrb.klinechart.request.base.KRequestSubscribe;
import com.scrb.klinechart.request.bean.KFlashDataBean;
import com.winks.base_utils.request.manager.RxLifeCycleUtils;
import com.winks.base_utils.request.manager.RxThreadUtil;
import com.winks.base_utils.ui.mvp.KBasePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class KFlashPersenter extends KBasePresenter<KFlashConcart.view> implements KFlashConcart.persenter {
    private static final String TAG = "FlashPersenter____";
    private KFlashMode mode;

    public KFlashPersenter() {
        mode = new KFlashMode();
    }

    @Override
    public void getFlashData() {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        mode.getFlashData()
                .compose(RxThreadUtil.rxObservableSchedulerHelper())
                .compose(RxLifeCycleUtils.bindToLifecycle(mView))
                .subscribe(new KRequestSubscribe<String>() {
                    @Override
                    protected void onRequestSuccess(String response) {
                        mView.requestSuccess(parseFlashData(response));
                    }

                    @Override
                    protected void onRequestError(KBaseObjectBean objectBean) {
                        mView.hideLoading();
                        mView.requestSuccess(null);

                    }

                    @Override
                    protected void onNetWorkError() {
                        mView.hideLoading();
                    }
                });
    }

    /**
     * 解析数据
     *
     * @param response
     * @return
     */
    private List<KFlashDataBean> parseFlashData(String response) {
        if (response == null && response.equals("")) {
            return null;
        }
        Document document = Jsoup.parse(response);
        if (document == null) {
            return null;
        }
        Elements mContextListBox = document.getElementsByClass("flash-list-item-wrapper");
        if (mContextListBox == null) {
            return null;
        }
        List<KFlashDataBean> list = new ArrayList<>();
        for (Element element : mContextListBox) {
            KFlashDataBean flashDataBean = new KFlashDataBean();
            Elements mItemIconsElements = element.getElementsByClass("item-title");
            Elements mTitleElements = element.getElementsByClass("item-icons");
            Elements mImgElements = element.getElementsByClass("little-img");
            Elements mConetxtElements = element.select("h3");
            if (mImgElements != null) {
                String img = mImgElements.attr("src");
                flashDataBean.showImg = img == null|| img.equals("")?false:true;
                flashDataBean.imgUrl = img;
            }
            if (mConetxtElements != null) {
                flashDataBean.context = mConetxtElements.text();
            }
            if (mTitleElements != null) {
                for (Element item : mTitleElements) {
                    flashDataBean.time = item.getElementsByClass("time-left") == null ? null : item.getElementsByClass("time-left").text();
                }
            }
            if (mItemIconsElements != null) {
                flashDataBean.articleUrl = mItemIconsElements.attr("href");
                flashDataBean.title = mItemIconsElements.text();
            }
            list.add(flashDataBean);
        }
        return list;
    }
}
