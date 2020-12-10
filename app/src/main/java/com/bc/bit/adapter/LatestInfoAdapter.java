package com.bc.bit.adapter;

import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.ExpressNewsListBean;
import com.bc.bit.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class LatestInfoAdapter extends BaseQuickAdapter<ExpressNewsListBean.ListBean.LivesBean, BaseViewHolder> implements LoadMoreModule {
    public LatestInfoAdapter() {
        super(R.layout.layout_latest_info_item);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull ExpressNewsListBean.ListBean.LivesBean item) {
        TextView item_latest_info_date = helper.getView(R.id.item_latest_info_date);
        TextView item_latest_info_content = helper.getView(R.id.item_latest_info_content);
        item_latest_info_date.setText(TimeUtil.timeLongdate((item.getCreated_at())));
        if (item.getContent().length() > 80) {
            item_latest_info_content.setText(item.getContent().substring(0, 80) + "...");
        } else {
            item_latest_info_content.setText(item.getContent());
        }
    }
}
