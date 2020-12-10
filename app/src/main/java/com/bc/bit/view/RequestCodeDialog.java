package com.bc.bit.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bc.bit.R;
import com.bc.bit.util.ImgUtil;


public class RequestCodeDialog extends Dialog implements View.OnClickListener{

    private Context context;
    private ImageView dialog_img_code,dialog_cancel;
    private EditText dialog_et_code;
    private TextView dialog_text_refresh, dialog_text_commit;
    private RequestCodeDialogCallback callback;
    private String imageCode;

    public RequestCodeDialog(@NonNull Context context) {
        super(context);
    }

    public RequestCodeDialog(Context context, int themeResId, String imageCode) {
        super(context, themeResId);
        this.context = context;
        this.imageCode = imageCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.request_code_dialog, null);
        dialog_img_code = (ImageView) layout.findViewById(R.id.dialog_img_code);
        dialog_et_code = (EditText) layout.findViewById(R.id.dialog_et_code);
//        dialog_text_refresh = (TextView) layout.findViewById(R.id.dialog_text_refresh);
        dialog_text_commit = (TextView) layout.findViewById(R.id.dialog_text_commit);
        dialog_cancel = (ImageView)layout.findViewById(R.id.dialog_cancel);
        init();
        dialog_img_code.setImageBitmap(ImgUtil.base64ToBitmap(imageCode));
        this.setContentView(layout);
        dialog_img_code.setOnClickListener(this);
        dialog_text_commit.setOnClickListener(this);
        dialog_cancel.setOnClickListener(this);
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

    public void setRequestCodeDialog(RequestCodeDialogCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_img_code:  //获取图形验证码
                if (callback != null) {
                    callback.callbackCode(dialog_img_code);
                }
                break;

            case R.id.dialog_text_commit:  //确认
                String requestCode = dialog_et_code.getText().toString().trim();
                if (requestCode.isEmpty()){
                    Toast.makeText(context,"输入不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (callback != null) {
                    callback.callbackCommit(requestCode);
                }
                dismiss();
                break;

            case R.id.dialog_cancel:
                dismiss();
                break;
        }

    }

    public interface RequestCodeDialogCallback {
        void callbackCode(View view);

        void callbackCommit(String requestCode);

    }

}
