package com.bc.bit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.IndustryStormAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 *  行业风云
 */
public class IndustryStormActivity extends BaseTitleActivity {
    @BindView(R.id.industry_storm_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.industry_storm_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private IndustryStormAdapter mAdapter;
    private List<TalkBean> industryStormBeanList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_storm);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.industry_situation));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new IndustryStormAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mSwipeRefreshLayout.setRefreshing(true);
        getNewsList(pageNo + "", pageSize + "");
        initRefreshLayout();
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
            if (industryStormBeanList != null && industryStormBeanList.size() > 0) {
                startActivityExtraData(DynamicDetailActivity.class, industryStormBeanList.get(position + 1));
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            industryStormBeanList.clear();
            getNewsList(pageNo + "", pageSize + "");
        });
    }

    /**
     * 获取宏观新闻数据
     *
     * @param pageno
     * @param pagesize
     */
    private void getNewsList(String pageno, String pagesize) {
        Api.getInstance().getTalkListByProject(Constant.PROJECT_NAME, pageno, pagesize).
                subscribe(new HttpObserver<DataResponse<ListMoreResponse<TalkBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<TalkBean>> data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (data.getData().getList() == null) return;
                        industryStormBeanList.addAll(data.getData().getList());
                        if (industryStormBeanList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        mAdapter.setList(industryStormBeanList);
                    }

                    @Override
                    public boolean onFailed(DataResponse<ListMoreResponse<TalkBean>> data, Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setEmptyView(getErrorView());
                        return super.onFailed(data, e);
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
            industryStormBeanList.clear();
            getNewsList(pageNo + "", pageSize + "");
        });
        return errorView;
    }

    /**
     * 数据为空
     *
     * @return
     */
    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_match_view, mRecyclerView, false);
        TextView tv_content = emptyView.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.empty_data));
        return emptyView;
    }
}
