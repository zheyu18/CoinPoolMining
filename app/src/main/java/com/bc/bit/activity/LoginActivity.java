package com.bc.bit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bc.bit.MainActivity;
import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.LoginSuccessEvent;
import com.bc.bit.util.ActivityCollector;
import com.bc.bit.util.Constant;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/***
 *  登录
 */
public class LoginActivity extends BaseCommonActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    private String phone, pwd;
    private String logout;

    public static void navigation(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.anim_in, R.anim.anim_exit2);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        super.initViews();
        initWhiteBar();
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        logout = extraId();
    }

    @OnClick({R.id.tv_find_password,R.id.btn_login, R.id.tv_register,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_find_password:
                startActivity(FindPasswordActivity.class);
                break;

            case R.id.btn_login:
                phone = readTxt(etLoginPhone);
                pwd = readTxt(etLoginPwd);
                if (phone.isEmpty() || phone.length() < 11 || !phone.substring(0, 1).equals(Constant.TYPE_ONE)) {
                    showTxt(getString(R.string.toast_correct_pwd));
                    return;
                }
                if (pwd.length() < 6) {
                    showTxt(getString(R.string.toast_valid_password));
                    return;
                }
                goLogin(phone, pwd);
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     *  登录
     * @param phone
     * @param pwd
     */
    private void goLogin(String phone, String pwd) {
        Api.getInstance().login(phone, pwd, Constant.TYPE_ONE, Constant.PROJECT_NAME)
                .subscribe(new HttpObserver<DetailResponse<UserBean>>(getMainActivity(), true) {
                    @Override
                    public void onSucceeded(DetailResponse<UserBean> data) {
                        MyContext.context().save(data.getData());
                        EventBus.getDefault().post(new LoginSuccessEvent());
                        if (TextUtils.isEmpty(logout)) {
                            finish();
                        } else {
                            startActivity(MainActivity.class);
                            ActivityCollector.finishAll();
                        }
                    }
                });
    }
}
