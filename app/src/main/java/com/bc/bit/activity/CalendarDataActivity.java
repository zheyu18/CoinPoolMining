package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.CalendarDataAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.CalenderDataBean;
import com.bc.bit.bean.CountryImageBean;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.util.HttpUtil;
import com.bc.bit.view.CalendarDataDividerItemDecoration;
import com.bc.bit.view.SpaceItemDecoration;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.lihang.ShadowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 *  日历数据
 */
public class CalendarDataActivity extends BaseTitleActivity implements CalendarView.OnCalendarSelectListener{
    @BindView(R.id.calendar_data_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.calendar_swipe_refresh)
    SwipeRefreshLayout calendarSwipeRefresh;
    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.calendarLayout)
    CalendarLayout calendarLayout;
    @BindView(R.id.layout_calender_data)
    ShadowLayout layoutCalenderData;
    private CalendarDataAdapter mAdapter;
    private List<CalenderDataBean> calendarDataList = new ArrayList<>();
    private List<CountryImageBean> countryImageBeanList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 20;
    private String mDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_data);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.calendar_data));
        calendarSwipeRefresh.setColorSchemeResources(R.color.col_theme);
        GridLayoutManager layoutManager = new GridLayoutManager(getMainActivity(), 2);
        final int spanCount = 2;
        mRecyclerView.addItemDecoration(new CalendarDataDividerItemDecoration(getMainActivity(), 15, spanCount));
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CalendarDataAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        calendarSwipeRefresh.setRefreshing(true);
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        int day = mCalendarView.getCurDay();
        tvCurrentDate.setText(year + "年" + month + "月");
        mDate = year + "-" + month + "-" + day;
        getCalendarData(pageNo + "", pageSize + "", mDate);
        initRefreshData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mCalendarView.setOnCalendarSelectListener(this);
    }

    /**
     * 刷新数据
     */
    private void initRefreshData() {
        calendarSwipeRefresh.setOnRefreshListener(() -> {
            calendarDataList.clear();
            getCalendarData(pageNo + "", pageSize + "", mDate);
        });
    }

    /**
     * 获取日历数据
     */
    private void getCalendarData(String pageno, String pagesize, String date) {
        Api.getInstance().getFinanceCalender(pageno, pagesize, date)
                .subscribe(new HttpObserver<ListResponse<CalenderDataBean>>() {
                    @Override
                    public void onSucceeded(ListResponse<CalenderDataBean> data) {
                        layoutCalenderData.setVisibility(View.VISIBLE);
                        if (data.getList() == null) return;
                        calendarDataList.addAll(data.getList());
                        Collections.reverse(calendarDataList);
                        if (calendarDataList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        getCountryImageData();
                    }

                    @Override
                    public boolean onFailed(ListResponse<CalenderDataBean> data, Throwable e) {
                        calendarSwipeRefresh.setRefreshing(false);
                        mAdapter.setEmptyView(getErrorView());
                        HttpUtil.handlerRequest(null, e);
                        return super.onFailed(data, e);
                    }
                });
    }

    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_view, mRecyclerView, false);
        TextView tv_content = emptyView.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.empty_data));
        return emptyView;
    }


    /**
     * 获取国家旗帜图片
     */
    private void getCountryImageData() {
        Api.getInstance().queryAllCountry()
                .subscribe(new ObserverAdapter<List<CountryImageBean>>() {
                    @Override
                    public void onNext(List<CountryImageBean> data) {
                        calendarSwipeRefresh.setRefreshing(false);
                        countryImageBeanList.addAll(data);
                        for (int i = 0; i < calendarDataList.size(); i++) {
                            for (int j = 0; j < countryImageBeanList.size(); j++) {
                                if (calendarDataList.get(i).getCountry().equals(countryImageBeanList.get(j).getCountryName())) {
                                    calendarDataList.get(i).setCountryImageUrl(countryImageBeanList.get(j).getCountryFlag());
                                }
                            }
                        }
                        mAdapter.setList(calendarDataList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        calendarSwipeRefresh.setRefreshing(false);
                        mAdapter.getLoadMoreModule().loadMoreFail();
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
            calendarSwipeRefresh.setRefreshing(true);
            calendarDataList.clear();
            getCalendarData(pageNo + "", pageSize + "", mDate);
        });
        return errorView;
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvCurrentDate.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
        mDate = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay();
        if (isClick) {
            calendarSwipeRefresh.setRefreshing(true);
            calendarDataList.clear();
            getCalendarData(pageNo + "", pageSize + "", mDate);
            mAdapter.notifyDataSetChanged();
        }
    }

}
