package com.bc.bit.bean;



/**
 * 通用网络请求响应模型
 */
public class BaseResponse{
    /**
     * data : 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
     * success : true
     */

    private boolean success;
    private String msg;

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
}
