package com.bc.bit.bean;

import java.io.Serializable;

public class ExpressNewsBean implements Serializable {

        /**
         * content : 加拿大安大略省省长福特：今日与美国贸易代表莱特希泽通话，讨论口罩事宜。
         * id : 152225
         * link :
         * pic :
         * time : 1586195147000
         */

        private String content;
        private int id;
        private String link;
        private String pic;
        private long time;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

}
