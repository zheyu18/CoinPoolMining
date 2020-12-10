package com.bc.bit.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.ImageSelectAdapter;
import com.bc.bit.util.GlideEngine;
import com.bc.bit.util.LogUtil;
import com.google.common.collect.Lists;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  帮助与反馈
 */
public class FeedbackActivity extends BaseTitleActivity {
    private static final String TAG = "FeedbackActivity";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;
    private ImageSelectAdapter adapter;
    private List<LocalMedia> datum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.feedback));
        //尺寸固定
        rv.setHasFixedSize(true);
        //禁止嵌套滚动
        rv.setNestedScrollingEnabled(false);
        //设置布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(getMainActivity(), 4);
        rv.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new ImageSelectAdapter(R.layout.item_image_select);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //设置适配器
        rv.setAdapter(adapter);
        //设置数据
        setData(new ArrayList<>());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //设置item点击事件
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Object data = adapter.getItem(position);
            if (data instanceof Integer) {
                //选择图片
                selectImage();
            }
        });
        adapter.addChildClickViewIds(R.id.iv_close);
        // 设置子控件点击监听
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_close) {
                //显示删除按钮
                adapter.remove(position);
            }
        });
    }

    /**
     * 设置数据
     *
     * @param datum
     */
    private void setData(List<Object> datum) {
        if (datum.size() != 4) {
            //添加选择图片按钮
            datum.add(R.drawable.pic_take_photo);
        }
        Collections.reverse(datum);
        adapter.setList(datum);
    }

    private void selectImage() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(3)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo()// 是否可预览视频 true or false
                //.enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .compress(true)// 是否压缩 true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                //.cropCompressQuality()// 裁剪压缩质量 默认90 int
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 回调方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //请求成功了
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //选择了媒体回调
                    //获取选择的资源
                    datum = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    LogUtil.d(TAG, "onActivityResult medias:" + datum.size());
                    //设置数据
                    setData(Lists.newArrayList(datum));
                    break;
            }
        }
    }

    @OnClick(R.id.feedback_commit)
    public void onViewClicked() {
        String feedBack = readTxt(etFeedbackContent);
        if (TextUtils.isEmpty(feedBack)) {
            showTxt(getString(R.string.toast_feedback_submit_empty));
            return;
        }
        showTxt(getString(R.string.toast_feedback_submit_success));
        finish();
    }
}
