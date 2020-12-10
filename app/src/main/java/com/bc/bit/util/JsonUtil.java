package com.bc.bit.util;


import com.bc.bit.bean.UserBean;
import com.bc.bit.util.WrapperJson.DataWrapperJson;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class JsonUtil {
    final static DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();

    static {
        df.setGroupingSize(3);
    }

    public static String numberFormat(int value) {
        try {
            return df.format(value);
        } catch (Exception e) {
            return String.valueOf(value);
        }
    }

    public static String jsonData(String json) {
        try {
            return new JSONObject(json).getString("data");
        } catch (Exception e) {
            return json;
        }
    }

    public static String toJson(Serializable obj) {
        return DataWrapperJson.toJsonString(obj);
    }

    public static UserBean toUser(String str) {
        return DataWrapperJson.jsonToObject(str, UserBean.class);
    }

    public static boolean succeed(String json) {

        try {
            JSONObject jo = new JSONObject(json);
            return jo.getInt("code") == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static String error(String json) {

        try {
            JSONObject jo = new JSONObject(json);
            return jo.getString("msg");
        } catch (Exception e) {
            return json;
        }
    }

    public static int code(String json) {

        try {
            JSONObject jo = new JSONObject(json);
            return jo.getInt("code");
        } catch (Exception e) {
            return -2;
        }
    }

}
