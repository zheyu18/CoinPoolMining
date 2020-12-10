package com.scrb.klinechart.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.scrb.klinechart.R;
import com.scrb.klinechart.request.bean.KLineBean;
import com.scrb.klinechart.request.chart.KChartConcart;
import com.scrb.klinechart.request.chart.KChartPersenter;
import com.scrb.lib.DataHelper;
import com.scrb.lib.KLineChartAdapter;
import com.scrb.lib.KLineChartView;
import com.scrb.lib.KLineEntity;
import com.scrb.lib.formatter.DateFormatter;
import com.winks.base_utils.ui.mvp.KBaseMVPFragment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KChartDetailsFragment extends KBaseMVPFragment<KChartPersenter> implements KChartConcart.view<KLineBean> {

    private static final String TYPE_DATA = "type_data";
    private String mTypeName;
    private String mTypeData;
    private KChartPersenter mKChartPersenter;
    private KLineChartView mKLineView;
    private KLineChartAdapter mKLineChartAdapter;
    private List<KLineEntity> mKLineEntity = new ArrayList<>();
    private String timeTag;
    private String D1 ="D1";
    @Override
    protected void lazyLoadShow() {
        if (mKLineEntity.size() !=0){
            return;
        }
        mKLineView = mView.findViewById(R.id.kLineChartView);
        if (mTypeData.equals(D1)) {
            timeTag = "yyyy-MM-dd";
        } else {
            timeTag = "HH:mm";
        }
        mKLineChartAdapter = new KLineChartAdapter();
        mKLineView.setAdapter(mKLineChartAdapter);
        mKLineView.setDateTimeFormatter(new DateFormatter());
        mKLineView.setGridRows(4);
        mKLineView.setGridColumns(4);
        mKLineView.setOnClickListener(v -> mKChartPersenter.getKData(mTypeName, mTypeData));
        mKChartPersenter.getKData(mTypeName, mTypeData);
    }

    @Override
    protected void lazyLoadHide() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_k_chart_details;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeName = getArguments().getString(TYPE_NAME);
        mTypeData = getArguments().getString(TYPE_DATA);
    }

    public static KChartDetailsFragment newInstance(String[] typeName) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_NAME, typeName[0]);
        bundle.putString(TYPE_DATA, typeName[1]);
        KChartDetailsFragment fragment = new KChartDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSuccess(List<KLineBean> data) {
        if (data == null || data.size() == 0) {
            showErrorEmpty();
            return;
        }
        Collections.reverse(data);
        mKLineEntity.clear();
        for (int i = 0; i < data.size(); i++) {
            BigDecimal open = new BigDecimal(data.get(i).getOpen());
            BigDecimal high = new BigDecimal(data.get(i).getHigh());
            BigDecimal low = new BigDecimal(data.get(i).getLow());
            BigDecimal close = new BigDecimal(data.get(i).getClose());
            BigDecimal vol = new BigDecimal(data.get(i).getVol());
            String time = new SimpleDateFormat(timeTag).format(data.get(i).getDateTime());
            mKLineEntity.add(new KLineEntity(time, open.floatValue(), high.floatValue(), low.floatValue(), close.floatValue(), vol.floatValue()));
        }
        if (mTypeData.equals(D1)) {
            DataHelper.calculate(mKLineEntity);
        }
        if (mKLineChartAdapter != null) {
            mKLineChartAdapter.addFooterData(mKLineEntity);
            mKLineChartAdapter.notifyDataSetChanged();
        }
        if (mKLineView != null) {
            mKLineView.startAnimation();
            mKLineView.refreshEnd();
        }
    }

    @Override
    protected KChartPersenter createPresenter() {
        mKChartPersenter = new KChartPersenter();
        return mKChartPersenter;
    }

    @Override
    public void showLoading() {
        if (mKLineView != null) {
            mKLineView.justShowLoading();
        }
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(Object o) {
        showErrorEmpty();
    }

    private void showErrorEmpty() {
        if (mKLineView != null) {
            mKLineView.refreshEnd();
            mKLineView.showNoEmptyView();
        }
    }

    @Override
    public void onNetWorkError() {
        super.onNetWorkError();
        showNetWorkError();
    }

    /**
     * 展示网络错误
     */
    private void showNetWorkError() {
        if (mKLineView != null) {
            mKLineView.refreshEnd();
            mKLineView.showNetErrorView();
        }
    }
}
