package com.bc.bit.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.util.Constant;

import butterknife.BindView;

/**
 *  用户隐私协议相关
 */
public class ProtocolActivity extends BaseTitleActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
    }

    @Override
    protected void initViews() {
        super.initViews();
        state = getIntent().getStringExtra(Constant.ID);
        if (Constant.PRIVATE_POLICY.equals(state)) {
            setTitleText(getString(R.string.privacy_policy));
            tvContent.setText(getString(R.string.privacy_policy_txt));
        } else if (Constant.USER_AGREEMENT.equals(state)) {
            setTitleText(getString(R.string.user_agreement));
            tvContent.setText(getString(R.string.user_agreement_txt));
        }
    }
}
