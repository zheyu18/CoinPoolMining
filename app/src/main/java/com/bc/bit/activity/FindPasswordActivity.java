package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.util.Constant;
import com.bc.bit.util.ImgUtil;
import com.bc.bit.view.RequestCodeDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  找回密码
 */
public class FindPasswordActivity extends BaseCommonActivity {
    private static final String TAG = "FindPasswordActivity";
    @BindView(R.id.et_find_pwd_phone)
    EditText etFindPwdPhone;
    @BindView(R.id.et_find_pwd_request_code)
    EditText etFindPwdRequestCode;
    @BindView(R.id.tv_find_pwd_get_code)
    TextView tvFindPwdGetCode;
    @BindView(R.id.et_find_pwd)
    EditText etFindPwd;
    @BindView(R.id.et_find_pwd_confirm)
    EditText etFindPwdConfirm;
    private String phone, numberCode, pwd, imageCode, confirmPwd;
    final Handler mHandler = new Handler();
    private long startTime;
    private RequestCodeDialog mRequestCodeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initWhiteBar();
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        getImageCode(null);
    }

    @OnClick({R.id.iv_back, R.id.tv_find_pwd_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_find_pwd_get_code:
                tvFindPwdGetCode.setClickable(true);
                phone = readTxt(etFindPwdPhone);
                if (phone.isEmpty() || phone.length() < 11 || !phone.substring(0, 1).equals(Constant.TYPE_ONE)) {
                    showTxt(getString(R.string.toast_correct_pwd));
                    return;
                }
                showRequestCodeDialog(phone);
                break;
            case R.id.btn_login:
                phone = readTxt(etFindPwdPhone);
                numberCode = readTxt(etFindPwdRequestCode);
                pwd = readTxt(etFindPwd);
                confirmPwd = readTxt(etFindPwdConfirm);
                if (phone.isEmpty() || phone.length() < 11 || !phone.substring(0, 1).equals(Constant.TYPE_ONE)) {
                    showTxt(getString(R.string.toast_correct_pwd));
                    return;
                }
                if (numberCode.isEmpty()) {
                    showTxt(getString(R.string.toast_number_code));
                    return;
                }

                if (pwd.length() < 6) {
                    showTxt(getString(R.string.toast_valid_password));
                    return;
                }
                if (!confirmPwd.equals(pwd)) {
                    showTxt(getString(R.string.inconsistent));
                    return;
                }

                resetPassword(phone, numberCode, pwd, confirmPwd);
                break;

        }
    }

    private void showRequestCodeDialog(final String phone) {
        if (imageCode != null) {
            mRequestCodeDialog = new RequestCodeDialog(this, R.style.RequestCodeDialogStyle, imageCode);
            mRequestCodeDialog.setRequestCodeDialog(new RequestCodeDialog.RequestCodeDialogCallback() {
                @Override
                public void callbackCode(View view) {  //获取验证码
                    ImageView imageView = (ImageView) view;
                    getImageCode(imageView); // 获取图形验证码
                }

                @Override
                public void callbackCommit(String requestCode) { //获取数字验证码
                    sendPhoneCode(phone, Constant.TYPE_TWO, Constant.PROJECT_NAME_CODE, requestCode);
                }
            });
            mRequestCodeDialog.show();
        }
    }

    /**
     * 获取手机验证码
     *
     * @param phone
     * @param type
     * @param project
     * @param code
     */
    private void sendPhoneCode(String phone, String type, String project, String code) {
        Api.getInstance().sendPhoneCode(phone, type, project, code)
                .subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        getImageCode(null);
                        showTxt(getString(R.string.toast_request_code));
                        startTime = System.currentTimeMillis();
                        mHandler.post(runnable);
                    }
                });
    }

    /**
     * 获取图形验证码
     *
     * @param imageView
     */
    private void getImageCode(final ImageView imageView) {
        Api.getInstance().getSendVerify()
                .subscribe(new HttpObserver<DetailResponse<String>>() {
                    @Override
                    public void onSucceeded(DetailResponse<String> data) {
                        imageCode = data.getData();
                        if (imageView != null) {
                            imageView.setImageBitmap(ImgUtil.base64ToBitmap(data.getData()));
                        }
                    }
                });
    }

    /**
     * 倒计时
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis() - startTime;
            if (time >= TIMEOUT) {
                tvFindPwdGetCode.setClickable(true);
                tvFindPwdGetCode.setText(getString(R.string.get_request_code));
            } else {
                tvFindPwdGetCode.setClickable(false);
                int m = (int) ((TIMEOUT - time) / 1000);
                tvFindPwdGetCode.setText("已发送（" + (m > 9 ? String.valueOf(m) : "0" + m) + "s）");
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };

    /**
     * 修改密码
     *
     * @param phone
     * @param numberCode
     * @param pwd
     * @param comfirmPwd
     */
    private void resetPassword(String phone, String numberCode, String pwd, String comfirmPwd) {
        Api.getInstance().resetPassword(phone, pwd, comfirmPwd, numberCode, Constant.PROJECT_NAME).
                subscribe(new HttpObserver<BaseResponse>(getMainActivity(), true) {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.reset_pwd_success));
                        MyContext.context().goReLogin(getMainActivity());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
