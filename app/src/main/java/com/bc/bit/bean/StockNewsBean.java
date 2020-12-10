package com.bc.bit.bean;

import java.io.Serializable;

/**
 *  股票新闻
 */
public class StockNewsBean implements Serializable {
        /**
         * content :
         * id : 434
         * picture : http://pdf.dfcfw.com/pdf/H3_AP201907191339920892_1.pdf
         * publishTime : 2019-07-21 09:02:11
         * title : [隆基股份]半年度业绩预增点评：业绩大超预期，单晶龙头地位显著
         * url : http://wap.eastmoney.com/ReportInfo.aspx?type=research&id=APPJ7AfJ1Ww1ASearchReport&date=20190721
         */

        private String content;
        private int id;
        private String picture;
        private String publishTime;
        private String title;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
