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


public class SignInDialog extends Dialog {
    private TextView tv_sign_in_confirm;
    private Context context;
    public SignInDialog(@NonNull Context context) {
        super(context);
    }

    public SignInDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.sign_in_popup_dialog, null);
        tv_sign_in_confirm = (TextView) layout.findViewById(R.id.tv_sign_in_confirm);
        init();
        this.setContentView(layout);
        tv_sign_in_confirm.setOnClickListener(v -> dismiss());
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
        setCanceledOnTouchOutside(true);
    }
}
