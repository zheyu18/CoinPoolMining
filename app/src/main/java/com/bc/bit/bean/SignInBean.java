package com.bc.bit.bean;

import java.io.Serializable;

public class SignInBean implements Serializable {
    /**
     * continueTimes : 1
     * id : 1569
     * time : 1590595200000
     * userId : 3258
     */

    private int continueTimes;
    private int id;
    private long time;
    private int userId;

    public int getContinueTimes() {
        return continueTimes;
    }

    public void setContinueTimes(int continueTimes) {
        this.continueTimes = continueTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
