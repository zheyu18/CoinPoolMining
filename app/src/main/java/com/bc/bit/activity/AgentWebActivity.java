package com.bc.bit.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseActivity;
import com.just.agentweb.AgentWeb;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 *  第三方web
 */
public class AgentWebActivity extends BaseActivity {
    private static final String TAG = "AgentWebActivity";
    @BindView(R.id.agent_web)
    LinearLayout mWebViewBox;
    private String openUrl;
    private String mPool; // 备用地址
    private AgentWeb mAgentWeb;
    private WebView mWebView;
    private List<String> dPool = new ArrayList<>();
    private int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_web);
    }
    @Override
    protected void initViews() {
        super.initViews();
        openUrl = getIntent().getStringExtra("url");
        mPool = getIntent().getStringExtra("dPool");
        String str = StringUtils.strip(mPool, "[]");
        dPool = Arrays.asList(str.split(","));
        loadUrl(openUrl);
    }

    private void loadUrl(String url) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebViewBox, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(getResources().getColor(R.color.col_theme))
                .createAgentWeb()
                .ready()
                .go(url);
        mWebView = mAgentWeb.getWebCreator().getWebView();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (k < dPool.size()) {
                    mAgentWeb.getUrlLoader().loadUrl(dPool.get(k));
                    k = k + 1;
                }
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                if (k < dPool.size()) {
                    mAgentWeb.getUrlLoader().loadUrl(dPool.get(k));
                    k = k + 1;
                }
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }
}
