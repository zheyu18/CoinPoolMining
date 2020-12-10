package com.scrb.klinechart.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.scrb.klinechart.R;
import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.request.chart.KChartConcart;
import com.scrb.klinechart.request.chart.KChartPersenter;
import com.scrb.klinechart.ui.activity.KChartActivity;
import com.scrb.klinechart.ui.activity.KChartDetailsActivity;
import com.scrb.klinechart.ui.adapter.KChartAdapter;
import com.winks.base_utils.ui.mvp.KBaseMVPFragment;

import java.util.ArrayList;
import java.util.List;

public class KChartFragment extends KBaseMVPFragment<KChartPersenter> implements KChartConcart.view<KChartBean> {
    private KChartPersenter mKChartPersenter;
    private List<KChartBean> mKChartBeanList = new ArrayList<>();
    private KChartAdapter mKChartAdapter;
    private RecyclerView mRecyclerView;
    private FrameLayout mRecyclerViewBox;
    private View mLoadingView;
    private View mEmptyView;
    private TextView mEmptyTextView;
    private Button mEmptyRefreshBtn;
    private ImageView mEmptyImageView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewGroup.LayoutParams mLadingLayoutParams;
    private ViewGroup.LayoutParams mEmptyLayoutParams;

//    /**
//     * 解决普通Fragment 切换和 BaseFragment 懒加载冲突
//     * @param inflater
//     * @param container
//     * @param savedInstanceState
//     * @return
//     */
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        isVisible = true;
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        isVisible = !hidden;
//        if (isPrepared) {
//            if (isVisible) {
//                lazyLoadShow();
//            } else {
//                lazyLoadHide();
//            }
//
//        }
//    }
    @Override
    protected void lazyLoadShow() {
        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerViewBox = mView.findViewById(R.id.k_chart_recycler_view_box);
        mSwipeRefreshLayout = mView.findViewById(R.id.ik_chart_smart_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mKChartPersenter!=null) {
                mKChartPersenter.getMarketData();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        mKChartAdapter = new KChartAdapter( mKChartBeanList,mContext);
        mKChartAdapter.setOnClickListener(data -> KChartDetailsActivity.startActivity(getActivity(),data.getSymbol(),data.getTicker(), data.getExchangeName()));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mKChartAdapter);
        if (mKChartPersenter!=null) {
            mKChartPersenter.getMarketData();
        }
    }

    @Override
    protected void lazyLoadHide() {

    }

    /**
     * 加载View
     */
    private void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.dialog_view, null);
            mLadingLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mRecyclerViewBox.addView(mLoadingView, mLadingLayoutParams);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_k_chart;
    }


    @Override
    protected KChartPersenter createPresenter() {
        mKChartPersenter = new KChartPersenter();
        return mKChartPersenter;
    }

    @Override
    public void onSuccess(List<KChartBean> data) {
        if (data == null) {
            showEmptyView(true);
            return;
        }
        mKChartBeanList.clear();
        mKChartBeanList.addAll(data);
        if (mKChartAdapter != null) {
            mKChartAdapter.setData(mKChartBeanList);
        }
        if (mRecyclerViewBox != null) {
            if (mRecyclerView != null) {
                mRecyclerViewBox.addView(mRecyclerView);
            }
        }
    }

    /**
     * 展示占位图
     *
     * @param showEmptyView 是否展示占位图 true 占位图 false  网络错误
     */
    private void showEmptyView(boolean showEmptyView) {
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.k_line_chart_empty, null);
            mEmptyTextView = mEmptyView.findViewById(R.id.k_line_chart_empty_text);
            mEmptyRefreshBtn = mEmptyView.findViewById(R.id.k_line_chart_empty_refresh_btn);
            mEmptyImageView = mEmptyView.findViewById(R.id.k_line_chart_empty_img);
            mEmptyLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mEmptyImageView.setImageDrawable(getResources().getDrawable(showEmptyView == true ? R.drawable.ic_empty : R.drawable.ic_net_work_error));
        mEmptyTextView.setText(getResources().getString(showEmptyView == true ? R.string.no_data : R.string.net_work_error));
        mEmptyRefreshBtn.setVisibility(showEmptyView == true ? View.GONE : View.VISIBLE);
        mEmptyRefreshBtn.setOnClickListener(v -> {
            if (mPresenter != null) {
                mKChartPersenter.getMarketData();
            }
        });
        if (mRecyclerViewBox != null) {
            mRecyclerViewBox.addView(mEmptyView, mEmptyLayoutParams);
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showLoading() {
        if (mKChartBeanList.size() != 0) {
            return;
        }
        removeAllView();
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        removeAllView();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void removeAllView() {
        int count = mRecyclerViewBox.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mRecyclerViewBox.getChildAt(count) != mRecyclerView) {
                mRecyclerViewBox.removeView(mRecyclerViewBox.getChildAt(i));
            }
        }
        mKChartBeanList.clear();
    }

    @Override
    public void onError(Object o) {
        showEmptyView(true);
    }

    @Override
    public void onNetWorkError() {
        showEmptyView(false);
    }
}
