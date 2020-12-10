package com.bc.bit.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zhengjiyu on 2018/11/14.
 * Describe:
 * Email:3301360040@qq.com
 */

public class SystemUtil {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }



//    public static String MD5(String sourceStr) {
//        try {
//            // 获得MD5摘要算法的 MessageDigest对象
//            MessageDigest mdInst = MessageDigest.getInstance("MD5");
//            // 使用指定的字节更新摘要
//            mdInst.update(sourceStr.getBytes());
//            // 获得密文
//            byte[] md = mdInst.digest();
//            // 把密文转换成十六进制的字符串形式
//            StringBuffer buf = new StringBuffer();
//            for (int i = 0; i < md.length; i++) {
//                int tmp = md[i];
//                if (tmp < 0)
//                    tmp += 256;
//                if (tmp < 16)
//                    buf.append("0");
//                buf.append(Integer.toHexString(tmp));
//            }
//            //return buf.toString().substring(8, 24);// 16位加密
//            return buf.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }




    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.getMessage();
            return "出错";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    public static String getSign(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new MapSort().sortMap(map);
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size()-1){
                str += list.get(i)+"" ;
            }else {
                str += list.get(i) + "&";
            }
        }
        return str;
    }




    public static String md5(String password) {

        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = password.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);

            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

}
