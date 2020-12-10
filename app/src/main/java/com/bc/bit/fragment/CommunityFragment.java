package com.bc.bit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bc.bit.R;
import com.bc.bit.activity.UserPublishActivity;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  社区
 */
public class CommunityFragment extends BaseCommonFragment {
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"推荐", "关注"};
    private MyPagerAdapter mAdapter;
    public static Fragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mFragments.add(new CommunityRecFragment());
        mFragments.add(new CommunityConcernFragment());

        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setCurrentTab(0);

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    @OnClick(R.id.iv_publish)
    public void onViewClicked() {
        if (!checkLogin()) return;
        startActivity(UserPublishActivity.class);
    }

    @Override
    protected boolean isBindView() {
        return true;
    }

    /**
     * 设置Fragment target，由子类实现
     */
    @Override
    protected boolean setFragmentTarget() {
        return false;
    }
}
