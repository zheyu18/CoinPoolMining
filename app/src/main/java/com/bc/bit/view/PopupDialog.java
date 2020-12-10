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
import com.bc.bit.util.Constant;


public class PopupDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private TextView dialog_tv_content, dialog_tv_logout, dialog_tv_cancel;
    private PopupDialogCallback callback;
    private String state;

    public PopupDialog(@NonNull Context context) {
        super(context);
    }

    public PopupDialog(@NonNull Context context, int themeResId, String state) {
        super(context, themeResId);
        this.context = context;
        this.state = state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_dialog, null);
        dialog_tv_content = (TextView) layout.findViewById(R.id.dialog_tv_content);
        dialog_tv_logout = (TextView) layout.findViewById(R.id.dialog_tv_logout);
        dialog_tv_cancel = (TextView) layout.findViewById(R.id.dialog_tv_cancel);

        if (Constant.TYPE_ONE.equals(state)) {
            dialog_tv_content.setText(context.getString(R.string.dialog_logout_content));
            dialog_tv_logout.setText(context.getString(R.string.dialog_logout));
            dialog_tv_cancel.setText(context.getString(R.string.dialog_cancel));
        } else if (Constant.TYPE_TWO.equals(state)) {
            dialog_tv_content.setText(context.getString(R.string.dialog_logout_account_content));
            dialog_tv_logout.setTextColor(context.getResources().getColor(R.color.white));
            dialog_tv_cancel.setTextColor(context.getResources().getColor(R.color.col_normal));
            dialog_tv_logout.setText(context.getString(R.string.dialog_logout_account));
            dialog_tv_cancel.setText(context.getString(R.string.dialog_logout_account_cancel));
        }
        init();
        dialog_tv_logout.setOnClickListener(this);
        dialog_tv_cancel.setOnClickListener(this);
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
        setCanceledOnTouchOutside(true);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_tv_cancel:
                cancel();
                break;
            case R.id.dialog_tv_logout:
                if (callback != null) {
                    callback.callback();
                    cancel();
                }
                break;
        }

    }

    public interface PopupDialogCallback {
        void callback();
    }

    public void setOnItemClickPopupDialog(PopupDialogCallback callback) {
        this.callback = callback;
    }

}
