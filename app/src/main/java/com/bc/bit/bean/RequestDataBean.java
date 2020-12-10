package com.bc.bit.bean;

import java.util.List;

public class RequestDataBean {

    @Override
    public String toString() {
        return "RequestDataBean{" +
                "status=" + status +
                ", url='" + url + '\'' +
                ", dPool=" + dPool +
                ", fPool=" + fPool +
                ", dBlog=" + dBlog +
                ", fBlog=" + fBlog +
                '}';
    }

    /**
     * status : 0
     * url : https://www.baidu.com
     * dPool : ["https://api.qhniua.com","https://api.ios005.com"]
     * fPool : []
     * dBlog : []
     * fBlog : []
     */

    private int status;//1进入网页，0进原生
    private String url;
    private List<String> dPool;
    private List<?> fPool;
    private List<?> dBlog;
    private List<?> fBlog;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getDPool() {
        return dPool;
    }

    public void setDPool(List<String> dPool) {
        this.dPool = dPool;
    }

    public List<?> getFPool() {
        return fPool;
    }

    public void setFPool(List<?> fPool) {
        this.fPool = fPool;
    }

    public List<?> getDBlog() {
        return dBlog;
    }

    public void setDBlog(List<?> dBlog) {
        this.dBlog = dBlog;
    }

    public List<?> getFBlog() {
        return fBlog;
    }

    public void setFBlog(List<?> fBlog) {
        this.fBlog = fBlog;
    }
}
