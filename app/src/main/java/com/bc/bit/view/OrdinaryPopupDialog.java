package com.bc.bit.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bc.bit.R;


public class OrdinaryPopupDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private TextView btn_ok;
    private OrdinaryPopupCallback callback;

    public OrdinaryPopupDialog(@NonNull Context context) {
        super(context);
    }
    public OrdinaryPopupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.ordinary_popup_dialog, null);
        btn_ok = (TextView) layout.findViewById(R.id.bnt_ok);
        init();
        btn_ok.setOnClickListener(this);
        this.setContentView(layout);

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bnt_ok:
                if (callback != null) {
                    callback.callback();
                }
                dismiss();
                break;
        }

    }

    public interface OrdinaryPopupCallback{
        void callback();
    }
    public void setOnItemClickOrdinaryPopupDialog(OrdinaryPopupCallback callback) {
        this.callback = callback;
    }
}
