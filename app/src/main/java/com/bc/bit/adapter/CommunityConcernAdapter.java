package com.bc.bit.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bc.bit.R;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.util.Constant;
import com.bc.bit.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CommunityConcernAdapter extends BaseQuickAdapter<TalkBean, BaseViewHolder> implements LoadMoreModule {
    private CommunityConcernListener communityConcernListener;
    public CommunityConcernAdapter(CommunityConcernListener communityConcernListener) {
        super(R.layout.layout_community_concern_item);
        this.communityConcernListener = communityConcernListener;
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull TalkBean item) {
        TextView item_community_concern_name =  helper.getView(R.id.item_community_concern_name);
        TextView item_community_concern_time =  helper.getView(R.id.item_community_concern_time);
        TextView item_community_concern_content =  helper.getView(R.id.item_community_concern_content);
        RoundedImageView item_community_image_header =  helper.getView(R.id.item_community_image_header);
        //获取列表控件
        RecyclerView rv = helper.getView(R.id.rv);

        item_community_concern_name.setText(item.getUser().getNickName());
        item_community_concern_time.setText(TimeUtil.commonFormat(item.getPublishTime()- Constant.TIME_DIFF));
        item_community_concern_content.setText(item.getContent());

        Glide.with(getContext()).load(item.getUser().getHead())
                .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                .error(R.drawable.pic_me_placeholder).into(item_community_image_header);

        List<String> picPaths = Arrays.asList(item.getPicture().split(","));
        if (picPaths != null && picPaths.size() > 0) {
            //有图片

            //显示图片列表控件
            rv.setVisibility(View.VISIBLE);

            //尺寸固定
            rv.setHasFixedSize(true);

            //禁用嵌套滚动
            rv.setNestedScrollingEnabled(false);

            //动态计算显示列数
            int spanCount = 1;
            if (picPaths.size() > 4) {
                //大于4张图片

                //显示3列
                spanCount = 2;
            } else if (picPaths.size() > 1) {
                //大于1张

                //显示2列
                spanCount = 2;
            }

            //设置布局管理器
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
            rv.setLayoutManager(layoutManager);

            //创建适配器
            ImageShowAdapter adapter = new ImageShowAdapter();

            //设置图片点击事件
            adapter.setOnItemClickListener(new OnItemClickListener() {
                /**
                 * 图片点击回调
                 *
                 * @param adapter
                 * @param view
                 * @param position
                 */
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //获取图片路径
                    //Java中有stream变换方法
                    //但是24及以上版本才能使用
                    //List<String> imageUris=data.getImages().stream().map
                    //回调监听器
                    communityConcernListener.onImageClick(rv, picPaths, position);
                }
            });

            //设置到控件
            rv.setAdapter(adapter);

            //设置数据
            adapter.setList(picPaths);

        } else {
            //隐藏控件
            rv.setVisibility(View.GONE);

            //清除适配器
            rv.setAdapter(null);
        }

    }

    public interface CommunityConcernListener {
        /**
         * 点击了动态图片回调
         *
         * @param rv
         * @param imageUris
         * @param index
         */
        void onImageClick(RecyclerView rv, List<String> imageUris, int index);
    }
}
