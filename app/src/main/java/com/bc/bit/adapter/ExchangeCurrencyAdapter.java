package com.bc.bit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.bean.CurrencyBean;
import com.bc.bit.util.ImgUtil;

import java.util.List;

public class ExchangeCurrencyAdapter extends BaseAdapter {
    private Context mContext;
    private ListView lv;
    private List<CurrencyBean.RatesBean> ratesBeanList;

    public ExchangeCurrencyAdapter(Context context, ListView lv) {
        this.mContext = context;
        this.lv = lv;
    }


    public void setList(List<CurrencyBean.RatesBean> ratesBeanList) {
        this.ratesBeanList = ratesBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ratesBeanList != null && ratesBeanList.size() > 0) {
            return ratesBeanList.size();
        }
        return 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {

        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_currency, null);
        ImageView item_currency_image = (ImageView)view.findViewById(R.id.item_currency_image);
        TextView item_currency_name = (TextView) view.findViewById(R.id.item_currency_name);
        CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.ctv_name);
        item_currency_name.setText(ratesBeanList.get(position).getName_zh());
        item_currency_image.setImageResource(ImgUtil.getImage(ratesBeanList.get(position).getCurrencyExchangeImage()));
        updateBackground(position, ctv);
        return view;

    }

    public void updateBackground(int position, View view) {
        int backgroundId;
        if (lv.isItemChecked(position)) {
            backgroundId = R.drawable.ic_selected;
        } else {
            backgroundId = R.drawable.btn_unselected;
        }
        Drawable background = mContext.getResources().getDrawable(backgroundId);
        view.setBackgroundDrawable(background);
    }
}
