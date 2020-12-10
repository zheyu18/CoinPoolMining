package com.bc.bit.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.util.Constant;
import com.bc.bit.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;

public class HomeHotNewsAdapter extends BaseQuickAdapter<TalkBean, BaseViewHolder> implements LoadMoreModule {
    public HomeHotNewsAdapter() {
        super(R.layout.layout_home_hot_news_item);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull TalkBean item) {
        TextView item_hot_news_title = helper.getView(R.id.item_hot_news_title);
        TextView item_hot_news_time = helper.getView(R.id.item_hot_news_time);
        ImageView item_hot_news_image = helper.getView(R.id.item_hot_news_image);
        if (item.getContent().length() > 30) {
            item_hot_news_title.setText(item.getContent().substring(0, 30) + "...");
        } else {
            item_hot_news_title.setText(item.getContent());
        }
        item_hot_news_time.setText(TimeUtil.commonFormat(item.getPublishTime()- Constant.TIME_DIFF));
        Glide.with(getContext()).load(item.getPicture()).
                apply(new RequestOptions().placeholder(R.drawable.pic_placeholder)).into(item_hot_news_image);
    }
}
