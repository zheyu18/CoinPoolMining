package com.bc.bit.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class MultipleItem implements MultiItemEntity, Serializable {

    public static final int ARTICLE_TOP = 0;
    public static final int ARTICLE_NEWS = 1;
    public static final int EXPRESS_NEWS = 2;
    private int itemType;
    private String time;
    private String content;
    private String picUrl;
    private String maxContent;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }
    public MultipleItem(int itemType,String time,String content) {
        this.itemType = itemType;
        this.time = time;
        this.content = content;
    }

    public String getMaxContent() {
        return maxContent;
    }

    public void setMaxContent(String maxContent) {
        this.maxContent = maxContent;
    }

    public MultipleItem(int itemType, String time, String content, String picUrl, String maxContent) {
        this.itemType = itemType;
        this.time = time;
        this.content = content;
        this.picUrl = picUrl;
        this.maxContent = maxContent;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
