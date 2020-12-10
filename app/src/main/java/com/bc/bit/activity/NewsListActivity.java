package com.bc.bit.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.NewsAdapter;
import com.bc.bit.bean.NewsBean;
import com.bc.bit.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *  消息中心
 */
public class NewsListActivity extends BaseTitleActivity {
    @BindView(R.id.rv_news_list)
    SlideRecyclerView rvNewsList;
    private List<NewsBean> newsBeans;
    private NewsAdapter newsAdapter;
    private NewsBean newsbean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.info_center));
        rvNewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.concern_divider_inset));
        rvNewsList.addItemDecoration(itemDecoration);
        newsBeans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            newsbean = new NewsBean();
            newsBeans.add(newsbean);
        }
        newsAdapter = new NewsAdapter(this, newsBeans);
        rvNewsList.setAdapter(newsAdapter);
        newsAdapter.setOnDeleteClickListener((view, position) -> {
            newsBeans.remove(position);
            newsAdapter.notifyDataSetChanged();
            rvNewsList.closeMenu();
        });
    }
}
