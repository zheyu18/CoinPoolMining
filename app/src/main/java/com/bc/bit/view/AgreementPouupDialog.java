package com.bc.bit.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bc.bit.R;
import com.bc.bit.activity.ProtocolActivity;
import com.bc.bit.util.Constant;
import com.bc.bit.util.NextActivityRequest;


public class AgreementPouupDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private AgreementDialogCallback callback;
    private TextView txt_dialog_content, bnt_ok, bnt_exit;

    public AgreementPouupDialog(@NonNull Context context) {
        super(context);
    }

    public AgreementPouupDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.agreement_popup_dialog, null);
        txt_dialog_content = (TextView) layout.findViewById(R.id.txt_dialog_content);
        bnt_exit = (TextView) layout.findViewById(R.id.bnt_exit);
        bnt_ok = (TextView) layout.findViewById(R.id.bnt_ok);
        init();
        bnt_ok.setOnClickListener(this);
        bnt_exit.setOnClickListener(this);

        initAgreementStyle();
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

    public void initAgreementStyle() {
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append(context.getString(R.string.simple_agreement_txt));

        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                avoidHintColor(widget);
                NextActivityRequest.with(context, ProtocolActivity.class).put(Constant.ID, Constant.USER_AGREEMENT).go();
            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint ds) {
                /**set textColor**/
                ds.setColor(ds.linkColor);
                /**Remove the underline**/
                ds.setUnderlineText(false);
            }

        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                avoidHintColor(widget);
                NextActivityRequest.with(context, ProtocolActivity.class).put(Constant.ID, Constant.PRIVATE_POLICY).go();
            }

            //去除连接下划线

            @Override
            public void updateDrawState(TextPaint ds) {
                /**set textColor**/
                ds.setColor(ds.linkColor);
                /**Remove the underline**/
                ds.setUnderlineText(false);
            }
        };
        style.setSpan(clickableSpan, 157, 163, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(clickableSpan1, 164, 170, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        txt_dialog_content.setText(style);


        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.col_theme));
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(context.getResources().getColor(R.color.col_theme));
        style.setSpan(foregroundColorSpan, 157, 163, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(foregroundColorSpan1, 164, 170, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //配置给TextView
        txt_dialog_content.setMovementMethod(LinkMovementMethod.getInstance());
        txt_dialog_content.setText(style);
    }

    private void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(context.getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bnt_exit:
                if (callback != null) {
                    callback.logoutCallback();
                }
                dismiss();
                break;
            case R.id.bnt_ok:
                if (callback != null) {
                    callback.confirmCallback();
                }
                dismiss();
                break;

        }

    }

    public interface AgreementDialogCallback {
        void logoutCallback();

        void confirmCallback();
    }

    public void setOnItemClickAgreementDialog(AgreementDialogCallback callback) {
        this.callback = callback;
    }
}
