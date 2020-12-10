package com.bc.bit.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.ImageSelectAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.util.GlideEngine;
import com.bc.bit.util.LoadingUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *  发布说说
 */
public class UserPublishActivity extends BaseTitleActivity {

    private static final String TAG = "UserPublishActivity";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.et_talk)
    EditText etTalk;
    private ImageSelectAdapter adapter;
    private UserBean userBean;
    private List<String> picPaths = new ArrayList<>();//上传成功返回图片路径
    private List<LocalMedia> albumPictureList = new ArrayList<>();//相册照片
    private List<LocalMedia> takePictureList = new ArrayList<>();//拍照照片
    private List<LocalMedia> localAllPictures = new ArrayList<>();// 总共的照片
    private boolean isTakePhoto = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_publish);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.publish_dynamic));
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
        userBean = MyContext.context().getUser();
        //设置适配器
        rv.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        setRightText(getString(R.string.publish), v -> publishTalkAbout());
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
     * 发布说说
     */
    private void publishTalkAbout() {
        String content = readTxt(etTalk);
        if (content.isEmpty()) {
            showTxt(getString(R.string.toast_talk_about_empty));
            return;
        }
        if (content.length() < 36) {
            showTxt(getString(R.string.toast_talk_about_beyond_count));
            return;
        }
        LoadingUtils.showLoading(getMainActivity());
        upLoadPicture(content);  // 开始上传图片
    }

    @OnClick({R.id.take_photo_album, R.id.take_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_photo_album:
                selectImage();
                isTakePhoto = false;
                break;
            case R.id.take_photo:
                if (!isTakePhoto) {
                    localAllPictures.clear();
                }
                takePhoto();
                isTakePhoto = true;
                break;
        }
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
                    takePictureList.clear();
                    albumPictureList.clear();
                    localAllPictures.clear();
                    adapter.notifyDataSetChanged();
                    albumPictureList.addAll(PictureSelector.obtainMultipleResult(data));
                    adapter.setList(albumPictureList);
                    localAllPictures.addAll(albumPictureList);
                    break;
            }
        }
    }

    private void upLoadPicture(String content) {
        if (localAllPictures == null || localAllPictures.size() <= 0) {
            publishTalk(content, "");
            return;
        }
        if (localAllPictures.size() > 3) {
            showTxt(getString(R.string.toast_beyond_count));
            return;
        }
        for (int i = 0; i < localAllPictures.size(); i++) {
            File file = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                file = new File(localAllPictures.get(i).getAndroidQToPath());
            } else {
                file = new File(localAllPictures.get(i).getPath());
            }
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
            Api.getInstance().upLoad(photoPart).
                    subscribe(new ObserverAdapter<String>() {
                        @Override
                        public void onNext(String data) {
                            picPaths.add(data);
                            if (picPaths.size() == localAllPictures.size()) {
                                StringBuffer sb = new StringBuffer();
                                for (String str : picPaths) {
                                    sb.append(str).append(",");
                                }
                                String picStr = sb.deleteCharAt(sb.length() - 1).toString();
                                publishTalk(content, picStr);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showTxt(getString(R.string.error_network_unknown_host));
                            LoadingUtils.hideLoading();
                        }
                    });
        }
    }

    private void publishTalk(String content, String picture) {
        Api.getInstance().publishTalk(userBean.getId() + "", content, picture, "", "").
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.toast_publish_success));
                        LoadingUtils.hideLoading();
                        etTalk.setText("");
                        finish();
                    }
                });
    }


    /**
     * 从本地相册获取图片
     */
    private void takePhoto() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        if (result != null) {
                            albumPictureList.clear();
                            adapter.notifyDataSetChanged();
                            takePictureList = result;
                            localAllPictures.addAll(takePictureList);
                            adapter.setList(localAllPictures);
                        }
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }

    @Override
    protected void initBar() {
        super.initBar();
        ImmersionBar.with(this)
                .transparentStatusBar()
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .init();

    }
}
