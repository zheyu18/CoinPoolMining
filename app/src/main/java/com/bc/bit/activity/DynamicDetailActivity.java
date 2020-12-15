package com.bc.bit.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseTitleActivity;
import com.bc.bit.adapter.CommentAdapter;
import com.bc.bit.adapter.ImageShowAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.ArticleCommentBean;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.bean.UserBean;
import com.bc.bit.util.Constant;
import com.bc.bit.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.common.collect.Lists;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  动态详情
 */
public class DynamicDetailActivity extends BaseTitleActivity {
    @BindView(R.id.comment_rv)
    RecyclerView commentRv;
    @BindView(R.id.et_comment_content)
    EditText etCommentContent;
    private CommentAdapter mAdapter;
    private UserBean userBean;
    private TalkBean talkBean;
    private List<ArticleCommentBean> commentBeanList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 10;
    private boolean isHasMore;
    private boolean isConcerned;
    private TextView add_concerned, comment_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setTitleText(getString(R.string.dynamic_detail));
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
        talkBean = (TalkBean) getIntent().getSerializableExtra(Constant.DATA);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getMainActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getMainActivity(), R.drawable.comment_divider_inset));
        commentRv.addItemDecoration(itemDecoration);
        commentRv.setLayoutManager(layoutManager);
        mAdapter = new CommentAdapter();
        mAdapter.setHeaderWithEmptyEnable(true);
        mAdapter.addHeaderView(createHeaderView());
        commentRv.setAdapter(mAdapter);

        getConcernedState(talkBean.getUser().getId() + "");
        getDiscussByUserId(pageNo + "", pageSize + "");
    }

    /**
     * 创建头部
     *
     * @return
     */
    private View createHeaderView() {
        //从XML创建View
        View view = getLayoutInflater().inflate(R.layout.header_dynamic_detail, (ViewGroup) commentRv.getParent(), false);
        RoundedImageView imageHeader = view.findViewById(R.id.image_header);
        TextView articlePublishName = view.findViewById(R.id.article_publish_name);
        TextView articlePublishTime = view.findViewById(R.id.article_publish_time);
        TextView articlePublishContent = view.findViewById(R.id.article_publish_content);
        add_concerned = view.findViewById(R.id.add_concerned);
        RecyclerView rv = view.findViewById(R.id.rv);

        comment_count = view.findViewById(R.id.comment_count);

        articlePublishContent.setText(talkBean.getContent() + "");
        articlePublishName.setText(talkBean.getUser().getNickName());
        articlePublishTime.setText(TimeUtil.commonFormat(talkBean.getPublishTime()- Constant.TIME_DIFF));
        Glide.with(getMainActivity()).load(talkBean.getUser().getHead())
                .apply(new RequestOptions().placeholder(R.drawable.pic_me_placeholder))
                .error(R.drawable.pic_me_placeholder).into(imageHeader);
        showPiture(rv); //显示图片
        add_concerned.setOnClickListener(view1 -> goConcerned());

        return view;
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore());
    }

    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_view, commentRv, false);
        TextView tv_content = emptyView.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.empty_comment));
        return emptyView;
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        if (isHasMore) {
            getDiscussByUserId(pageNo + "", pageSize + "");
        } else {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }


    /**
     * 开始关注
     */
    private void goConcerned() {
        Api.getInstance().follow(userBean.getId() + "", talkBean.getUser().getId() + "", !isConcerned + "").
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
//                        getConcernedState(talkBean.getId() + "");
                        if (!isConcerned) {
                            showTxt(getString(R.string.toast_concerned_success));
                            add_concerned.setText(getString(R.string.followed));
                            add_concerned.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_concerned_border));
                        } else {
                            showTxt(getString(R.string.toast_concerned_cancel_success));
                            add_concerned.setText(getString(R.string.concern));
                            add_concerned.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_unconcerned_border));
                        }
                    }
                });
    }

    /**
     * 获取评论数据
     *
     * @param pageno
     * @param pagesize
     */
    private void getDiscussByUserId(String pageno, String pagesize) {
        Api.getInstance().getDiscussByUserId(talkBean.getUser().getId() + "", pageno, pagesize)
                .subscribe(new HttpObserver<DataResponse<ListMoreResponse<ArticleCommentBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<ArticleCommentBean>> data) {
                        if (data.getData().getList() == null) return;
                        commentBeanList.addAll(data.getData().getList());
                        comment_count.setText(String.format("评论(%d)", commentBeanList.size()));
                        if (commentBeanList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        isHasMore = data.getData().isHasMore();
                        pageNo = pageNo + 1;
                        mAdapter.setList(commentBeanList);
                        mAdapter.getLoadMoreModule().loadMoreComplete();
                    }

                    @Override
                    public boolean onFailed(DataResponse<ListMoreResponse<ArticleCommentBean>> data, Throwable e) {
                        mAdapter.getLoadMoreModule().loadMoreFail();
                        return true;
                    }
                });

    }

    /**
     * 获取用户是否关注
     *
     * @param followerId
     */
    private void getConcernedState(String followerId) {
        Api.getInstance().isFollow(userBean.getId() + "", followerId).
                subscribe(new HttpObserver<DetailResponse<Boolean>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Boolean> data) {
                        isConcerned = data.getData();
                        if (isConcerned) {
                            add_concerned.setText(getString(R.string.followed));
                            add_concerned.setBackground(getResources().getDrawable(R.drawable.bg_concerned_border));
                        } else {
                            add_concerned.setText(getString(R.string.concern));
                            add_concerned.setBackground(getResources().getDrawable(R.drawable.bg_unconcerned_border));
                        }
                    }
                });
    }


    /**
     * 显示大图
     *
     * @param rv
     */
    private void showPiture(RecyclerView rv) {
        List<String> picPaths = Arrays.asList(talkBean.getPicture().split(","));
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
            GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
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
                    ArrayList<String> imagesUris = Lists.newArrayList(picPaths);
                    //静态的方法要通过INSTANCE字段使用
                    PhotoViewer.INSTANCE
                            //设置图片数据
                            .setData(imagesUris)
                            //设置当前位置
                            .setCurrentPage(position)
                            //设置图片控件容器
                            //他需要容器的目的是
                            //显示缩放动画
                            .setImgContainer(rv)
                            //设置图片加载回调
                            .setShowImageViewInterface((imageView, url) -> {
                                //使用Glide显示图片
                                Glide.with(getMainActivity()).load(url).apply(new RequestOptions().placeholder(R.drawable.picture_image_placeholder)).into(imageView);
                            })
                            //启动界面
                            .start(getMainActivity());
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

    @OnClick({R.id.btn_commit_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit_comment:
                String commentStr = readTxt(etCommentContent);
                if (commentStr.isEmpty()) {
                    showTxt(getString(R.string.toast_comment_empty));
                    return;
                }
                if (commentStr.length() < 9) {
                    showTxt(getString(R.string.toast_comment_beyond_count));
                    return;
                }
                sendComment(commentStr);
                break;
        }
    }

    /**
     * 发送评论
     *
     * @param content
     */
    private void sendComment(String content) {
        Api.getInstance().commentTalk(userBean.getId() + "", talkBean.getId() + "", null, null, content).
                subscribe(new HttpObserver<BaseResponse>() {
                    @Override
                    public void onSucceeded(BaseResponse data) {
                        showTxt(getString(R.string.toast_comment_success));
                        etCommentContent.setText("");
                        commentBeanList.clear();
                        pageNo = 1;
                        getDiscussByUserId(pageNo + "", pageSize + "");
                    }
                });
    }

    @Override
    protected void initBlackBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .init();
    }
}
