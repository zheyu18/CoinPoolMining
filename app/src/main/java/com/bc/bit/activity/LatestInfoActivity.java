package com.bc.bit.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.LatestInfoAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.ExpressNewsListBean;
import com.bc.bit.util.NextActivityRequest;
import com.bc.bit.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实时快讯
 */
public class LatestInfoActivity extends BaseTitleActivity {
    @BindView(R.id.latest_info_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.latest_info_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ExpressNewsListBean.ListBean.LivesBean> expressNewsList = new ArrayList<>();
    private LatestInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_info);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.timely_info_title));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        mAdapter = new LatestInfoAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(32, 32));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mSwipeRefreshLayout.setRefreshing(true);
        getExpressNewsList();
        initRefreshLayout();
    }

    /**
     * 刷新数据
     */
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            expressNewsList.clear();
            getExpressNewsList();
        });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            mAdapter.getLoadMoreModule().loadMoreEnd();
            showTxt(getString(R.string.toast_loading_finish));
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            if (expressNewsList != null && expressNewsList.size() > 0) {
                NextActivityRequest.with(getMainActivity(), InfoDetailActivity.class)
                        .put("content", expressNewsList.get(position).getContent())
                        .go();
            }
        });
    }

    /**
     * 获取快讯数据
     */
    private void getExpressNewsList() {
        Api.getInstance().getExpressNewsList()
                .subscribe(new ObserverAdapter<ExpressNewsListBean>() {
                    @Override
                    public void onNext(ExpressNewsListBean expressNewsListBean) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.getLoadMoreModule().loadMoreComplete();
                        try {
                            List<ExpressNewsListBean.ListBean.LivesBean> lives = expressNewsListBean.getList().get(0).getLives();
                            expressNewsList.addAll(lives);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mAdapter.setList(expressNewsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.setEmptyView(getErrorView());
                        mSwipeRefreshLayout.setRefreshing(false);
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
            expressNewsList.clear();
            getExpressNewsList();
        });
        return errorView;
    }
}
