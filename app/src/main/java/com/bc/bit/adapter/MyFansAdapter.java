package com.bc.bit.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.UserBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

public class MyFansAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> implements LoadMoreModule {

    public MyFansAdapter() {
        super(R.layout.item_layout_my_fans);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, UserBean listBean) {
        RoundedImageView item_fans_image_header = helper.getView(R.id.item_fans_image_header);
        Glide.with(getContext()).load(listBean.getHead()).apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder)).into(item_fans_image_header);
        TextView item_fans_name = helper.getView(R.id.item_fans_name);
        item_fans_name.setText(listBean.getNickName());
        TextView item_fans_signature = helper.getView(R.id.item_fans_signature);
        if (TextUtils.isEmpty(listBean.getSignature())){
            item_fans_signature.setText(getContext().getString(R.string.no_signature));
        }else {
            item_fans_signature.setText(listBean.getSignature());
        }
    }
}
