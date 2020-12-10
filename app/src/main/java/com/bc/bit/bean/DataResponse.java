package com.bc.bit.bean;

public class DataResponse<T> extends BaseResponse {
    /**
     * data : {"currentPage":1,"hasMore":true,"list":[{"browserCount":1,"commentCount":1,"content":"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈保护好黄忠哈巴爸爸不行不行好吧在不在不不是呢嫩些行吧行吧不行不行八点半本饿不不行不行不","displayBig":false,"enable":true,"forwardCount":0,"hasZan":false,"id":1454,"picture":"http://image.cqscrb.top/files/2020/4/8/6cd6445367d55afa3a962a08e75911d5.jpg","publishTime":1588867200000,"user":{"album":"","fansCount":0,"followCount":0,"head":"http://image.cqscrb.top/files/2020/4/9/2dbe2d2fb8a17cd4db5e06e991b0301b.jpg","id":3233,"nickName":"zheyu","password":"123456","phone":"15328681260","project":{"id":7,"key":"futures","name":"期货(重点)"},"projectKey":"futures","signature":"哈哈哈","talkCount":0,"type":1,"uuid":"b20ba85e0ce34ee79c4c439029518fd1"},"userId":3233,"video":null,"videoId":-1,"zanCount":2},{"browserCount":1,"commentCount":2,"content":"啊哈哈哈哈还真是在不在不是还在家自己想那些不长脑子那些年不想你先你先你先你信息南站的那些年","displayBig":false,"enable":true,"forwardCount":0,"hasZan":false,"id":1455,"picture":"http://image.cqscrb.top/files/2020/4/8/d23e95a43885cb23884ba1e90314bde9.jpg,http://image.cqscrb.top/files/2020/4/8/d23e95a43885cb23884ba1e90314bde9.jpg,http://image.cqscrb.top/files/2020/4/8/d23e95a43885cb23884ba1e90314bde9.jpg","publishTime":1588867200000,"user":{"album":"","fansCount":0,"followCount":0,"head":"http://image.cqscrb.top/files/2020/4/9/2dbe2d2fb8a17cd4db5e06e991b0301b.jpg","id":3233,"nickName":"zheyu","password":"123456","phone":"15328681260","project":{"id":7,"key":"futures","name":"期货(重点)"},"projectKey":"futures","signature":"哈哈哈","talkCount":0,"type":1,"uuid":"b20ba85e0ce34ee79c4c439029518fd1"},"userId":3233,"video":null,"videoId":-1,"zanCount":1}],"pageSize":2,"totalPage":67,"totalSize":133}
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
