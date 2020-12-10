package com.bc.bit.adapter;

import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bc.bit.R;
import com.bc.bit.util.ImgUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;



/**
 * 显示选择图片后适配器
 */
public class ImageSelectAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private String picPath;
    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ImageSelectAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object data) {
        //获取图片控件
        ImageView iv_banner = helper.getView(R.id.iv_banner);

        if (data instanceof LocalMedia) {
            //选择的图片
            //显示图片
//            ImageUtil.showLocalImage(getContext(), iv_banner, ((LocalMedia) data).getCompressPath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                picPath =((LocalMedia) data).getAndroidQToPath();
            } else {
                picPath = ((LocalMedia) data).getPath();
            }
            Glide.with(getContext())
                    .load(picPath)
                    .override(250, 250)
                    .centerCrop()
                    .apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder))
                    .into(iv_banner);
            //显示删除按钮
            helper.setVisible(R.id.iv_close, true);
        } else {
            //显示图片
            ImgUtil.showLocalImage(getContext(), iv_banner, (Integer) data);
            //隐藏删除按钮
            helper.setVisible(R.id.iv_close, false);
        }

    }
}
