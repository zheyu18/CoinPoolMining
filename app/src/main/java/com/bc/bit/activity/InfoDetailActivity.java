package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.widget.SquareRelativeLayout;

import butterknife.BindView;

/**
 *  资讯详情
 */
public class InfoDetailActivity extends BaseTitleActivity {
    @BindView(R.id.tv_article_content)
    TextView tvArticleContent;
    @BindView(R.id.img_article)
    ImageView imgArticle;
    @BindView(R.id.layout_img_article)
    SquareRelativeLayout layoutImgArticle;
    private String mInfoContent,mPicUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
    }


    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.info_detail));
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        mInfoContent = getIntent().getStringExtra("content");
        mPicUrl = getIntent().getStringExtra("pic_url");

        if (!TextUtils.isEmpty(mInfoContent)) {
            tvArticleContent.setText(mInfoContent);
        }
        if (!TextUtils.isEmpty(mPicUrl)) {
            Glide.with(this).load(mPicUrl).into(imgArticle);
            layoutImgArticle.setVisibility(View.VISIBLE);
        }
    }
}
