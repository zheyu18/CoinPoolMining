package com.bc.bit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.R;
import com.bc.bit.activity.SearchActivity;
import com.bc.bit.adapter.CurrencyAdapter;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.bc.bit.view.SpaceItemDecoration;
import com.scrb.klinechart.request.base.KBaseObjectBean;
import com.scrb.klinechart.request.base.KRequestManager;
import com.scrb.klinechart.request.base.KRequestSubscribe;
import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.ui.activity.KChartDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  行情中心
 */
public class QuotesFragment extends BaseCommonFragment {
    @BindView(R.id.txt_left_title)
    TextView txtLeftTitle;
    @BindView(R.id.timely_market_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.timely_market_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CurrencyAdapter mAdapter;
    private List<KChartBean> kChartList = new ArrayList<>();
    public static Fragment newInstance() {
        return new QuotesFragment();
    }

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quotes, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        txtLeftTitle.setText(getString(R.string.home_nav_currency));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(35, 30));
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CurrencyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mSwipeRefreshLayout.setRefreshing(true);
        getMarketData();
        initRefreshData();
    }

    private void initRefreshData() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            kChartList.clear();
            getMarketData();
        });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            if (kChartList != null && kChartList.size() > 0) {
                KChartDetailsActivity.startActivity(getMainActivity(),
                        kChartList.get(position).getSymbol(),
                        kChartList.get(position).getTicker(),
                        kChartList.get(position).getExchangeName());
            }
        });
    }

    /**
     * 获取实时行情数据
     */
    private void getMarketData() {
        KRequestManager.getInstance().getCKApi.getMarketData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new KRequestSubscribe<List<KChartBean>>() {
                    @Override
                    protected void onRequestSuccess(List<KChartBean> response) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (response != null && response.size() > 0) {
                            kChartList.addAll(response);
                            mAdapter.setList(kChartList);
                        }
                    }

                    @Override
                    protected void onRequestError(KBaseObjectBean objectBean) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    protected void onNetWorkError() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setEmptyView(getErrorView());
                        showTxt(getString(R.string.error_network_timeout));
                    }
                });

    }


    /**
     * 加载错误占位图
     *
     * @return
     */
    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener((View v) -> {
            mSwipeRefreshLayout.setRefreshing(true);
            kChartList.clear();
            getMarketData();
        });
        return errorView;
    }

    @OnClick(R.id.iv_right)
    public void onViewClicked() {
        if (!checkLogin()) return;
        startActivity(SearchActivity.class);
    }
}
