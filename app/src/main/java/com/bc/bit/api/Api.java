package com.bc.bit.api;


import com.bc.bit.MyContext;
import com.bc.bit.bean.ArticleCommentBean;
import com.bc.bit.bean.ArticleNewsBean;
import com.bc.bit.bean.BannerBean;
import com.bc.bit.bean.BaseResponse;
import com.bc.bit.bean.BaseUrlConfigBean;
import com.bc.bit.bean.CalenderDataBean;
import com.bc.bit.bean.CountryImageBean;
import com.bc.bit.bean.DataResponse;
import com.bc.bit.bean.DetailResponse;
import com.bc.bit.bean.ExpressNewsBean;
import com.bc.bit.bean.ExpressNewsListBean;
import com.bc.bit.bean.IndustryStormBean;
import com.bc.bit.bean.ListMoreResponse;
import com.bc.bit.bean.ListResponse;
import com.bc.bit.bean.MarketBean;
import com.bc.bit.bean.NewsBean;
import com.bc.bit.bean.NewsListResponse;
import com.bc.bit.bean.SignInBean;
import com.bc.bit.bean.StockNewsBean;
import com.bc.bit.bean.TalkBean;
import com.bc.bit.bean.TalkParamsBean;
import com.bc.bit.bean.UserBean;
import com.bc.bit.util.Constant;
import com.bc.bit.util.SystemUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api {

    private static final String TAG = "Api";

    /**
     * 实例
     */
    private static Api instance;

    /**
     * Service单例
     */
    private final Service service;

    private String baseUrl = "http://39.98.245.21:8080/";

    /**
     * 返回当前对象的唯一实例
     *
     * @return
     */
    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    public Api() {
        //初始化okhttp
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS) //连接超时时间
                .writeTimeout(30, TimeUnit.SECONDS) //写，也就是将数据发送到服务端超时时间
                .readTimeout(30, TimeUnit.SECONDS); //读，将服务端的数据下载到本地

        //公共请求参数
        okhttpClientBuilder.addNetworkInterceptor(chain -> {
            //获取到request
            Request request = chain.request();
            //继续执行网络请求
            return chain.proceed(request);
        });

        BaseUrlConfigBean baseUrlConfig = MyContext.context().getBaseUrlConfig();
        if (baseUrlConfig != null) {
            baseUrl = baseUrlConfig.getData().getUrl();
        }
        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //让retrofit使用okhttp
                .client(okhttpClientBuilder.build())
                //api地址
                .baseUrl(baseUrl)
                //适配rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //字符串
                .addConverterFactory(ScalarsConverterFactory.create())
                //使用gson解析json
                //包括请求参数和响应
                .addConverterFactory(GsonConverterFactory.create())
                //创建retrofit
                .build();


        //创建service
        service = retrofit.create(Service.class);
    }

    /**
     * 获取推图形验证码
     *
     * @return
     */
    public Observable<DetailResponse<String>> getSendVerify() {
        return service.sendVerify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取手机验证码
     *
     * @param phone
     * @param type
     * @param project
     * @param code
     * @return
     */
    public Observable<BaseResponse> sendPhoneCode(String phone, String type, String project, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("type", type);
        params.put("project", project);
        params.put("code", code);
        return service.sendPhoneCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param confirmPassword
     * @param code
     * @param type
     * @param project
     * @return
     */
    public Observable<DetailResponse<UserBean>> register(String phone, String password, String confirmPassword,
                                                         String code, String type, String project) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        params.put("confirmPassword", confirmPassword);
        params.put("code", code);
        params.put("type", type);
        params.put("project", project);
        return service.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 重置密码
     *
     * @param phone
     * @param newPassword
     * @param confirmPassword
     * @param code
     * @param project
     * @return
     */

    public Observable<BaseResponse> resetPassword(String phone, String newPassword, String confirmPassword,
                                                  String code, String project) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("newPassword", newPassword);
        params.put("confirmPassword", confirmPassword);
        params.put("code", code);
        params.put("project", project);
        return service.resetPassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * @param num
     * @param page
     * @return
     */
    public Observable<DataResponse<NewsListResponse<NewsBean>>> getBlockChainNews(String num, String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("num", page);
        params.put("page", num);
        return service.getBlockChainNews(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取推荐用户列表
     *
     * @return
     */
    public Observable<ListResponse<UserBean>> getRecommandUserList(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.getRecommandUserList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @param type
     * @param project
     * @return
     */
    public Observable<DetailResponse<UserBean>> login(String phone, String password, String type, String project) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);
        params.put("type", type);
        params.put("project", project);
        return service.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    public Observable<DetailResponse<UserBean>> queryUser(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.queryUser(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取推荐说说列表
     *
     * @param project
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<TalkBean>>> getRecommandTalk(String project, String userId, String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("project", project);
        params.put("userId", userId);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getRecommandTalk(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询说说列表,查询条件为JSON字符串,格式如下:
     * <p>
     * {
     * {field}:{value},字段查询
     * {field}Start:{value},添加大于等于查询
     * {field}End:{value},添加小于等于查询
     * {field}IN:[array],添加IN查询
     * {field}NOTNULL:{value},添加not null查询
     * {field}NULL:{value},添加null查询
     * _orderBy:{value},升序排列
     * _orderByDesc:{value},降序排列
     * _pageNumber:{value},页码
     * _pageSize:{value},每页个数
     * }
     * 降序排列 _orderByDesc: publishTime(发表时间) zanCount(点赞数) commentCount(评论数) _pageNumber参数如果不传的话,默认为1 _pageSize参数如果不传的话,默认为10
     *
     * @param userId
     * @param talkParamsBean
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<TalkBean>>> getRecommandTalk(String userId, TalkParamsBean talkParamsBean) {
        return service.getRecommandTalk(userId, talkParamsBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取其他用户对该用户发表的说说的评论操作
     *
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<ArticleCommentBean>>> getDiscussByUserId(String userId, String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getDiscussByUserId(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 是否关注该用户
     *
     * @param userId
     * @param followerId
     * @return
     */
    public Observable<DetailResponse<Boolean>> isFollow(String userId, String followerId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("followerId", followerId);
        return service.isFollow(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 关注用户
     *
     * @param userId
     * @param followerId
     * @param isFollow
     * @return
     */
    public Observable<BaseResponse> follow(String userId, String followerId, String isFollow) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("followerId", followerId);
        params.put("isFollow", isFollow);
        return service.follow(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 分页获取财经说说(默认第一页 ， 每页10条)
     *
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Observable<ListResponse<ExpressNewsBean>> getFinanceTalk(String pageNum, String pageSize, String date) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("date", date);
        return service.getFinanceTalk(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取Banner列表
     *
     * @param project
     * @return
     */
    public Observable<ListResponse<BannerBean>> getBannerList(String project) {
        HashMap<String, String> params = new HashMap<>();
        params.put("project", project);
        return service.getBannerList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 上传图片
     *
     * @return
     */
    public Observable<String> upLoad(MultipartBody.Part photoPart) {
        return service.upLoad(photoPart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 更新用户信息
     *
     * @param userBean
     * @return
     */
    public Observable<BaseResponse> updateUser(UserBean userBean) {
        return service.updateUser(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取用户关注/粉丝列表
     *
     * @param userId
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<UserBean>>> getUserFollowList(String userId, String type, String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getUserFollowList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 根据项目名获取说说列表
     *
     * @param project
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<TalkBean>>> getTalkListByProject(String project, String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("project", project);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getTalkListByProject(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询说说列表,查询条件为JSON字符串,格式如下:
     *
     * {
     * {field}:{value},字段查询
     * {field}Start:{value},添加大于等于查询
     * {field}End:{value},添加小于等于查询
     * {field}IN:[array],添加IN查询
     * {field}NOTNULL:{value},添加not null查询
     * {field}NULL:{value},添加null查询
     * _orderBy:{value},升序排列
     * _orderByDesc:{value},降序排列
     * _pageNumber:{value},页码
     * _pageSize:{value},每页个数
     * }
     * 降序排列 _orderByDesc: publishTime(发表时间) zanCount(点赞数) commentCount(评论数)
     * wo su _pageNumber参数如果不传的话,默认为1 _pageSize参数如果不传的话,默认为10
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<TalkBean>>> getTalkList(String userId, TalkParamsBean talkParamsBean) {
        return service.getTalkList(userId, talkParamsBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 发布说说
     *
     * @param userId
     * @param content
     * @param picture
     * @param displayBig
     * @param videoId
     * @return
     */
    public Observable<BaseResponse> publishTalk(String userId, String content, String picture, String displayBig, String videoId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("content", content);
        params.put("picture", picture);
        params.put("displayBig", displayBig);
        params.put("videoId", videoId);
        return service.publishTalk(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 评论说说
     *
     * @param userId
     * @param talkId
     * @param videoId
     * @param matchId
     * @param content
     * @return
     */
    public Observable<BaseResponse> commentTalk(String userId, String talkId, String videoId, String matchId, String content) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("talkId", talkId);
        if (videoId != null) {
            params.put("videoId", videoId);
        }
        if (matchId != null) {
            params.put("matchId", matchId);
        }
        params.put("content", content);
        return service.commentTalk(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户浏览,点赞,转发操作
     *
     * @param userId
     * @param talkId  说说id @RequestParam(name="talkId")
     * @param type    类型(1-浏览 2-点赞 3-转发) @RequestParam(name="type")
     * @param content 转发说说时的评论 @RequestParam(name="content", required=false)
     * @return
     */
    public Observable<BaseResponse> userTalkOperation(String userId, String talkId, String type, String content) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("talkId", talkId);
        params.put("type", type);
        if (content != null) {
            params.put("content", content);
        }
        return service.userTalkOperation(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param userId
     * @param talkId
     * @param type       类型(1-浏览 2-点赞 3-转发) @RequestParam(name="type")
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<DataResponse<ListMoreResponse<TalkBean>>> getUserTalkList(String userId, String talkId, String type, String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("talkId", talkId);
        params.put("type", type);
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getUserTalkList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 版本验证
     *
     * @param name
     * @param platform
     * @return
     */
    public Observable<String> sendTalk(String name, String platform) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("platform", platform);
        return service.sendTalk(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  备用版本验证地址(一)
     * @param param
     * @return
     */
    public Observable<String> versionVerifySpareOne(String param) {
        return service.versionVerifySpareOne(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  备用版本验证地址(二)
     * @param param
     * @return
     */
    public Observable<String> versionVerifySpareTwo(String param) {
        return service.versionVerifySpareTwo(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  版本验证（最新需求）
     * @return
     */
    public Observable<String> versionVerifySpare() {
        return service.versionVerifySpare()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 根据用户id删除用户
     *
     * @param userId
     * @return
     */
    public Observable<BaseResponse> deleteByUserId(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.deleteByUserId(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 今日是否签到
     *
     * @param userId
     * @return
     */
    public Observable<DetailResponse<Boolean>> hasSign(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.hasSign(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 用户签到历史,默认只会返回当月签到记录
     *
     * @param userId
     * @return
     */
    public Observable<ListResponse<SignInBean>> getSignList(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.getSignList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 今日签到
     *
     * @param userId
     * @return
     */
    public Observable<DetailResponse<Boolean>> signNow(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return service.signNow(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取首页行情
     *
     * @return
     */
    public Observable<ListResponse<MarketBean>> getMarketList() {
        return service.getMarketList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 分页获取财经日历
     *
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    public Observable<ListResponse<CalenderDataBean>> getFinanceCalender(String pageNum, String pageSize, String date) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("date", date);
        return service.getFinanceCalender(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取所有的国家的国旗
     *
     * @return
     */
    public Observable<List<CountryImageBean>> queryAllCountry() {
        return service.queryAllCountry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 分页获取财经大事(行业风暴)
     *
     * @param pageNum
     * @param pageSize
     * @param date
     * @return
     */
    public Observable<ListResponse<IndustryStormBean>> getFinanceAffairs(String pageNum, String pageSize, String date) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("date", date);
        return service.getFinanceAffairs(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取股票新闻
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Observable<ListResponse<StockNewsBean>> getNewsList(String pageNumber, String pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNumber", pageNumber);
        params.put("pageSize", pageSize);
        return service.getNewsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  第三方文章新闻
     * @return
     */
    public Observable<List<ArticleNewsBean>> getArticleNewsList(String currentTime) {
        HashMap<String,String> signMap = new HashMap<>();
        signMap.put("access_key", Constant.ACCESS_KEY);
        signMap.put("date",currentTime);
        signMap.put("secret_key",Constant.SECRET_KEY);


        HashMap<String, String> params = new HashMap<>();
        params.put("access_key", Constant.ACCESS_KEY);
        params.put("date", currentTime);
        params.put("secret_key", Constant.SECRET_KEY);
        params.put("sign", SystemUtil.MD5(SystemUtil.getSign(signMap)));

        return service.getArticleNewsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 第三方快讯
     * @return
     */
    public Observable<ExpressNewsListBean> getExpressNewsList() {
        return service.getExpressNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     *  根据用户Id获取推荐说说列表
     * @param userId
     * @return
     */
    public Observable<String> getAllTalkByUserId(String userId) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("userId", userId);
        return service.getAllTalkByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *  获取国家转换汇率
     * @param countryId
     * @return
     */
    public Observable<String> getCountryRate(String countryId) {
        return service.getCountryRate(countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//  Api.getInstance().getCountryRate("CN").subscribe(
//                new ObserverAdapter<String>() {
//        @Override
//        public void onNext(String data) {
//            LogUtil.e("MainActivity",data);
//        }
//        @Override
//        public void onError(Throwable e) {
//            showTxt(getString(R.string.error_network_unknown_host));
//        }
//    });

}
