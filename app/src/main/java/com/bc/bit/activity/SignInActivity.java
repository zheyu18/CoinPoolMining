package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.SignInBean;
import com.bc.bit.bean.UserBean;
import com.bc.bit.util.LoadingUtils;
import com.bc.bit.util.TimeUtil;
import com.bc.bit.view.SignInDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  签到中心
 */
public class SignInActivity extends BaseCommonActivity implements CalendarView.OnCalendarInterceptListener{
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;
    @BindView(R.id.btn_sign_in)
    TextView btnSignIn;
    private SignInDialog mSignInDialog;
    private Handler mHandler = new Handler();
    private UserBean userBean;
    private int year, month, day;
    private Map<String, Calendar> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
    }
    @Override
    protected void initListeners() {
        super.initListeners();
        mCalendarView.setOnCalendarInterceptListener(this);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
        getUserSignInState(userBean.getId() + "");
        getUserSignInData(userBean.getId() + "");

        year = mCalendarView.getCurYear();
        month = mCalendarView.getCurMonth();
        day = mCalendarView.getCurDay();
        tvCurrentDate.setText(String.format("%d年%d月%d日", year, month, day));
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    /**
     *  拦截消费事件
     * @param calendar
     * @return
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return true;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {

    }
    @OnClick({R.id.btn_sign_in,R.id.bnt_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                LoadingUtils.showLoading(getMainActivity());
                startSignIn(userBean.getId() + "");
                break;
            case R.id.bnt_back:
                finish();
                break;
        }
    }
    private void startSignIn(String userId) {
        Api.getInstance().signNow(userId)
                .subscribe(new HttpObserver<DetailResponse<Boolean>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Boolean> data) {
                        LoadingUtils.hideLoading();
                        if (data.getData()) {
                            getUserSignInData(userBean.getId() + "");
                            mSignInDialog = new SignInDialog(getMainActivity(), R.style.SignInDialogStyle);
                            mSignInDialog.show();
                            mHandler.postDelayed(() -> {
                                mSignInDialog.dismiss();
                                btnSignIn.setClickable(false);
                                btnSignIn.setText(getString(R.string.checked_in));
                            }, 2000);
                        }
                    }
                });
    }

    /**
     *  渲染签到背景
     * @param userId
     */
    private void getUserSignInData(String userId) {
        Api.getInstance().getSignList(userId)
                .subscribe(new HttpObserver<ListResponse<SignInBean>>() {
                    @Override
                    public void onSucceeded(ListResponse<SignInBean> data) {
                        map.clear();
                        for (int i = 0; i < data.getList().size(); i++) {
                            int time = Integer.parseInt(TimeUtil.getDateToDayString(data.getList().get(i).getTime()));
                            map.put(getSchemeCalendar(year, month, time, 0xFFFFA128, "事").toString(),
                                    getSchemeCalendar(year, month, time, 0xFFFFA128, "事"));
                        }
                        mCalendarView.setSchemeDate(map);
                    }
                });
    }

    /**
     *  获取签到状态
     * @param userid
     */
    private void getUserSignInState(String userid) {
        Api.getInstance().hasSign(userid)
                .subscribe(new HttpObserver<DetailResponse<Boolean>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Boolean> data) {
                        if (data.getData()) {
                            btnSignIn.setClickable(false);
                            btnSignIn.setText(getString(R.string.checked_in));
                        } else {
                            btnSignIn.setClickable(true);
                            btnSignIn.setText(getString(R.string.sign_in));
                        }
                    }
                });
    }
}
