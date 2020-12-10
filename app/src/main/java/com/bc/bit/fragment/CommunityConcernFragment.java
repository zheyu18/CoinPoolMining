package com.bc.bit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.R;
import com.bc.bit.activity.DynamicDetailActivity;
import com.bc.bit.adapter.CommunityConcernAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.bc.bit.util.Constant;
import com.bc.bit.view.CommunityRecDecoration;
import com.bc.bit.view.SpaceItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.collect.Lists;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 社区 - 关注
 */
public class CommunityConcernFragment extends BaseCommonFragment implements CommunityConcernAdapter.CommunityConcernListener {
    @BindView(R.id.community_concern_swipe_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.community_concern_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CommunityConcernAdapter mAdapter;
    private List<TalkBean> communityConcernList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 30;

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_concern, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(35, 35));
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommunityConcernAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mSwipeRefreshLayout.setRefreshing(true);
        getCommunityRecData(pageNo + "", pageSize + "");
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
            if (communityConcernList != null && communityConcernList.size() > 0) {
                startActivityExtraData(DynamicDetailActivity.class, communityConcernList.get(position));
            }
        });

    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            communityConcernList.clear();
            getCommunityRecData(pageNo + "", pageSize + "");
        });
    }

    /**
     * 获取发现推荐数据
     *
     * @param pageno
     * @param pagesize
     */
    private void getCommunityRecData(String pageno, String pagesize) {
        Api.getInstance().getTalkListByProject(Constant.PROJECT_NAME, pageno, pagesize).
                subscribe(new HttpObserver<DataResponse<ListMoreResponse<TalkBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<TalkBean>> data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        communityConcernList.addAll(data.getData().getList());
                        if (communityConcernList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        Collections.shuffle(communityConcernList);
                        mAdapter.setList(communityConcernList);
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

    /**
     * 加载错误占位图
     *
     * @return
     */
    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener((View v) -> {
            mSwipeRefreshLayout.setRefreshing(true);
            communityConcernList.clear();
            getCommunityRecData(pageNo + "", pageSize + "");
        });
        return errorView;
    }


    @Override
    public void onImageClick(RecyclerView rv, List<String> images, int index) {
        ArrayList<String> imagesUris = Lists.newArrayList(images);
        //静态的方法要通过INSTANCE字段使用
        PhotoViewer.INSTANCE
                //设置图片数据
                .setData(imagesUris)
                //设置当前位置
                .setCurrentPage(index)
                //设置图片控件容器
                //他需要容器的目的是
                //显示缩放动画
                .setImgContainer(rv)
                //设置图片加载回调
                .setShowImageViewInterface((imageView, url) -> {
                    //使用Glide显示图片
                    Glide.with(getMainActivity()).load(url).apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder)).into(imageView);
                })
                //启动界面
                .start(this);
    }

}
