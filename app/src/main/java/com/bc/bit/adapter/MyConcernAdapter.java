package com.bc.bit.adapter;

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

public class MyConcernAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> implements LoadMoreModule {

    public MyConcernAdapter(){
        super(R.layout.item_layout_my_concerned);

    }
    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, UserBean listBean) {
        RoundedImageView image_header = helper.getView(R.id.image_header);
        Glide.with(getContext()).load(listBean.getHead()).apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder)).into(image_header);
        TextView item_tv_name = helper.getView(R.id.item_tv_name);
        item_tv_name.setText(listBean.getNickName());

    }
}
