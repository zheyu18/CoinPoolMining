package com.bc.bit.activity;

import android.os.Bundle;
import android.view.View;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.util.Constant;
import com.bc.bit.view.PopupDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.setting));
    }

    @OnClick({R.id.layout_setting_change_pwd, R.id.layout_setting_switch_account,
            R.id.layout_setting_delete_account, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_setting_change_pwd:
                if(!checkLogin())return;
                startActivity(FindPasswordActivity.class);
                break;
            case R.id.layout_setting_switch_account:
                if(!checkLogin())return;
                startActivity(SwitchAccountActivity.class);
                break;
            case R.id.layout_setting_delete_account:
                showDialog(Constant.TYPE_TWO);
                break;
            case R.id.tv_logout:
                showDialog(Constant.TYPE_ONE);
                break;
        }
    }


    private void showDialog(String type) {
        if (!checkLogin()) return;
        PopupDialog popupDialog = new PopupDialog(getMainActivity(), R.style.RequestCodeDialogStyle, type);
        popupDialog.setOnItemClickPopupDialog(() -> {
            if (Constant.TYPE_ONE.equals(type)) {
                signOut();
            }
        });
        popupDialog.show();
    }

    /**
     * 退出当前账号
     */
    private void signOut() {
        MyContext.context().goReLogin(getMainActivity());
    }
}
