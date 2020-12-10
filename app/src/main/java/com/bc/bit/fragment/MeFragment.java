package com.bc.bit.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.AboutActivity;
import com.bc.bit.activity.FeedbackActivity;
import com.bc.bit.activity.MyConcernedActivity;
import com.bc.bit.activity.MyDynamicsActivity;
import com.bc.bit.activity.MyFansActivity;
import com.bc.bit.activity.MyInformationActivity;
import com.bc.bit.activity.ProtocolActivity;
import com.bc.bit.activity.SettingActivity;
import com.bc.bit.activity.SignInActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.LoginSuccessEvent;
import com.bc.bit.event.MyinforChangeSuccessEvent;
import com.bc.bit.event.UnsubscribeEvent;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.bc.bit.util.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MeFragment extends BaseCommonFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_avatar)
    RoundedImageView ivAvatar;
    @BindView(R.id.tv_login_immediately)
    TextView tvLoginImmediately;
    @BindView(R.id.layout_myinfor)
    LinearLayout layoutMyinfor;
    @BindView(R.id.me_concerned_count)
    TextView meConcernedCount;
    @BindView(R.id.me_fans_count)
    TextView meFansCount;
    @BindView(R.id.me_dynamic_count)
    TextView meDynamicCount;
    @BindView(R.id.me_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private UserBean userBean;

    public static Fragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        initRefreshLayout();
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
        EventBus.getDefault().register(this);
        if (userBean != null) {
            showUserInfor();
            getRecommandUserList();
            getUserInforByUserId(userBean != null ? String.valueOf(userBean.getId()) : "");
        } else {
            tvLoginImmediately.setVisibility(View.VISIBLE);
            layoutMyinfor.setVisibility(View.GONE);
        }
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (userBean != null) {
                getRecommandUserList();
                getUserInforByUserId(userBean != null ? String.valueOf(userBean.getId()) : "");
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
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
                            MyContext.context().save(data.getData());
                            userBean = data.getData();
                            showUserInfor();
                        }
                    }
                });
    }


    /**
     * 显示用户信息
     */
    private void showUserInfor() {
        layoutMyinfor.setVisibility(View.VISIBLE);
        tvLoginImmediately.setVisibility(View.GONE);
        if (getActivity() != null) {
            Glide.with(getMainActivity()).load(userBean.getHead())
                    .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder)).into(ivAvatar);
        }
        tvName.setText(userBean.getNickName());
        if (TextUtils.isEmpty(userBean.getSignature())) {
            tvSignature.setText(getString(R.string.no_signature));
        } else {
            tvSignature.setText(userBean.getSignature());
        }
        meConcernedCount.setText(userBean.getFollowCount() + "");
        meDynamicCount.setText(userBean.getTalkCount() + "");
    }


    /**
     * 获取推荐用户列表
     */
    private void getRecommandUserList() {
        Api.getInstance().getRecommandUserList(userBean != null ? String.valueOf(userBean.getId()) : "" + "")
                .subscribe(new HttpObserver<ListResponse<UserBean>>() {
                    @Override
                    public void onSucceeded(ListResponse<UserBean> data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (data.getList() != null) {
                            meFansCount.setText(data.getList().size() + "");
                        }
                    }

                    @Override
                    public boolean onFailed(ListResponse<UserBean> data, Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        return super.onFailed(data, e);
                    }
                });
    }


    @OnClick({R.id.iv_avatar, R.id.tv_login_immediately, R.id.layout_myinfor,
            R.id.layout_me_concern, R.id.layout_me_fans, R.id.layout_me_dynamic,
            R.id.layout_me_about, R.id.layout_me_feedback, R.id.layout_me_user_agreement,
            R.id.layout_me_private_policy, R.id.layout_me_cache, R.id.layout_me_setting
    ,R.id.iv_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
            case R.id.layout_myinfor:
                if (!checkLogin()) return;
                startActivity(MyInformationActivity.class);
                break;
            case R.id.tv_login_immediately:
                if (!checkLogin()) return;
                break;
            case R.id.layout_me_concern:
                if (!checkLogin()) return;
                startActivity(MyConcernedActivity.class);
                break;
            case R.id.layout_me_fans:
                if (!checkLogin()) return;
                startActivity(MyFansActivity.class);
                break;
            case R.id.layout_me_dynamic:
                if (!checkLogin()) return;
                startActivityExtraData(MyDynamicsActivity.class, userBean);
                break;
            case R.id.layout_me_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.layout_me_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.layout_me_user_agreement:
                startActivityExtraId(ProtocolActivity.class, Constant.USER_AGREEMENT);
                break;
            case R.id.layout_me_private_policy:
                startActivityExtraId(ProtocolActivity.class, Constant.PRIVATE_POLICY);
                break;
            case R.id.layout_me_cache:
                showTxt(getString(R.string.toast_no_cache));
                break;
            case R.id.layout_me_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.iv_sign_in:
                if (!checkLogin()) return;
                startActivity(SignInActivity.class);
                break;
        }
    }

    /**
     * 登录成功事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        userBean = MyContext.context().getUser();
        if (userBean != null) {
            getUserInforByUserId(userBean.getId() + "");
            getRecommandUserList();
        }
    }


    /**
     * 修改个人信息成功事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyinforChangeSuccessEvent(MyinforChangeSuccessEvent event) {
        getUserInforByUserId(userBean.getId() + "");
    }

    /**
     * 取消关注成功事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnsubscribeEvent(UnsubscribeEvent event) {
        userBean = MyContext.context().getUser();
        if (userBean != null) {
            getUserInforByUserId(userBean.getId() + "");
            getRecommandUserList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
