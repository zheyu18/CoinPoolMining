package com.scrb.klinechart.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.scrb.klinechart.R;
import com.scrb.klinechart.ui.fragment.KChartDetailsFragment;
import com.scrb.klinechart.ui.weigt.NoScrollViewPager;
import com.scrb.klinechart.ui.adapter.KFragmentAdapter;
import com.winks.base_utils.ui.view.KBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class KChartDetailsActivity extends KBaseActivity {
    private static final String PAGER_TITLE = "title";
    private static final String PAGER_DATA = "pager_data";
    private static final String PAGER_DATA_NAME = "pager_data_name";
    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
//    K线周期，支持的值：M1/M5/M15/M30/H1/H4/D1/W1/MONTH ,
//    分别对应：1分钟、5分钟、15分钟、30分钟、1小时、4小时、1天、1周、1月
    private String[][] mTitles = {{"1分钟","M1"},{"5分钟","M5"},{"15分钟","M15"},{"30分钟","M30"},{"1小时","H1"},{"1天","D1"}};
    private KFragmentAdapter mKFragmentAdapter;

    /**
     * 跳转Activity
     * @param activity
     * @param ticker 交易所与交易对信息
     */
    public static void startActivity(Activity activity, String title,String ticker,String name) {
        Intent intent = new Intent(activity,KChartDetailsActivity.class);
        intent.putExtra(PAGER_TITLE,title);
        intent.putExtra(PAGER_DATA,ticker);
        intent.putExtra(PAGER_DATA_NAME,name);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        findViewById(R.id.k_chart_details_back_box).setOnClickListener(v -> finish());
        TextView mTitleTextView = findViewById(R.id.k_chart_details_title);
        mTitleTextView.setText(getIntent().getStringExtra(PAGER_TITLE));
        mTabLayout = findViewById(R.id.k_chart_details_tab_layout);
        mViewPager = findViewById(R.id.k_chart_details_view_pager);
        mViewPager.setScanScroll(false);
        for (int i= 0;i<mTitles.length;i++){
            mFragmentList.add(KChartDetailsFragment.newInstance(new String[]{getIntent().getStringExtra(PAGER_DATA),mTitles[i][1]}));
        }
        mKFragmentAdapter = new KFragmentAdapter(getSupportFragmentManager(), mFragmentList,mTitles);
        mViewPager.setAdapter(mKFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.k_chart_details_tab_layout), ContextCompat.getColor(this, R.color.k_chart_details_tab_layout));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.k_chart_details_tab_layout));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_k_chart_details;
    }
}
