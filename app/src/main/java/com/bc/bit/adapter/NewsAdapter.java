package com.bc.bit.adapter;

import android.content.Context;
import android.view.View;


import com.bc.bit.R;
import com.bc.bit.bean.NewsBean;

import java.util.List;


/**
 *  消息适配器
 */
public class NewsAdapter extends BaseRecyclerViewAdapter<NewsBean>{

    private OnDeleteClickLister mDeleteClickListener;

    public NewsAdapter(Context context, List<NewsBean> data) {
        super(context, data, R.layout.layout_news_item);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, NewsBean bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(v -> {
                if (mDeleteClickListener != null) {
                    mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                }
            });
        }
    }
    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
