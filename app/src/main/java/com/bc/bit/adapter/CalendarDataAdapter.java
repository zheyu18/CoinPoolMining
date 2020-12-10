package com.bc.bit.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bc.bit.R;
import com.bc.bit.bean.CalenderDataBean;
import com.bc.bit.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

public class CalendarDataAdapter extends BaseQuickAdapter<CalenderDataBean, BaseViewHolder> implements LoadMoreModule {

    public CalendarDataAdapter() {
        super(R.layout.layout_calendar_data_item);
    }


    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull CalenderDataBean item) {
        RoundedImageView iv_country_flag = helper.getView(R.id.iv_country_flag);
        TextView tv_item_calendar_date = helper.getView(R.id.tv_item_calendar_date);
        TextView tv_item_calendar_data_title = helper.getView(R.id.tv_item_calendar_data_title);
        TextView tv_item_former_value = helper.getView(R.id.tv_item_former_value);
        TextView tv_item_expected = helper.getView(R.id.tv_item_expected);
        TextView tv_item_publish = helper.getView(R.id.tv_item_publish);
        TextView item_calendar_publish_state = helper.getView(R.id.item_calendar_publish_state);
        RatingBar item_calendar_data_ratingBar = helper.getView(R.id.item_calendar_data_ratingBar);

        tv_item_calendar_date.setText(TimeUtil.getDateToHourString(item.getTime()));
        tv_item_calendar_data_title.setText(item.getName());
        tv_item_former_value.setText(String.format("前值：%s", item.getPrevious()));
        tv_item_expected.setText(String.format("预期：%s", item.getAffect()));

        Glide.with(getContext()).load(item.getCountryImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .error(R.drawable.picture_image_placeholder).into(iv_country_flag);
        item_calendar_data_ratingBar.setRating(item.getStar());
        if (!TextUtils.isEmpty(item.getActual())) {
            item_calendar_publish_state.setBackgroundResource(R.drawable.bg_publish);
            item_calendar_publish_state.setTextColor(ContextCompat.getColor(getContext(), R.color.col_theme));
            item_calendar_publish_state.setText("已公布");
            tv_item_publish.setText(String.format("公布：%s", item.getActual()));
        } else {
            item_calendar_publish_state.setBackgroundResource(R.drawable.bg_unpublished);
            item_calendar_publish_state.setTextColor(ContextCompat.getColor(getContext(), R.color.col_txt_11));
            item_calendar_publish_state.setText("未公布");
            tv_item_publish.setText("公布：--");
        }
    }
}
