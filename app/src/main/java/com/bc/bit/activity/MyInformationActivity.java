package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.api.ObserverAdapter;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.UserBean;
import com.bc.bit.event.MyinforChangeSuccessEvent;
import com.bc.bit.util.GlideEngine;
import com.bc.bit.util.LoadingUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *  个人信息
 */
public class MyInformationActivity extends BaseTitleActivity implements View.OnClickListener{
    private static final String TAG = "MyInformationActivity";
    @BindView(R.id.iv_me_header)
    RoundedImageView ivMeHeader;
    @BindView(R.id.et_info_nickname)
    EditText etInfoNickname;
    @BindView(R.id.et_info_signature)
    EditText etInfoSignature;
    private UserBean userBeans;
    private Dialog mDialogBottom;
    private View mInflate;
    private TextView mChoosePhoto;
    private TextView mTakePhotos, mBtnCancel;
    private String picPath;
    private String signatureStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
    }


    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.my_info));
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBeans = MyContext.context().getUser();
        Glide.with(getMainActivity()).load(userBeans.getHead()).apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                .error(R.drawable.pic_me_placeholder).into(ivMeHeader);
        if (!TextUtils.isEmpty(userBeans.getSignature())){
            etInfoSignature.setText(userBeans.getSignature());
        }
    }

    @OnClick({R.id.iv_me_header,R.id.tv_info_change_avatar, R.id.btn_save_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_me_header:
            case R.id.tv_info_change_avatar:
                showDialog();
                break;

            case R.id.btn_save_info:
                String nickName = readTxt(etInfoNickname);
                signatureStr = readTxt(etInfoSignature);
                if (nickName.isEmpty()) {
                    showTxt(getString(R.string.toast_nickname_empty));
                    return;
                }
                if (nickName.length() > 8) {
                    showTxt(getString(R.string.toast_nickname_number));
                    return;
                }

                LoadingUtils.showLoading(getMainActivity());
                upLoadPicture(picPath, nickName);
                break;
        }
    }

    public void showDialog() {
        mDialogBottom = new Dialog(this, R.style.BottomDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        //初始化控件
        mChoosePhoto = (TextView) mInflate.findViewById(R.id.choosePhoto);
        mTakePhotos = (TextView) mInflate.findViewById(R.id.takePhoto);
        mBtnCancel = (TextView) mInflate.findViewById(R.id.btn_cancel);
        mChoosePhoto.setOnClickListener(this);
        mTakePhotos.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialogBottom.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialogBottom.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 30;//设置Dialog距离底部的距离
        lp.dimAmount = 0.4f;
        dialogWindow.setAttributes(lp);
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//       将属性设置给窗体
        mDialogBottom.show();//显示对话框
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                takePhoto();
                mDialogBottom.dismiss();
                break;
            case R.id.choosePhoto:
                choosePhoto();
                mDialogBottom.dismiss();
                break;
            case R.id.btn_cancel:
                mDialogBottom.dismiss();
                break;
        }
    }

    /**
     *  拍照
     */
    private void takePhoto() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        showPortrait(result);
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }


    /**
     *  选择图库图片
     */
    private void choosePhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .maxSelectNum(1)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // 结果回调
                        showPortrait(result);
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }
                });
    }

    /**
     *  显示本地头像
     * @param result
     */
    private void showPortrait(List<LocalMedia> result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            picPath = result.get(0).getAndroidQToPath();
        } else {
            picPath = result.get(0).getPath();
        }
        Glide.with(getMainActivity())
                .load(picPath)
                .centerCrop()
                .into(ivMeHeader);
    }

    /**
     *  上传头像
     * @param path
     * @param nickName
     */
    private void upLoadPicture(String path, String nickName) {
        if (path != null) {
            File file = new File(path);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
            Api.getInstance().upLoad(photoPart).
                    subscribe(new ObserverAdapter<String>() {
                        @Override
                        public void onNext(String data) {
                            Glide.with(getMainActivity())
                                    .load(data)
                                    .centerCrop()
                                    .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                                    .error(R.drawable.pic_me_placeholder)
                                    .into(ivMeHeader);
                            updataUserInfor(data, nickName);
                        }

                        @Override
                        public void onError(Throwable e) {
                            showTxt(getString(R.string.error_network_server));
                        }
                    });

        } else {
            updataUserInfor(null, nickName);
        }

    }


    /**
     *  更新用户信息
     * @param path
     * @param nickName
     */
    private void updataUserInfor(String path, String nickName) {
        UserBean userBean = new UserBean();
        userBean.setId(userBeans.getId());
        if (path != null) {
            userBean.setHead(path);
        }
        userBean.setNickName(nickName);
        if (signatureStr != null) {
            userBean.setSignature(signatureStr);
        }
        Api.getInstance().updateUser(userBean).
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        if (data.isSuccess()) {
                            LoadingUtils.hideLoading();
                            showTxt(getString(R.string.change_infor_success));
                            EventBus.getDefault().post(new MyinforChangeSuccessEvent());
                            finish();
                        }
                    }
                });
    }
}
