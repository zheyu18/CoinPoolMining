package com.bc.bit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.base.BaseCommonActivity;
import com.bc.bit.adapter.SearchAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.bean.TalkParamsBean;
import com.bc.bit.bean.UserBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.collect.Lists;
import com.gyf.immersionbar.ImmersionBar;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  搜索
 */
public class SearchActivity extends BaseCommonActivity implements SearchAdapter.SearchListener{
    private static final String TAG = "SearchActivity";
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.search_swipe_rv)
    RecyclerView mRecyclerView;
    private int pageNo = 1;
    private int pageSize = 10;
    private UserBean userBean;
    private SearchAdapter mAdapter;
    private List<TalkBean> searchList = new ArrayList<>();
    private boolean isHasMore;
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new SearchAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userBean = MyContext.context().getUser();
    }

    @Override
    protected void initListeners() {
        // 设置加载更多监听事件
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> loadMore());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (searchList != null && searchList.size() > 0) {
                startActivityExtraData(DynamicDetailActivity.class, searchList.get(position));
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                searchContent = readTxt(etSearch);
                if (TextUtils.isEmpty(searchContent)) {
                    showTxt(getString(R.string.toast_search_empty));
                    return;
                }
                searchList.clear();
                searchData(searchContent, pageNo + "", pageSize + "");
                break;
        }
    }

    /**
     * 加载 更多
     */
    private void loadMore() {
        if (isHasMore) {
            searchData(searchContent, pageNo + "", pageSize + "");
        } else {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    /**
     *  获取搜索数据
     * @param searchContent
     * @param pageno
     * @param pagesize
     */
    private void searchData(String searchContent, String pageno, String pagesize) {
        TalkParamsBean talkParamsBean = new TalkParamsBean();
        talkParamsBean.setPageNumber(pageno);
        talkParamsBean.setPageSize(pagesize);
        talkParamsBean.setContent(searchContent);
        Api.getInstance().getTalkList(userBean.getId() + "", talkParamsBean).
                subscribe(new HttpObserver<DataResponse<ListMoreResponse<TalkBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<TalkBean>> data) {
                        searchList.addAll(data.getData().getList());
                        if (searchList.size() == 0) {
                            mAdapter.setEmptyView(getEmptyView());
                        }
                        isHasMore = data.getData().isHasMore();
                        pageNo = pageNo + 1;
                        mAdapter.setList(searchList);
                        mAdapter.getLoadMoreModule().loadMoreComplete();
                    }

                    @Override
                    public boolean onFailed(DataResponse<ListMoreResponse<TalkBean>> data, Throwable e) {
                        mAdapter.setEmptyView(getErrorView());
                        return super.onFailed(data, e);
                    }
                });
    }


    /**
     *  数据为空占位图
     * @return
     */
    private View getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_view, mRecyclerView, false);
        TextView tv_content = emptyView.findViewById(R.id.tv_content);
        tv_content.setText(getString(R.string.empty_data));
        return emptyView;
    }


    /**
     * 加载错误占位图
     *
     * @return
     */
    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener((View v) -> {
            searchList.clear();
            pageNo = 1;
            searchData(searchContent, pageNo + "", pageSize + "");
        });
        return errorView;
    }


    @Override
    public void onImageClick(RecyclerView rv, List<String> images, int index) {
        ArrayList<String> imagesUris = Lists.newArrayList(images);
        //静态的方法要通过INSTANCE字段使用
        PhotoViewer.INSTANCE
                //设置图片数据
                .setData(imagesUris)
                //设置当前位置
                .setCurrentPage(index)
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
                .start(this);
    }
}
