package com.bc.bit.bean;

import java.io.Serializable;

public class ArticleCommentBean implements Serializable {

            /**
             * content : 看看可口可乐可口可乐可口可乐可口可乐可口可乐可口可乐看了看可口可乐看了看可口可乐可口可乐可口可乐了
             * gameVideo : {"bGameId":"","bGameName":"","countTimes":"","discuss":null,"iTotal":"","id":0,"imageFont":"","imgUrlHttp":"","indexDate":null,"lookTimes":"","newsId":"","sourceName":"","subTitle":"","timeLength":"","title":"","user":null,"userId":0,"videoUrl":""}
             * id : 700
             * matchId : -1
             * matchInfo : null
             * publishTime : 1588780800000
             * talk : {"browserCount":4,"commentCount":14,"content":"由指数换股想到的两种投资方法\n    每年6月份，大部分指数要对一些成份股进行调换。昨天指数公司预告了将要调换的股票，于6月17日生效。\n    指数成份股选取有一定的规则。比如，沪深300指数是由沪深两市前300只规模最大的股票组成，经过一年时间，其中有的股票市值缩小了，排不进前300名的行列，同时有其他的股票规模增大了，挤进前300名之列，于是指数成份股就要做一些调换。\n    此事让我联想到了两种投资方法：\n    1、指数基金投资\n    股票市值体现了其市场价值，调入市值增大的、同时剔除市值减小的成份股，这样指数始终保持着择优录取、吐故纳新的功能。据此可知，指数基金实际上也就是对一系列股票的优化组合。股市也遵循\u201c二八法则\u201d，市场上大多数是平庸股和熊股，只有少数是牛股。于是根据指数成份股的构成可知，指数基金表现虽然逊于牛股，但优于大多数股票。这也就是指数基金能够超越大多数投资者水平的基本原因。因此，对于大多数人来说，指数基金是一个很好的投资选项。\n    2、优中选优，集中投资\n    指数成份股的选取规则看上去死板板的，却很有效。某只成分股一旦市值下降到一定程度，已经不好了，就会被剔除出去；而另一只股票市值增大，已经很好了，就会被选入。通常是，指数成份股编辑只对已经是好是坏的股票进行取舍，而不管它们\u201c未来会怎样\u201d。由于\u201c好的还会好\u201d是大概率事件，这个\u201c马.太.效.应\u201d使得指数一直保持着相对优势。因此，根据这一规律，如果你想跑赢指数，又不奢望抓到未来的潜在大牛股，宁愿要确定的好结果，也不要\u201c有可能\u201d的极好结果，那么不妨在指数成份股中，优中选优，相对集中投资，就有可能实现。\n","displayBig":false,"enable":true,"forwardCount":0,"hasZan":false,"id":55,"picture":"http://image.cqscrb.top//files/2019/5/5/8df7b73a7820f4aef47864f2a6c5fccf.jpg","publishTime":1585324800000,"user":null,"userId":24,"video":null,"videoId":0,"zanCount":1}
             * talkId : 55
             * user : {"album":"http://image.cqscrb.top/files/2020/3/30/1abb2dc3d76311944ffdbe9980fbaadd.jpg","fansCount":0,"followCount":0,"head":"http://image.cqscrb.top/files/2020/3/30/7f1b68a91d81846d31ba23bb50a4bbcc.jpg","id":3231,"nickName":"xixi","password":"123456ss","phone":"18981261910","project":null,"projectKey":"futures","signature":"哈哈哈哈","talkCount":0,"type":0,"uuid":"3dd1ef13292a4ac3ad05fd3dc4a6d87a"}
             * userId : 3231
             * videoId : -1
             */

            private String content;
            private GameVideoBean gameVideo;
            private int id;
            private int matchId;
            private Object matchInfo;
            private long publishTime;
            private TalkBean talk;
            private int talkId;
            private UserBean user;
            private int userId;
            private int videoId;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public GameVideoBean getGameVideo() {
                return gameVideo;
            }

            public void setGameVideo(GameVideoBean gameVideo) {
                this.gameVideo = gameVideo;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMatchId() {
                return matchId;
            }

            public void setMatchId(int matchId) {
                this.matchId = matchId;
            }

            public Object getMatchInfo() {
                return matchInfo;
            }

            public void setMatchInfo(Object matchInfo) {
                this.matchInfo = matchInfo;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public TalkBean getTalk() {
                return talk;
            }

            public void setTalk(TalkBean talk) {
                this.talk = talk;
            }

            public int getTalkId() {
                return talkId;
            }

            public void setTalkId(int talkId) {
                this.talkId = talkId;
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

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }

            public static class GameVideoBean {
                /**
                 * bGameId :
                 * bGameName :
                 * countTimes :
                 * discuss : null
                 * iTotal :
                 * id : 0
                 * imageFont :
                 * imgUrlHttp :
                 * indexDate : null
                 * lookTimes :
                 * newsId :
                 * sourceName :
                 * subTitle :
                 * timeLength :
                 * title :
                 * user : null
                 * userId : 0
                 * videoUrl :
                 */

                private String bGameId;
                private String bGameName;
                private String countTimes;
                private Object discuss;
                private String iTotal;
                private int id;
                private String imageFont;
                private String imgUrlHttp;
                private Object indexDate;
                private String lookTimes;
                private String newsId;
                private String sourceName;
                private String subTitle;
                private String timeLength;
                private String title;
                private Object user;
                private int userId;
                private String videoUrl;

                public String getBGameId() {
                    return bGameId;
                }

                public void setBGameId(String bGameId) {
                    this.bGameId = bGameId;
                }

                public String getBGameName() {
                    return bGameName;
                }

                public void setBGameName(String bGameName) {
                    this.bGameName = bGameName;
                }

                public String getCountTimes() {
                    return countTimes;
                }

                public void setCountTimes(String countTimes) {
                    this.countTimes = countTimes;
                }

                public Object getDiscuss() {
                    return discuss;
                }

                public void setDiscuss(Object discuss) {
                    this.discuss = discuss;
                }

                public String getITotal() {
                    return iTotal;
                }

                public void setITotal(String iTotal) {
                    this.iTotal = iTotal;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getImageFont() {
                    return imageFont;
                }

                public void setImageFont(String imageFont) {
                    this.imageFont = imageFont;
                }

                public String getImgUrlHttp() {
                    return imgUrlHttp;
                }

                public void setImgUrlHttp(String imgUrlHttp) {
                    this.imgUrlHttp = imgUrlHttp;
                }

                public Object getIndexDate() {
                    return indexDate;
                }

                public void setIndexDate(Object indexDate) {
                    this.indexDate = indexDate;
                }

                public String getLookTimes() {
                    return lookTimes;
                }

                public void setLookTimes(String lookTimes) {
                    this.lookTimes = lookTimes;
                }

                public String getNewsId() {
                    return newsId;
                }

                public void setNewsId(String newsId) {
                    this.newsId = newsId;
                }

                public String getSourceName() {
                    return sourceName;
                }

                public void setSourceName(String sourceName) {
                    this.sourceName = sourceName;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getTimeLength() {
                    return timeLength;
                }

                public void setTimeLength(String timeLength) {
                    this.timeLength = timeLength;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
                    this.user = user;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public String getVideoUrl() {
                    return videoUrl;
                }

                public void setVideoUrl(String videoUrl) {
                    this.videoUrl = videoUrl;
                }
            }

            public static class TalkBean {
                /**
                 * browserCount : 4
                 * commentCount : 14
                 * content : 由指数换股想到的两种投资方法
                 每年6月份，大部分指数要对一些成份股进行调换。昨天指数公司预告了将要调换的股票，于6月17日生效。
                 指数成份股选取有一定的规则。比如，沪深300指数是由沪深两市前300只规模最大的股票组成，经过一年时间，其中有的股票市值缩小了，排不进前300名的行列，同时有其他的股票规模增大了，挤进前300名之列，于是指数成份股就要做一些调换。
                 此事让我联想到了两种投资方法：
                 1、指数基金投资
                 股票市值体现了其市场价值，调入市值增大的、同时剔除市值减小的成份股，这样指数始终保持着择优录取、吐故纳新的功能。据此可知，指数基金实际上也就是对一系列股票的优化组合。股市也遵循“二八法则”，市场上大多数是平庸股和熊股，只有少数是牛股。于是根据指数成份股的构成可知，指数基金表现虽然逊于牛股，但优于大多数股票。这也就是指数基金能够超越大多数投资者水平的基本原因。因此，对于大多数人来说，指数基金是一个很好的投资选项。
                 2、优中选优，集中投资
                 指数成份股的选取规则看上去死板板的，却很有效。某只成分股一旦市值下降到一定程度，已经不好了，就会被剔除出去；而另一只股票市值增大，已经很好了，就会被选入。通常是，指数成份股编辑只对已经是好是坏的股票进行取舍，而不管它们“未来会怎样”。由于“好的还会好”是大概率事件，这个“马.太.效.应”使得指数一直保持着相对优势。因此，根据这一规律，如果你想跑赢指数，又不奢望抓到未来的潜在大牛股，宁愿要确定的好结果，也不要“有可能”的极好结果，那么不妨在指数成份股中，优中选优，相对集中投资，就有可能实现。
                 * displayBig : false
                 * enable : true
                 * forwardCount : 0
                 * hasZan : false
                 * id : 55
                 * picture : http://image.cqscrb.top//files/2019/5/5/8df7b73a7820f4aef47864f2a6c5fccf.jpg
                 * publishTime : 1585324800000
                 * user : null
                 * userId : 24
                 * video : null
                 * videoId : 0
                 * zanCount : 1
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
                private Object user;
                private int userId;
                private Object video;
                private int videoId;
                private int zanCount;

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

                public Object getUser() {
                    return user;
                }

                public void setUser(Object user) {
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

            public static class UserBean {
                /**
                 * album : http://image.cqscrb.top/files/2020/3/30/1abb2dc3d76311944ffdbe9980fbaadd.jpg
                 * fansCount : 0
                 * followCount : 0
                 * head : http://image.cqscrb.top/files/2020/3/30/7f1b68a91d81846d31ba23bb50a4bbcc.jpg
                 * id : 3231
                 * nickName : xixi
                 * password : 123456ss
                 * phone : 18981261910
                 * project : null
                 * projectKey : futures
                 * signature : 哈哈哈哈
                 * talkCount : 0
                 * type : 0
                 * uuid : 3dd1ef13292a4ac3ad05fd3dc4a6d87a
                 */

                private String album;
                private int fansCount;
                private int followCount;
                private String head;
                private int id;
                private String nickName;
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

}
