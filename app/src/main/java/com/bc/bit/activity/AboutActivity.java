package com.bc.bit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  关于我们
 */
public class AboutActivity extends BaseTitleActivity {
    @BindView(R.id.tv_current_version)
    TextView tvCurrentVersion;
    @BindView(R.id.top_center_txt)
    TextView topCenterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initViews() {
        super.initViews();
        topCenterTxt.setText(getString(R.string.about_us));
        tvCurrentVersion.setText("当前版本v" + MyContext.context().version());
    }

    @OnClick({R.id.bnt_back,R.id.layout_version_update, R.id.layout_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnt_back:
                finish();
                break;
            case R.id.layout_version_update:
                showTxt(getString(R.string.latest_version));
                break;
            case R.id.layout_feedback:
                startActivity(FeedbackActivity.class);
                break;

        }
    }
}
