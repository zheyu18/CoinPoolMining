package com.bc.bit.bean;

import java.io.Serializable;

public class MyConcernedBean implements Serializable {
            /**
             * album :
             * fansCount : 13
             * followCount : 46
             * head : http://image.cqscrb.top/files/2020/3/12/09faa483a31f9f5fb5bf240032747704.jpeg
             * id : 3
             * nickName : 海伦海
             * password : 123456
             * phone : 18225031348
             * project : null
             * projectKey : futures
             * signature : 海伦
             * talkCount : 5
             * type : 0
             * uuid : ee793f269413432bb563423536952e59
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

    public MyConcernedBean(String nickName) {
        this.nickName = nickName;
    }

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
