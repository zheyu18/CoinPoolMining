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
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.LoginSuccessEvent;
import com.bc.bit.util.Constant;
import com.bc.bit.util.ImgUtil;
import com.bc.bit.view.RequestCodeDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  注册
 */
public class RegisterActivity extends BaseCommonActivity {
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_request_code)
    EditText etRegisterRequestCode;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.tv_register_get_code)
    TextView tvRegisterGetCode;
    private String mPhone;
    private String mNumberCode;
    private RequestCodeDialog mRequestCodeDialog;
    private String imageCode;
    private long startTime;
    final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    @OnClick({R.id.iv_back, R.id.tv_register_get_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_register_get_code:
                mPhone = readTxt(etRegisterPhone);
                if (mPhone.isEmpty() || mPhone.length() < 11 || !mPhone.substring(0, 1).equals(Constant.TYPE_ONE)) {
                    showTxt(getString(R.string.toast_correct_pwd));
                    return;
                }
                showRequestCodeDialog(mPhone);
                break;
            case R.id.btn_register:
                mPhone = readTxt(etRegisterPhone);
                mNumberCode = readTxt(etRegisterRequestCode);

                if (mPhone.isEmpty() || mPhone.length() < 11 || !mPhone.substring(0, 1).equals(Constant.TYPE_ONE)) {
                    showTxt(getString(R.string.toast_correct_pwd));
                    return;
                }
                if (mNumberCode.isEmpty()) {
                    showTxt(getString(R.string.toast_number_code));
                    return;
                }

                String pwd = readTxt(etRegisterPwd);
                if (pwd.length() < 6) {

                    showTxt(getString(R.string.toast_valid_password));
                    return;
                }
                registerUser(mPhone, mNumberCode, pwd);
                break;

        }
    }

    /**
     * 注册
     *
     * @param phone
     * @param numberCode
     * @param pwd
     */
    private void registerUser(String phone, String numberCode, String pwd) {
        Api.getInstance().register(phone, pwd, pwd, numberCode, Constant.TYPE_ONE, Constant.PROJECT_NAME).
                subscribe(new HttpObserver<DetailResponse<UserBean>>(getMainActivity(), true) {
                    @Override
                    public void onSucceeded(DetailResponse<UserBean> data) {
                        goLogin(phone, pwd);
                    }
                });
    }

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     */
    private void goLogin(String phone, String pwd) {
        Api.getInstance().login(phone, pwd, Constant.TYPE_ONE, Constant.PROJECT_NAME)
                .subscribe(new HttpObserver<DetailResponse<UserBean>>() {
                    @Override
                    public void onSucceeded(DetailResponse<UserBean> data) {
                        MyContext.context().save(data.getData());
                        finish();
                        showTxt(getString(R.string.register_success));
                        EventBus.getDefault().post(new LoginSuccessEvent());
                    }
                });
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
                    sendPhoneCode(phone, Constant.TYPE_ONE, Constant.PROJECT_NAME_CODE, requestCode);
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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis() - startTime;
            if (time >= TIMEOUT) {
                tvRegisterGetCode.setClickable(true);
                tvRegisterGetCode.setText(getString(R.string.get_request_code));
            } else {
                tvRegisterGetCode.setClickable(false);
                int m = (int) ((TIMEOUT - time) / 1000);
                tvRegisterGetCode.setText("已发送（" + (m > 9 ? String.valueOf(m) : "0" + m) + "s）");
                mHandler.postDelayed(runnable, 1000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
