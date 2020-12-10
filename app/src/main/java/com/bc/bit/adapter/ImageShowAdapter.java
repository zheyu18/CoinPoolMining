package com.bc.bit.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bc.bit.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 *  图片适配器
 */
public class ImageShowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageShowAdapter() {
        super(R.layout.item_show_image);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, String data) {
        //找图片控件
        ImageView iv_banner = helper.getView(R.id.iv_banner);
        //显示图片
        Glide.with(getContext()).load(data).apply(new RequestOptions().placeholder(R.drawable.pic_placeholder)).into(iv_banner);
    }
}
