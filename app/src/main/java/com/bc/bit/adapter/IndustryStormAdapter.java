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

public class IndustryStormAdapter extends BaseQuickAdapter<TalkBean, BaseViewHolder> implements LoadMoreModule {
    private static final String TAG = "IndustryStormAdapter";
    public IndustryStormAdapter() {
        super(R.layout.item_industry_storm);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull TalkBean item) {
        TextView item_industry_storm_title = helper.getView(R.id.item_industry_storm_title);
        TextView item_industry_storm_time = helper.getView(R.id.item_industry_storm_time);
        ImageView item_industry_storm_img = helper.getView(R.id.item_industry_storm_img);
        if (item.getContent().length() > 35) {
            item_industry_storm_title.setText(item.getContent().substring(0, 35) + "...");
        } else {
            item_industry_storm_title.setText(item.getContent());
        }
        item_industry_storm_time.setText(TimeUtil.commonFormat(item.getPublishTime()- Constant.TIME_DIFF));
        Glide.with(getContext()).load(item.getPicture()).
                apply(new RequestOptions().placeholder(R.drawable.pic_placeholder)).into(item_industry_storm_img);
    }

}
