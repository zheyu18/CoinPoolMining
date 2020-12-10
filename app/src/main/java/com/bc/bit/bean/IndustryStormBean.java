package com.bc.bit.bean;

import java.io.Serializable;

public class IndustryStormBean implements Serializable {

        /**
         * content : 美联储公布2020年第一季度资金流动报告，包括美国家庭财务健康数据。
         * country : 美国
         * id : 2059
         * region : 华盛顿
         * time : 1591113600000
         */

        private String content;
        private String country;
        private int id;
        private String region;
        private long time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
}
