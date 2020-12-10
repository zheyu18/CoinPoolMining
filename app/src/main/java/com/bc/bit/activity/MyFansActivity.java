package com.bc.bit.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.MyFansAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.UnsubscribeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *  我的粉丝
 */
public class MyFansActivity extends BaseTitleActivity {
    @BindView(R.id.rv_fans)
    RecyclerView mRecyclerView;
    @BindView(R.id.fans_swipe_refresh)
    SwipeRefreshLayout fansSwipeRefresh;
    private MyFansAdapter mAdapter;
    private UserBean userBean;
    private List<UserBean> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.my_fans));
        fansSwipeRefresh.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getMainActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.concern_divider_inset));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyFansAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
        fansSwipeRefresh.setRefreshing(true);
        getRecommandUserList();
        initRefreshLayout();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (userList != null && userList.size() > 0) {
                startActivityExtraData(MyDynamicsActivity.class, userList.get(position));
            }
        });
        mAdapter.addChildClickViewIds(R.id.item_fans_add_concerned);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_fans_add_concerned) {
                goConcerned(position);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        fansSwipeRefresh.setOnRefreshListener(() -> {
            userList.clear();
            getRecommandUserList();
        });
    }

    /**
     * 获取推荐用户列表
     */
    private void getRecommandUserList() {
        Api.getInstance().getRecommandUserList(userBean.getId() + "")
                .subscribe(new HttpObserver<ListResponse<UserBean>>() {
                    @Override
                    public void onSucceeded(ListResponse<UserBean> data) {
                        fansSwipeRefresh.setRefreshing(false);
                        userList.addAll(data.getList());
                        if (userList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        mAdapter.setList(userList);
                    }

                    @Override
                    public boolean onFailed(ListResponse<UserBean> data, Throwable e) {
                        fansSwipeRefresh.setRefreshing(false);
                        mAdapter.setEmptyView(getErrorView());
                        return super.onFailed(data, e);
                    }
                });
    }

    /**
     * 关注用户
     *
     * @param position
     */
    private void goConcerned(int position) {
        Api.getInstance().follow(userBean.getId() + "", userList.get(position).getId() + "", true + "").
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.toast_subscribe_success));
                        mAdapter.remove(position);
                        EventBus.getDefault().post(new UnsubscribeEvent());
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
            fansSwipeRefresh.setRefreshing(true);
            userList.clear();
            getRecommandUserList();
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
