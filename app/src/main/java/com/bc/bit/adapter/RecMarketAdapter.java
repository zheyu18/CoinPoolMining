package com.bc.bit.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.bc.bit.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scrb.klinechart.request.bean.KChartBean;

import org.jetbrains.annotations.NotNull;

public class RecMarketAdapter extends BaseQuickAdapter<KChartBean, BaseViewHolder> {

    public RecMarketAdapter() {
        super(R.layout.layout_rec_market_item);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull KChartBean item) {
        LinearLayout layout_rec_market = helper.getView(R.id.layout_rec_market);
        TextView item_rec_market_title = helper.getView(R.id.item_rec_market_title);
        TextView item_rec_market_latest_price = helper.getView(R.id.item_rec_market_latest_price);
        TextView item_rec_market_trend = helper.getView(R.id.item_rec_market_trend);

        item_rec_market_title.setText(item.getSymbol());
        item_rec_market_latest_price.setText(item.getClose() + "");
        try {
            double upPercent = item.getDegree();
            item_rec_market_trend.setText(upPercent + "%");
            if (upPercent > 0) {
                item_rec_market_latest_price.setTextColor(getContext().getResources().getColor(R.color.col_red));
                layout_rec_market.setBackground(getContext().getDrawable(R.drawable.bg_layout_market_up));
                item_rec_market_trend.setTextColor(getContext().getResources().getColor(R.color.col_red));
            } else {
                item_rec_market_latest_price.setTextColor(getContext().getResources().getColor(R.color.col_green));
                layout_rec_market.setBackground(getContext().getDrawable(R.drawable.bg_layout_market_down));
                item_rec_market_trend.setTextColor(getContext().getResources().getColor(R.color.col_green));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
