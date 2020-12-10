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

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.DynamicDetailActivity;
import com.bc.bit.activity.MyDynamicsActivity;
import com.bc.bit.adapter.CommunityRecAdapter;
import com.bc.bit.adapter.StarsRecAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.LoginSuccessEvent;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.bc.bit.util.Constant;
import com.bc.bit.view.CommunityRecDecoration;
import com.bc.bit.view.SpaceItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.collect.Lists;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 社区- 推荐
 */
public class CommunityRecFragment extends BaseCommonFragment implements CommunityRecAdapter.CommunityRecListener {
    @BindView(R.id.community_rec_swipe_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.community_rec_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private StarsRecAdapter starsRecAdapter;
    private CommunityRecAdapter mAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private List<UserBean> userRecList = new ArrayList<>();
    private List<TalkBean> communityRecList = new ArrayList<>();
    private boolean isHasMore;
    private UserBean userBean;
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_rec, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        mRecyclerView.addItemDecoration(new CommunityRecDecoration(35, 35));
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommunityRecAdapter(this);
        mAdapter.addHeaderView(createHeaderView());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        EventBus.getDefault().register(this);
        userBean = MyContext.context().getUser();
        mSwipeRefreshLayout.setRefreshing(true);
        getCommunityRecData(pageNo + "", pageSize + "");
        getRecommandUserList();
        initRefreshLayout();
    }


    @Override
    protected void initListeners() {
        super.initListeners();
        starsRecAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            startActivityExtraData(MyDynamicsActivity.class, userRecList.get(position));
        });
        starsRecAdapter.addChildClickViewIds(R.id.item_star_rec_layout_concern);
        starsRecAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_star_rec_layout_concern) {
                if (!checkLogin()) return;
                goConcerned(position);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            if (communityRecList != null && communityRecList.size() > 0) {
                startActivityExtraData(DynamicDetailActivity.class, communityRecList.get(position));
            }
        });
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore());
    }

    /**
     * 关注用户
     *
     * @param position
     */
    private void goConcerned(int position) {
        Api.getInstance().follow(userBean.getId() + "", userRecList.get(position).getId() + "", true + "").
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.toast_subscribe_success));
                        starsRecAdapter.remove(position);
                        userRecList.remove(position);
                        starsRecAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            communityRecList.clear();
            userRecList.clear();
            pageNo = 1;
            getCommunityRecData(pageNo + "", pageSize + "");
            getRecommandUserList();
        });
    }


    /**
     * 加载 更多
     */
    private void loadMore() {
        if (isHasMore) {
            getCommunityRecData(pageNo + "", pageSize + "");
        } else {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }


    /**
     * 获取推荐用户列表
     */
    private void getRecommandUserList() {
        Api.getInstance().getRecommandUserList(userBean != null ? String.valueOf(userBean.getId()) : "" + "")
                .subscribe(new HttpObserver<ListResponse<UserBean>>() {
                    @Override
                    public void onSucceeded(ListResponse<UserBean> data) {
                        userRecList.addAll(data.getList());
                        starsRecAdapter.setList(userRecList);
                    }
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
                        communityRecList.addAll(data.getData().getList());
                        isHasMore = data.getData().isHasMore();
                        pageNo = pageNo + 1;
                        if (communityRecList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        mAdapter.setList(communityRecList);
                        mAdapter.getLoadMoreModule().loadMoreComplete();
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
     * 创建头部
     *
     * @return
     */
    private View createHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.header_community_rec, (ViewGroup) mRecyclerView.getParent(), false);
        RecyclerView rv_header_rec = view.findViewById(R.id.rv_header_rec);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv_header_rec.setLayoutManager(layoutManager);
        starsRecAdapter = new StarsRecAdapter();
        rv_header_rec.setAdapter(starsRecAdapter);
        return view;
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
            communityRecList.clear();
            userRecList.clear();
            pageNo = 1;
            getCommunityRecData(pageNo + "", pageSize + "");
            getRecommandUserList();
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

    /**
     * 登录成功事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        userBean = MyContext.context().getUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
