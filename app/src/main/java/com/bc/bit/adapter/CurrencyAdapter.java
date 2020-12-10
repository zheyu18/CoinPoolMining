package com.bc.bit.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bc.bit.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scrb.klinechart.request.bean.KChartBean;

import org.jetbrains.annotations.NotNull;

public class CurrencyAdapter extends BaseQuickAdapter<KChartBean, BaseViewHolder> implements LoadMoreModule {

    public CurrencyAdapter() {
        super(R.layout.layout_currency_item);
    }


    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull KChartBean item) {
        TextView tv_currency_title = helper.getView(R.id.tv_currency_title);
        TextView tv_currency_high_price = helper.getView(R.id.tv_currency_high_price);
        TextView tv_currency_latest_price = helper.getView(R.id.tv_currency_latest_price);
        TextView tv_currency_amplitude = helper.getView(R.id.tv_currency_amplitude);
        ImageView iv_currency_trend = helper.getView(R.id.iv_currency_trend);
        tv_currency_title.setText(item.getSymbol());
        tv_currency_high_price.setText(item.getCurrency());
        tv_currency_latest_price.setText(item.getClose()+"");
        if (item.getDegree() != 0) {
            try {
                double upPercent = item.getDegree();
                tv_currency_amplitude.setText(upPercent + "%");
                if (upPercent > 0) {
                    tv_currency_amplitude.setTextColor(getContext().getResources().getColor(R.color.col_red));
                    iv_currency_trend.setImageResource(R.drawable.ic_currency_up);
                } else {
                    tv_currency_amplitude.setTextColor(getContext().getResources().getColor(R.color.col_green));
                    iv_currency_trend.setImageResource(R.drawable.ic_currency_down);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
