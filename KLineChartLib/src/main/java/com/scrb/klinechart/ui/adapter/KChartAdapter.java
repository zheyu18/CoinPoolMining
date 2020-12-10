package com.scrb.klinechart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.scrb.klinechart.R;
import com.scrb.klinechart.request.bean.KChartBean;

import java.util.List;

public class KChartAdapter extends RecyclerView.Adapter<KChartAdapter.ViewHolder> {
    public List<KChartBean> data;
    public Context mContext;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener onClickListener = null;

    public KChartAdapter(List<KChartBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    public void setData(List<KChartBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_k_chart, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kChartName.setText(data.get(position).getSymbol());
        holder.kChartNewPrice.setText(data.get(position).getClose() + "");
        holder.kChartTopPrice.setText(data.get(position).getHigh() + "");
        holder.kChartRose.setText(data.get(position).getDegree() + "%");
        holder.kChatBox.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView kChartName;
        private final TextView kChartNewPrice;
        private final TextView kChartTopPrice;
        private final TextView kChartRose;
        private final LinearLayout kChatBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kChartName = itemView.findViewById(R.id.item_k_chart_name);
            kChartNewPrice = itemView.findViewById(R.id.item_k_chart_new_price);
            kChartTopPrice = itemView.findViewById(R.id.item_k_chart_top_price);
            kChartRose = itemView.findViewById(R.id.item_k_chart_rose);
            kChatBox = itemView.findViewById(R.id.item_k_chart_box);
        }
    }

    public interface OnClickListener {
        void onClick(KChartBean data);
    }
}
