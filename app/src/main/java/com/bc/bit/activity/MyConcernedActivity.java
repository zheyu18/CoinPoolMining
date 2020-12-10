package com.bc.bit.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.MyConcernAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.UnsubscribeEvent;
import com.bc.bit.util.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 *  我的关注
 */
public class MyConcernedActivity extends BaseTitleActivity implements View.OnClickListener {
    @BindView(R.id.concern_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_concern_swipe_refresh)
    SwipeRefreshLayout myConcernSwipeRefresh;
    private MyConcernAdapter mAdapter;
    private UserBean userBean;
    private List<UserBean> userList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 10;
    private Dialog mDialogBottom;
    private View mInflate;
    private TextView mBtnOk, mBtnCancel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_concerned);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.my_concerned));
        myConcernSwipeRefresh.setColorSchemeResources(R.color.col_theme);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getMainActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.concern_divider_inset));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyConcernAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        myConcernSwipeRefresh.setRefreshing(true);
        userBean = MyContext.context().getUser();
        getUserFollowList(pageNo + "", pageSize + "");
        initRefreshLayout();
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        myConcernSwipeRefresh.setOnRefreshListener(() -> {
            userList.clear();
            getUserFollowList(pageNo + "", pageSize + "");
        });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (userList != null && userList.size() > 0) {
                startActivityExtraData(MyDynamicsActivity.class, userList.get(position));
            }
        });
        mAdapter.addChildClickViewIds(R.id.item_cancel_concerned);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_cancel_concerned) {
                this.position = position;
                showDialog();
            }
        });
    }

    /**
     * 获取已关注用户列表
     */
    private void getUserFollowList(String pageno, String pagesize) {
        Api.getInstance().getUserFollowList(userBean.getId() + "", Constant.TYPE_ONE, pageno, pagesize).
                subscribe(new HttpObserver<DataResponse<ListMoreResponse<UserBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<UserBean>> data) {
                        myConcernSwipeRefresh.setRefreshing(false);
                        if (data.getData() == null) return;
                        userList.addAll(data.getData().getList());
                        if (userList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        mAdapter.setList(userList);
                    }

                    @Override
                    public boolean onFailed(DataResponse<ListMoreResponse<UserBean>> data, Throwable e) {
                        myConcernSwipeRefresh.setRefreshing(false);
                        mAdapter.setEmptyView(getErrorView());
                        return super.onFailed(data, e);
                    }
                });
    }


    /**
     * 取消关注该用户
     *
     * @param position
     */
    private void goCancelConcerned(int position) {
        Api.getInstance().follow(userBean.getId() + "", userList.get(position).getId() + "", false + "").
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.toast_unsubscribe_success));
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
            myConcernSwipeRefresh.setRefreshing(true);
            userList.clear();
            getUserFollowList(pageNo + "", pageSize + "");
        });
        return errorView;
    }

    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_match_view, mRecyclerView, false);
        TextView tv_content = emptyView.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.empty_data));
        return emptyView;
    }

    public void showDialog() {
        mDialogBottom = new Dialog(this, R.style.BottomDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(this).inflate(R.layout.concerned_dialog_layout, null);
        //初始化控件
        mBtnOk = (TextView) mInflate.findViewById(R.id.btn_ok);
        mBtnCancel = (TextView) mInflate.findViewById(R.id.btn_cancel);
        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialogBottom.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialogBottom.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.dimAmount = 0.4f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//       将属性设置给窗体
        mDialogBottom.show();//显示对话框
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                goCancelConcerned(position);
                mDialogBottom.dismiss();
                break;
            case R.id.btn_cancel:
                mDialogBottom.dismiss();
                break;
        }
    }
}
