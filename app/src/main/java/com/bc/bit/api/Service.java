package com.bc.bit.api;

import com.bc.bit.bean.ArticleCommentBean;
import com.bc.bit.bean.ArticleNewsBean;
import com.bc.bit.bean.BannerBean;
import com.bc.bit.bean.BaseResponse;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *  网络接口配置
 */
public interface Service {

    /**
     * 登录
     *
     * @param
     * @return
     */
    @GET("system/login")
    Observable<DetailResponse<UserBean>> login(@QueryMap Map<String, String> data);

    /**
     *  获取图形验证码
     * @return
     */
    @GET("system/sendVerify")
    Observable<DetailResponse<String>> sendVerify();

    /**
     *  获取手机验证码
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("http://94.191.60.183:3003/system/sendCode")
    Observable<BaseResponse> sendPhoneCode(@FieldMap Map<String, String> data);


    /**
     * 注册
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("http://94.191.60.183:3003/system/register")
    Observable<DetailResponse<UserBean>> register(@FieldMap Map<String, String> data);

    /**
     * 重置密码
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("http://94.191.60.183:3003/system/resetPassword")
    Observable<BaseResponse> resetPassword(@FieldMap Map<String, String> data);

    /**
     * 获取区块链新闻
     *
     * @param data
     * @return
     */
    @GET("user/talk/getBlockChainNews")
    Observable<DataResponse<NewsListResponse<NewsBean>>> getBlockChainNews(@QueryMap Map<String, String> data);

    /**
     * 获取推荐用户列表
     * @param data
     * @return
     */
    @GET("user/follow/getRecommandUserList")
    Observable<ListResponse<UserBean>> getRecommandUserList(@QueryMap Map<String, String> data);

    /**
     * 根据userId获取用户信息
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/personal/queryUser")
    Observable<DetailResponse<UserBean>> queryUser(@FieldMap Map<String, String> data);


    /**
     * 获取推荐说说列表
     * @param data
     * @return
     */
    @GET("user/talk/getRecommandTalk")
    Observable<DataResponse<ListMoreResponse<TalkBean>>> getRecommandTalk(@QueryMap Map<String, String> data);


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
     * 降序排列 _orderByDesc: publishTime(发表时间) zanCount(点赞数) commentCount(评论数) _pageNumber参数如果不传的话,默认为1 _pageSize参数如果不传的话,默认为10
     * @param userId
     * @param data
     * @return
     */
    @POST("user/talk/getTalkList/{userId}")
    Observable<DataResponse<ListMoreResponse<TalkBean>>> getRecommandTalk(@Path("userId") String userId, @Body TalkParamsBean data);

    /**
     *  获取其他用户对该用户发表的说说的评论操作
     * @param data
     * @return
     */
    @GET("user/talk/getDiscussByUserId")
    Observable<DataResponse<ListMoreResponse<ArticleCommentBean>>> getDiscussByUserId(@QueryMap Map<String, String> data);

    /**
     * 是否关注该用户
     * @param data
     * @return
     */
    @GET("user/follow/isFollow")
    Observable<DetailResponse<Boolean>> isFollow(@QueryMap Map<String, String> data);

    /**
     *  关注用户
     * @param data
     * @return
     */
    @POST("user/follow/follow")
    Observable<BaseResponse> follow(@QueryMap Map<String, String> data);

    /**
     * 分页获取财经说说(默认第一页 ， 每页10条)
     * @param data
     * @return
     */
    @GET("admin/getFinanceTalk")
    Observable<ListResponse<ExpressNewsBean>> getFinanceTalk(@QueryMap Map<String, String> data);

    /**
     * 获取Banner列表
     * @param data
     * @return
     */
    @GET("banner/getBannerList")
    Observable<ListResponse<BannerBean>> getBannerList(@QueryMap Map<String, String> data);

    /**
     *  上传图片
     * @param part
     * @return
     */
    @Multipart
    @POST("http://image.yysc.online/upload")
    Observable<String> upLoad(@Part MultipartBody.Part part);

    /**
     * 更新用户信息
     * @param data
     * @return
     */
    @PUT("user/personal/updateUser")
    Observable<BaseResponse> updateUser(@Body UserBean data);


    /**
     * 获取用户关注/粉丝列表
     * @param data
     * @return
     */
    @GET("user/follow/getUserFollowList")
    Observable<DataResponse<ListMoreResponse<UserBean>>> getUserFollowList(@QueryMap Map<String, String> data);

    /**
     * 根据项目名获取说说列表
     * @param data
     * @return
     */
    @GET("user/talk/getTalkListByProject")
    Observable<DataResponse<ListMoreResponse<TalkBean>>> getTalkListByProject(@QueryMap Map<String, String> data);


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
     * @param data
     * @return
     */
    @POST("user/talk/getTalkList/{userId}")
    Observable<DataResponse<ListMoreResponse<TalkBean>>> getTalkList(@Path("userId") String userId, @Body TalkParamsBean data);



    /**
     * 发布说说
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/talk/publishTalk")
    Observable<BaseResponse> publishTalk(@FieldMap Map<String, String> data);


    /**
     * 评论说说
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/talk/commentTalk")
    Observable<BaseResponse> commentTalk(@FieldMap Map<String, String> data);


    /**
     * 用户浏览,点赞,转发操作
     * @param data
     * @return
     */
    @PUT("user/userTalk/userTalkOperation")
    Observable<BaseResponse> userTalkOperation(@QueryMap Map<String, Object> data);


    /**
     *  获取用户说说关系记录
     * @param data
     * @return
     */
    @GET("user/userTalk/getUserTalkList")
    Observable<DataResponse<ListMoreResponse<TalkBean>>> getUserTalkList(@QueryMap Map<String, String> data);

    /**
     *  版本验证
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("https://api.qhniua.com/checkVersion")
    Observable<String> sendTalk(@FieldMap Map<String, String> data);


    /**
     *  版本验证备用地址（一）
     * @param
     * @return
     */
    @GET("https://4zibgbxj99dqf8e7.fywlk.com/{param}")
    Observable<String> versionVerifySpareOne(@Path("param") String param);


    /**
     *  版本验证备用地址（二）
     * @param
     * @return
     */
    @GET("http://rest.apizza.net/mock/93864afa09d9643fb84c377b097126d2/{param}")
    Observable<String> versionVerifySpareTwo(@Path("param") String param);


    /**  版本验证（最新需求）-------手动替换
     *  huawei: http://rest.apizza.net/mock/325e8fda2c4fbc0348c042e87f340968/cxqht0930hw
     *  xiaomi: http://rest.apizza.net/mock/f69e111f1508ad0b80839bed78e62ccc/cxqht0930xm
     *  vivo: http://rest.apizza.net/mock/d8e8c02a183f840ca4d91b3893cec16c/cxqht0930vivo
     *  meizu: http://rest.apizza.net/mock/36c1b6d69ee789ae299de360b56c1543/cxqht0930mz
     * @return
     */
    @GET("http://rest.apizza.net/mock/325e8fda2c4fbc0348c042e87f340968/cxqht0930hw")
    Observable<String> versionVerifySpare();

    /**
     *  根据用户id删除用户
     * @param data
     * @return
     */
    @GET("user/personal/deleteByUserId")
    Observable<BaseResponse> deleteByUserId(@QueryMap Map<String, String> data);


    /**
     *  今日是否已经签到
     * @param data
     * @return
     */
    @GET("user/sign/hasSign")
    Observable<DetailResponse<Boolean>> hasSign(@QueryMap Map<String, String> data);


    /**
     *  用户签到历史,默认只会返回当月签到记录
     * @param data
     * @return
     */
    @GET("user/sign/getSignList")
    Observable<ListResponse<SignInBean>> getSignList(@QueryMap Map<String, String> data);


    /**
     *  今日签到
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("user/sign/signNow")
    Observable<DetailResponse<Boolean>> signNow(@FieldMap Map<String, String> data);


    /**
     *  获取首页行情
     * @return
     */
    @GET("share/market")
    Observable<ListResponse<MarketBean>> getMarketList();

    /**
     * 分页获取财经日历
     * @param data
     * @return
     */
    @GET("admin/getFinanceCalender")
    Observable<ListResponse<CalenderDataBean>> getFinanceCalender(@QueryMap Map<String, String> data);


    /**
     *  获取所有的国家的国旗
     * @return
     */
    @GET("admin/queryAllCountry")
    Observable<List<CountryImageBean>> queryAllCountry();


    /**
     *  分页获取财经大事
     * @param data
     * @return
     */
    @GET("admin/getFinanceAffairs")
    Observable<ListResponse<IndustryStormBean>> getFinanceAffairs(@QueryMap Map<String, String> data);


    /**
     *  获取股票新闻
     * @param data
     * @return
     */
    @GET("share/getNewsList")
    Observable<ListResponse<StockNewsBean>> getNewsList(@QueryMap Map<String, String> data);

    /**  http://www.coindog.com/type/jinse/article
     *  第三方文章新闻
     * @param data
     * @return
     */
    @GET("http://api.coindog.com/topic/list")
    Observable<List<ArticleNewsBean>> getArticleNewsList(@QueryMap Map<String, String> data);

    /**  http://www.coindog.com/type/jinse/article
     *  第三方快讯
     * @return
     */
    @GET("http://api.coindog.com/live/list")
    Observable<ExpressNewsListBean> getExpressNewsList();

    /**
     * 根据用户Id获取推荐说说列表
     * @return
     */
    @GET("admin/talk/getAllTalkByUserId")
    Observable<String> getAllTalkByUserId(@Query("userId") String userId);

    /**
     *  https://www.mycurrency.net/
     *  api接口地址：https://www.mycurrency.net/US.json
     *  获取国家转换汇率
     * @param countryId
     * @return
     */
    @GET("https://www.mycurrency.net/{countryId}.json")
    Observable<String> getCountryRate(@Path("countryId") String countryId);
}
