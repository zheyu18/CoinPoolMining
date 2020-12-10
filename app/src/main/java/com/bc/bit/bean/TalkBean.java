package com.bc.bit.bean;

import java.io.Serializable;

public class TalkBean implements Serializable {

    /**
     * browserCount : 3
     * commentCount : 4
     * content : 第一步，判断是涨还是跌。假如我们根据自己的方法拍拍脑袋说，明天跌。那么我们的趋势性判断第一步就完成了。
     * <p>
     * 第二步，判断是大涨还是小涨，大跌还是小跌。这一步会相对容易一点，也就是说我把涨跌再细分一下，分成大涨或者小涨，大跌或者小跌。
     * <p>
     * 第三步，判断速度。也就是说在你的涨幅里面加上时间段，比如说一个月看涨五个点，还是两个月看涨五个点？还是一个月看涨十个点。这个就是速度的问题。所以你如果能够把这个判断得很精确的话，那回报就会比没有精确判断的收益会高很多，所以期权是分析越到位，所得到的回报就会越高。
     * <p>
     * 第四步，选策略。第三步判断完了，比如我看到它明天是小跌，是慢跌，就是卖出认购期权，如果说我判断它明天是跌，是大跌是快跌，那么我就买入看跌期权，也就是买入认沽期权。
     * <p>
     * 第五步，选择合约。这个简单的做法就是直接买平值合约，或者卖平值合约，因为平值合约的流动性会最好，交易也最活跃。如果说你想做的更专业一点，熟练之后，不局限于只选择平值合约，你想选择更合适的合约，那么我们就来详细讲解一下第五步。
     * displayBig : true
     * enable : true
     * forwardCount : 0
     * hasZan : false
     * id : 3
     * picture : http://image.cqscrb.top//files/2019/5/4/c54b21e4c157eb372633b0cfac347536.png
     * publishTime : 1582950092000
     * user : {"album":"","fansCount":0,"followCount":0,"head":"http://image.cqscrb.top/files/2020/3/12/09faa483a31f9f5fb5bf240032747704.jpeg","id":3,"nickName":"海伦海","password":"123456","phone":"18225031348","project":null,"projectKey":"futures","signature":"海伦","talkCount":0,"type":0,"uuid":"ee793f269413432bb563423536952e59"}
     * userId : 3
     * video : null
     * videoId : 10
     * zanCount : 8
     */

    private int browserCount;
    private int commentCount;
    private String content;
    private boolean displayBig;
    private boolean enable;
    private int forwardCount;
    private boolean hasZan;
    private int id;
    private String picture;
    private long publishTime;
    private UserBean user;
    private int userId;
    private Object video;
    private int videoId;
    private int zanCount;

    public boolean isConcerned() {
        return isConcerned;
    }

    public void setConcerned(boolean concerned) {
        isConcerned = concerned;
    }

    private boolean isConcerned;


    public int getBrowserCount() {
        return browserCount;
    }

    public void setBrowserCount(int browserCount) {
        this.browserCount = browserCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDisplayBig() {
        return displayBig;
    }

    public void setDisplayBig(boolean displayBig) {
        this.displayBig = displayBig;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getForwardCount() {
        return forwardCount;
    }

    public void setForwardCount(int forwardCount) {
        this.forwardCount = forwardCount;
    }

    public boolean isHasZan() {
        return hasZan;
    }

    public void setHasZan(boolean hasZan) {
        this.hasZan = hasZan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getVideo() {
        return video;
    }

    public void setVideo(Object video) {
        this.video = video;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }


}
