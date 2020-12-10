package com.scrb.klinechart.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
public class KFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    private String[][] mTitle;
    public KFragmentAdapter(FragmentManager supportFragmentManager, List<Fragment> list, String[][] mTitle) {
        super(supportFragmentManager);
        this.list = list;
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position][0];
    }
}
