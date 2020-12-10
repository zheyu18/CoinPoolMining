package com.bc.bit.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.bean.UserBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  切换账号
 */
public class SwitchAccountActivity extends BaseTitleActivity {
    @BindView(R.id.switch_account_image_header)
    RoundedImageView switchAccountImageHeader;
    @BindView(R.id.switch_account_name)
    TextView switchAccountName;
    private UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.switch_account));
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
        Glide.with(getMainActivity()).load(userBean.getHead())
                .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                .error(R.drawable.pic_me_placeholder).into(switchAccountImageHeader);
        switchAccountName.setText(userBean.getNickName());
    }

    @OnClick({R.id.layout_switch_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_switch_account:
                MyContext.context().goReLogin(getMainActivity());
                break;
        }
    }
}
