package com.bc.bit.util;

/**
 * 常量类
 */
public class Constant {

    /**
     * Id常量
     */
    public static final String ID = "ID";

    /**
     * 传递data key
     */
    public static final String DATA = "DATA";

    /**
     * int值id
     */

    public static final String INT = "int";

    /**
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    public static final String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    /**
     * 项目主题类型
     */
    public static final String PROJECT_NAME = "currency";

    /**
     *  项目主题类型(验证码、注册接口专用)
     */
    public static final String PROJECT_NAME_CODE = "bcwk";

    public static final String TYPE_ONE = "1";
    public static final String TYPE_TWO = "2";
    public static final String TYPE_THREE = "3";

    /**
     * 平台
     */
    public static final String SD = "YX";

    /**
     *  退出登录标识
     */
    public static final String LOGOUT = "logout";

    /**
     *  修改密码入口状态判断
     */
    public static final String CHANGE_PASSWORD_LOGIN_ENTRANCE = "login";
    public static final String CHANGE_PASSWORD_SETTING_ENTRANCE = "setting";

    /**
     *  用户协议相关
     */
    public static final String PRIVATE_POLICY = "private_policy";
    public static final String USER_AGREEMENT = "user_agreement";
    public static final String PROTOCOL = "protocol";

    /**
     *  获取文章新闻所需签名密钥
     */
    public static final String ACCESS_KEY = "8353fb4106239de6f10c17505829f530";
    public static final String SECRET_KEY = "626aafe153467814";


    /**
     *  第三方缓存地址
     */
    public static final String TRIPARTITE_URL_SPARE = "tripartite_url_spare";

    /**
     *  第三方缓存备用地址
     */
    public static final String TRIPARTITE_URL_SPARE_POOL = "tripartite_url_spare_pool";

    /**
     *  时间差8小时
     */
    public static final long TIME_DIFF = 28800000;

}
