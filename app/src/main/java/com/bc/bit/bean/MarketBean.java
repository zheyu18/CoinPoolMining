package com.bc.bit.bean;

import java.io.Serializable;

public class MarketBean implements Serializable {
        /**
         * code :
         * dealCount : 2556639
         * dealNum : 30044782
         * id : 0
         * name : 上证指数
         * price : 2923.3711
         * type :
         * upOrDownPercent : 1.9731
         * upPercent : 0.07
         */

        private String code;
        private String dealCount;
        private String dealNum;
        private int id;
        private String name;
        private String price;
        private String type;
        private String upOrDownPercent;
        private String upPercent;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDealCount() {
            return dealCount;
        }

        public void setDealCount(String dealCount) {
            this.dealCount = dealCount;
        }

        public String getDealNum() {
            return dealNum;
        }

        public void setDealNum(String dealNum) {
            this.dealNum = dealNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpOrDownPercent() {
            return upOrDownPercent;
        }

        public void setUpOrDownPercent(String upOrDownPercent) {
            this.upOrDownPercent = upOrDownPercent;
        }

        public String getUpPercent() {
            return upPercent;
        }

        public void setUpPercent(String upPercent) {
            this.upPercent = upPercent;
        }
}
