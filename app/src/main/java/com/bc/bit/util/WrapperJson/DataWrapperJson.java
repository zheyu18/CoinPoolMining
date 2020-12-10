package com.bc.bit.util.WrapperJson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataWrapperJson  implements DataWrapper{
    private static Gson mGson = new Gson();
    private Class<?> mClass;

    public DataWrapperJson(Class<?> cls) {
        this.mClass = cls;
    }

    public TransferObject wrapper(String response) {
        return TransferObject.success(mGson.fromJson(response, this.mClass));
    }

    public static final <T> T jsonToObject(String json, Class<T> cls) {
        if (json != null && cls != null) {
            try {
                return mGson.fromJson(json, cls);
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static final <T> T jsonToType(String json, TypeToken typeToken) {
        if (json != null && typeToken != null) {
            try {
                return mGson.fromJson(json, typeToken.getType());
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static final String toJsonString(Object obj) {
        return obj == null ? "" : mGson.toJson(obj);
    }
}
