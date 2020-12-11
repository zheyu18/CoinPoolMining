package com.bc.bit.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bc.bit.R;
import com.bc.bit.adapter.ExchangeCurrencyAdapter;
import com.bc.bit.bean.CurrencyBean;
import com.bc.bit.util.LogUtil;

import java.util.List;

public class ExchangeCurrencyPopupDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener{
    private Context context;
    private ListView mListView;
    private TextView tv_currency_confirm;
    private ExchangeCurrencyDialogCallback callback;
    private ExchangeCurrencyAdapter mAdapter;
    private List<CurrencyBean.RatesBean> ratesBeanList;
    private static final String TAG = "ExchangeCurrencyPopupDialog";
    private int clickPosition = -1;

    public ExchangeCurrencyPopupDialog(@NonNull Context context) {
        super(context);
    }
    public ExchangeCurrencyPopupDialog(@NonNull Context context, int themeResId,
                               List<CurrencyBean.RatesBean> ratesBeanList) {
        super(context, themeResId);
        this.context = context;
        this.ratesBeanList = ratesBeanList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.currency_popup_dialog, null);
        tv_currency_confirm = layout.findViewById(R.id.tv_currency_confirm);
        mListView = layout.findViewById(R.id.currency_listview);
        tv_currency_confirm.setOnClickListener(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        init();
        initData();
        this.setContentView(layout);
    }

    private void initData() {
        mAdapter = new ExchangeCurrencyAdapter(context, mListView);
        mListView.setAdapter(mAdapter);
        mAdapter.setList(ratesBeanList);
        mListView.setOnItemClickListener(this);
    }

    private void init() {
        Window window = getWindow();
        //设置边框距离
        window.getDecorView().setPadding(0, 0, 0, 0);
        //设置dialog位置
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //设置点击Dialog外部任意区域关闭Dialog
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.notifyDataSetChanged();
        this.clickPosition = position;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_currency_confirm:
                if (clickPosition == -1) {
                    Toast.makeText(context, "请选择货币", Toast.LENGTH_LONG).show();
                    return;
                }
                if (callback != null) {
                    callback.exchangeCurrencyCallback(clickPosition);
                    dismiss();
                }
                break;
        }
    }
    public interface ExchangeCurrencyDialogCallback {
        void exchangeCurrencyCallback(int position);
    }

    public void setOnItemClickExchangeCurrency(ExchangeCurrencyDialogCallback callback) {
        this.callback = callback;
    }
}
