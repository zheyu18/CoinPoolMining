package com.bc.bit.bean;

import java.io.Serializable;

public class BannerBean implements Serializable {

    /**
     * data : 1
     * id : 25
     * picturePath : http://image.cqscrb.top/files/2019/11/9/a7a31e38be0234298e530ef00f57ede8.png,http://image.cqscrb.top/files/2019/11/9/d1c98533f6c57a70864c7b8427ff043b.png
     * project : null
     * projectKey : futures
     * type : 1
     * updateTime : 1575874852000
     * url :
     */

    private int data;
    private int id;
    private String picturePath;
    private Object project;
    private String projectKey;
    private int type;
    private long updateTime;
    private String url;
    private String content;
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public BannerBean(int resId, String url) {
        this.resId = resId;
        this.url = url;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Object getProject() {
        return project;
    }

    public void setProject(Object project) {
        this.project = project;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
