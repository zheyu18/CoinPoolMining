package com.bc.bit.bean;

public class DetailResponse<T> extends BaseResponse {

    /**
     * 真实数据
     * 类似是泛型
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
