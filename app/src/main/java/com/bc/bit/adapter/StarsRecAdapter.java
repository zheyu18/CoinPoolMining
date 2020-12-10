package com.bc.bit.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.UserBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

/**
 * 大咖推荐
 */
public class StarsRecAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    public StarsRecAdapter() {
        super(R.layout.layout_stars_rec_item);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull UserBean item) {
        RoundedImageView item_star_rec_avatar = helper.getView(R.id.item_star_rec_avatar);
        TextView item_star_rec_name = helper.getView(R.id.item_star_rec_name);
        TextView item_star_rec_fans = helper.getView(R.id.item_star_rec_fans);
        TextView item_star_rec_concerned = helper.getView(R.id.item_star_rec_concerned);

        Glide.with(getContext()).load(item.getHead())
                .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                .error(R.drawable.pic_me_placeholder).into(item_star_rec_avatar);
        item_star_rec_name.setText(item.getNickName());
        item_star_rec_fans.setText(item.getFansCount()+"粉丝");
        item_star_rec_concerned.setText(item.getFansCount()+"关注");

    }
}
