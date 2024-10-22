package com.bc.bit.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bc.bit.MyContext;
import com.bc.bit.R;
import com.bc.bit.activity.CalendarDataActivity;
import com.bc.bit.activity.DynamicDetailActivity;
import com.bc.bit.activity.IndustryStormActivity;
import com.bc.bit.activity.LatestInfoActivity;
import com.bc.bit.activity.NewsListActivity;
import com.bc.bit.activity.SearchActivity;
import com.bc.bit.adapter.HomeHotNewsAdapter;
import com.bc.bit.adapter.RecMarketAdapter;
import com.bc.bit.api.Api;
import com.bc.bit.api.HttpObserver;
import com.bc.bit.bean.CurrencyBean;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.fragment.base.BaseCommonFragment;
import com.bc.bit.util.Constant;
import com.bc.bit.util.ImgUtil;
import com.bc.bit.util.LogUtil;
import com.bc.bit.view.CurrencyPopupDialog;
import com.bc.bit.view.ExchangeCurrencyPopupDialog;
import com.bc.bit.view.SpaceItemDecoration;
import com.bc.bit.view.XCollapsingToolbarLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scrb.klinechart.request.base.KBaseObjectBean;
import com.scrb.klinechart.request.base.KRequestManager;
import com.scrb.klinechart.request.base.KRequestSubscribe;
import com.scrb.klinechart.request.bean.KChartBean;
import com.scrb.klinechart.ui.activity.KChartDetailsActivity;
import com.sunfusheng.marqueeview.MarqueeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页
 */
public class HomePageFragment extends BaseCommonFragment implements XCollapsingToolbarLayout.OnScrimsListener {
    @BindView(R.id.home_page_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ctl_test_bar)
    XCollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.rec_market_rv)
    RecyclerView recMarketRv;
    @BindView(R.id.rec_news_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_currency_amount)
    EditText etCurrencyAmount;
    @BindView(R.id.currency_exchange_amount)
    TextView currencyExchangeAmount;
    @BindView(R.id.tv_currency_name)
    TextView tvCurrencyName;
    @BindView(R.id.tv_exchange_currency_name)
    TextView tvExchangeCurrencyName;
    @BindView(R.id.iv_currency_flag)
    RoundedImageView ivCurrencyFlag;
    @BindView(R.id.iv_exchange_currency_flag)
    RoundedImageView ivExchangeCurrencyFlag;
    @BindView(R.id.tv_base_currency_rate)
    TextView tvBaseCurrencyRate;
    @BindView(R.id.tv_base_exchange_currency_rate)
    TextView tvBaseExchangeCurrencyRate;
    private RecMarketAdapter recMarketAdapter;
    private HomeHotNewsAdapter mAdapter;
    private List<KChartBean> kChartList = new ArrayList<>();
    private List<TalkBean> newsList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 10;
    private CurrencyPopupDialog mCurrencyPopupDialog;
    private List<CurrencyBean> currencyBeanList;
    private static final String TAG = "HomePageFragment";
    private int currencyPosition = -1;
    private ExchangeCurrencyPopupDialog mExchangeCurrencyPopupDialog;
    private CurrencyBean.RatesBean rate;
    private DecimalFormat df = new DecimalFormat("#.##");

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, null);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.col_theme);
        mSwipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        GridLayoutManager layoutManager = new GridLayoutManager(getMainActivity(), 3);
        recMarketRv.setLayoutManager(layoutManager);
        recMarketRv.setNestedScrollingEnabled(false);
        recMarketRv.addItemDecoration(new SpaceItemDecoration(0, 20));
        recMarketAdapter = new RecMarketAdapter();
        recMarketRv.setAdapter(recMarketAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getMainActivity());
        mRecyclerView.setLayoutManager(layoutManager1);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new HomeHotNewsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        currencyBeanList = MyContext.context().getCurrencyBeanList();

        List<String> info = new ArrayList<>();
        info.add("灰度再次增持1701枚比特币和50357枚莱特币...");
        info.add("币安携手CertiK共同守护DeFi生态系统");
        marqueeView.startWithList(info);
        getMarketData();
        initRefreshData();
    }


    @Override
    protected void initListeners() {
        super.initListeners();
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                mSwipeRefreshLayout.setEnabled(true);
            } else {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });

        recMarketAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            if (kChartList != null && kChartList.size() > 0) {
                KChartDetailsActivity.startActivity(getMainActivity(),
                        kChartList.get(position).getSymbol(),
                        kChartList.get(position).getTicker(),
                        kChartList.get(position).getExchangeName());
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!checkLogin()) return;
            if (newsList != null && newsList.size() > 0) {
                startActivityExtraData(DynamicDetailActivity.class, newsList.get(position));
            }
        });


        etCurrencyAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String currencyAmount = readTxt(etCurrencyAmount);
                if (!TextUtils.isEmpty(currencyAmount)) {
                    if (rate == null) {
                        showTxt("请先选择货币");
                        etCurrencyAmount.setText("");
                        return;
                    }
                    try {
                        int currencyNum = Integer.parseInt(currencyAmount);
                        currencyExchangeAmount.setText((currencyNum * (rate.getRate())) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    currencyExchangeAmount.setText("");
                }

            }
        });


    }

    /**
     * 刷新数据
     */
    private void initRefreshData() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            kChartList.clear();
            newsList.clear();
            getMarketData();
        });
    }

    /**
     * 获取实时行情数据
     */
    private void getMarketData() {
        KRequestManager.getInstance().getCKApi.getMarketData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new KRequestSubscribe<List<KChartBean>>() {
                    @Override
                    protected void onRequestSuccess(List<KChartBean> response) {
                        getNewsList(pageNo + "", pageSize + "");
                        if (response != null && response.size() > 0) {
                            kChartList.addAll(response);
                            if (kChartList.size() > 3) {
                                recMarketAdapter.setList(kChartList.subList(0, 3));
                            } else {
                                recMarketAdapter.setList(kChartList);
                            }
                        }
                    }

                    @Override
                    protected void onRequestError(KBaseObjectBean objectBean) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    protected void onNetWorkError() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showTxt(getString(R.string.error_network_timeout));
                    }
                });

    }

    /**
     * 获取行业资讯数据
     *
     * @param pageno
     * @param pagesize
     */
    private void getNewsList(String pageno, String pagesize) {
        Api.getInstance().getTalkListByProject(Constant.PROJECT_NAME, pageno, pagesize).
                subscribe(new HttpObserver<DataResponse<ListMoreResponse<TalkBean>>>() {
                    @Override
                    public void onSucceeded(DataResponse<ListMoreResponse<TalkBean>> data) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (data.getData().getList() == null) return;
                        newsList.addAll(data.getData().getList());
                        // 设置新的数据方法
                        mAdapter.setList(newsList);
                        if (newsList.size() > 10) {
                            mAdapter.setList(newsList.subList(0, 10));
                        } else {
                            mAdapter.setList(newsList);
                        }
                    }

                    @Override
                    public boolean onFailed(DataResponse<ListMoreResponse<TalkBean>> data, Throwable e) {
//                        LoadingUtils.hideLoading();
                        mSwipeRefreshLayout.setRefreshing(false);
                        return super.onFailed(data, e);
                    }
                });
    }

    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        if (shown) {
            ImmersionBar.with(this).statusBarDarkFont(true).init();
        } else {
            ImmersionBar.with(this).statusBarDarkFont(false).init();
        }
    }

    @OnClick({R.id.layout_search, R.id.iv_news, R.id.layout_calender_data,
            R.id.layout_timely_info, R.id.layout_industry_storm,
            R.id.layout_currency, R.id.layout_exchange_currency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_search:
                if (!checkLogin()) return;
                startActivity(SearchActivity.class);
                break;
            case R.id.iv_news:
                startActivity(NewsListActivity.class);
                break;
            case R.id.layout_calender_data:
                startActivity(CalendarDataActivity.class);
                break;
            case R.id.layout_timely_info:
                startActivity(LatestInfoActivity.class);
                break;
            case R.id.layout_industry_storm:
                startActivity(IndustryStormActivity.class);
                break;
            case R.id.layout_currency:
                handleCurrency();
                break;
            case R.id.layout_exchange_currency:
                handleExchangeCurrency();
                break;

        }
    }

    private void handleCurrency() {
        if (mCurrencyPopupDialog == null) {
            mCurrencyPopupDialog = new CurrencyPopupDialog(getMainActivity(),
                    R.style.RequestCodeDialogStyle, currencyBeanList);
        }
        mCurrencyPopupDialog.show();
        mCurrencyPopupDialog.setOnItemClickCurrencyDialog((position) -> {
            currencyPosition = position;
            CurrencyBean currencyBean = currencyBeanList.get(position);
            tvCurrencyName.setText(currencyBean.getBaseSymbol());
            ivCurrencyFlag.setImageResource(ImgUtil.getImage(currencyBean.getCurrencyImage()));
            etCurrencyAmount.setText("");
            tvExchangeCurrencyName.setText("请选择");
            ivExchangeCurrencyFlag.setImageResource(R.drawable.flag_missing);
        });
    }


    private void handleExchangeCurrency() {
        if (currencyPosition == -1) {
            showTxt("请先选择需对比货币");
            return;
        }
        CurrencyBean currencyBean = currencyBeanList.get(currencyPosition);
        List<CurrencyBean.RatesBean> rates = currencyBean.getRates();
        mExchangeCurrencyPopupDialog = new ExchangeCurrencyPopupDialog(getMainActivity(),
                R.style.RequestCodeDialogStyle, rates);
        mExchangeCurrencyPopupDialog.show();
        mExchangeCurrencyPopupDialog.setOnItemClickExchangeCurrency(position -> {
            rate = rates.get(position);
            tvExchangeCurrencyName.setText(rate.getSymbol());
            ivExchangeCurrencyFlag.setImageResource(ImgUtil.getImage(rate.getCurrencyExchangeImage()));
            Double getDouble;
            try {
                double rateDb = 1 / rate.getRate();
                getDouble = Double.parseDouble(df.format(rateDb));
            } catch (Exception e) {
                getDouble = 0.0;
            }
            tvBaseCurrencyRate.setText("1 " + currencyBean.getBaseSymbol() + " = " + rate.getRate() + " " + rate.getSymbol());
            tvBaseExchangeCurrencyRate.setText("1 " + rate.getSymbol() + " = " + getDouble + " " + currencyBean.getBaseSymbol());
            etCurrencyAmount.setText("");
        });
    }
}
