package com.bc.bit;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.adapter.BaseFragmentAdapter;
import com.bc.bit.fragment.CommunityFragment;
import com.bc.bit.fragment.HomePageFragment;
import com.bc.bit.fragment.MeFragment;
import com.bc.bit.fragment.QuotesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

public class MainActivity extends BaseCommonActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    private BaseFragmentAdapter<Fragment> mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(HomePageFragment.newInstance());
        mPagerAdapter.addFragment(QuotesFragment.newInstance());
        mPagerAdapter.addFragment(CommunityFragment.newInstance());
        mPagerAdapter.addFragment(MeFragment.newInstance());
        mViewPager.setAdapter(mPagerAdapter);
        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                mPagerAdapter.setCurrentItem(HomePageFragment.class);
                ImmersionBar.with(this).statusBarDarkFont(false).init();
                return true;
            case R.id.home_quotes:
                mPagerAdapter.setCurrentItem(QuotesFragment.class);
                ImmersionBar.with(this).statusBarDarkFont(true).init();
                return true;

            case R.id.home_community:
                mPagerAdapter.setCurrentItem(CommunityFragment.class);
                ImmersionBar.with(this).statusBarDarkFont(false).init();
                return true;

            case R.id.home_me:
                mPagerAdapter.setCurrentItem(MeFragment.class);
                ImmersionBar.with(this).statusBarDarkFont(true).init();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }
}
