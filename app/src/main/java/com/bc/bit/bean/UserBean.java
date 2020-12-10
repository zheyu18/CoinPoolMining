package com.bc.bit.bean;

import java.io.Serializable;

public class UserBean implements Serializable {


    /**
     * album :
     * fansCount : 0
     * followCount : 0
     * head : http://video.cqscrb.top/logo.jpg
     * id : 3229
     * nickName : 185****5392
     * password : 123456
     * phone : 18581565392
     * project : null
     * projectKey : futures
     * signature :
     * talkCount : 0
     * type : 2
     * uuid : df3c6e06fd9d4caaa75b12e0e463e25f
     */

    private String album;
    private int fansCount;
    private int followCount;
    private String head;
    private volatile int id;
    private volatile String nickName;
    private String password;
    private String phone;
    private Object project;
    private String projectKey;
    private String signature;
    private int talkCount;
    private int type;
    private String uuid;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getTalkCount() {
        return talkCount;
    }

    public void setTalkCount(int talkCount) {
        this.talkCount = talkCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
