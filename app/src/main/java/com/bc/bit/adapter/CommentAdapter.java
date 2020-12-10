package com.bc.bit.adapter;

import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.ArticleCommentBean;
import com.bc.bit.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;


import org.jetbrains.annotations.NotNull;

public class CommentAdapter extends BaseQuickAdapter<ArticleCommentBean, BaseViewHolder> implements LoadMoreModule {

    public CommentAdapter() {
        super(R.layout.layout_comment_item);
    }


    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull ArticleCommentBean item) {

        RoundedImageView image_header = helper.getView(R.id.image_header);
        TextView item_comment_name = helper.getView(R.id.item_comment_name);
        TextView item_comment_time =helper.getView(R.id.item_comment_time);
        TextView item_comment_content =helper.getView(R.id.item_comment_content);

        item_comment_name.setText(item.getUser().getNickName());
        item_comment_time.setText(TimeUtil.commonFormat(item.getPublishTime()));
        item_comment_content.setText(item.getContent());
        Glide.with(getContext()).load(item.getUser().getHead())
                .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                .error(R.drawable.picture_image_placeholder).into(image_header);

    }
}
