package com.bc.bit.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.adapter.MyDynamicsAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.bean.UserBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人动态列表
 */
public class MyDynamicsActivity extends BaseCommonActivity implements MyDynamicsAdapter.MyDynamicsListener {
    @BindView(R.id.image_header)
    RoundedImageView imageHeader;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_concern_count)
    TextView tvConcernCount;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_dynamics_count)
    TextView tvDynamicsCount;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.my_dynamic_rv)
    RecyclerView myDynamicRv;
    @BindView(R.id.bnt_cancel)
    TextView bntCancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.my_dynamic_swipe_refresh)
    SwipeRefreshLayout myDynamicSwipeRefresh;
    private UserBean userBean;
    private List<TalkBean> talkBeanList = new ArrayList<>();
    private MyDynamicsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamics);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initWhiteBar();
        myDynamicSwipeRefresh.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myDynamicRv.setLayoutManager(layoutManager);
        mAdapter = new MyDynamicsAdapter(this);
        mAdapter.getLoadMoreModule();
        myDynamicRv.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = (UserBean) extraData();
        myDynamicSwipeRefresh.setRefreshing(true);
        getUserInforByUserId(userBean.getId() + "");
        getRecommandUserList();
        refreshListdata();

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            startActivityExtraData(DynamicDetailActivity.class, talkBeanList.get(position));
        });

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                myDynamicSwipeRefresh.setEnabled(true);
            } else {
                myDynamicSwipeRefresh.setEnabled(false);
            }
        });
    }


    /**
     * 根据ID获取用户信息
     *
     * @param userId
     */
    private void getUserInforByUserId(String userId) {
        Api.getInstance().queryUser(userId)
                .subscribe(new HttpObserver<DetailResponse<UserBean>>() {
                    @Override
                    public void onSucceeded(DetailResponse<UserBean> data) {
                        if (data.getData() != null) {
                            userBean = data.getData();
                            showInfo();
                            getAllTalkByUserId(userBean.getId() + "");
                        }
                    }
                });
    }


    /**
     * 显示用户信息
     */
    private void showInfo() {
        if (userBean != null) {
            Glide.with(getMainActivity()).load(userBean.getHead())
                    .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                    .error(R.drawable.pic_me_placeholder).into(imageHeader);
            tvNickname.setText(userBean.getNickName());
            tvTitle.setText(userBean.getNickName());
            if (TextUtils.isEmpty(userBean.getSignature())) {
                tvSignature.setText(getString(R.string.no_signature));
            } else {
                tvSignature.setText(userBean.getSignature());
            }
            tvConcernCount.setText(userBean.getFollowCount() + "");
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
                        if (data.getList() != null) {
                            tvFansCount.setText(data.getList().size() + "");
                        }
                    }

                    @Override
                    public boolean onFailed(ListResponse<UserBean> data, Throwable e) {
                        return super.onFailed(data, e);
                    }
                });
    }

    /**
     * 根据用户id获取说说列表
     */
    private void getAllTalkByUserId(String userId) {
        Api.getInstance().getAllTalkByUserId(userId != null ? userId : "")
                .subscribe(new ObserverAdapter<String>() {
                    @Override
                    public void onNext(String data) {
                        myDynamicSwipeRefresh.setRefreshing(false);
                        parseRequestData(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.setEmptyView(getErrorView());
                        myDynamicSwipeRefresh.setRefreshing(false);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    private void refreshListdata() {
        myDynamicSwipeRefresh.setOnRefreshListener(() -> {
            talkBeanList.clear();
            getAllTalkByUserId(userBean.getId() + "");
        });
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mAdapter.getLoadMoreModule().loadMoreEnd();
    }

    /**
     * 解析请求数据
     *
     * @param data
     */
    private void parseRequestData(String data) {
        if (TextUtils.isEmpty(data) || data.equals("[]")) {
            mAdapter.setEmptyView(getEmptyView());
            return;
        }
        List<TalkBean> talkList = new Gson().fromJson(data, new TypeToken<List<TalkBean>>() {
        }.getType());
        for (TalkBean talkBean : talkList) {
            talkBean.setUser(userBean);
        }
        talkBeanList.addAll(talkList);
        tvDynamicsCount.setText(talkBeanList.size() + "");
        mAdapter.setList(talkBeanList);
    }


    /**
     * 数据为空
     *
     * @return
     */
    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_match_view, myDynamicRv, false);
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
        View errorView = getLayoutInflater().inflate(R.layout.error_view, myDynamicRv, false);
        errorView.setOnClickListener((View v) -> {
            myDynamicSwipeRefresh.setRefreshing(true);
            talkBeanList.clear();
            getAllTalkByUserId(userBean.getId() + "");
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
                .setShowImageViewInterface((ImageView imageView, String url) -> {
                    //使用Glide显示图片
                    Glide.with(getMainActivity()).load(url).apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder)).into(imageView);
                })
                //启动界面
                .start(this);

    }

    @OnClick({R.id.iv_back, R.id.bnt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.bnt_cancel:
                finish();
                break;
        }
    }
}
