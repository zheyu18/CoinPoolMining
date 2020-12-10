package com.scrb.klinechart.request.base;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KBaseObjectBean<T> implements Serializable {
    public boolean success;

    @Override
    public String toString() {
        return "KBaseObjectBean{" +
                "success=" + success +
                ", content='" + content + '\'' +
                ", dataType=" + dataType +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String content;

    public KBaseObjectBean() {
    }

    public KBaseObjectBean(T data) {
        this.data = data;
    }

    /**
     * 1 banner 数据
     */
    public int dataType;

    public KBaseObjectBean(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public KBaseObjectBean(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public String msg;
    public T data;
}
