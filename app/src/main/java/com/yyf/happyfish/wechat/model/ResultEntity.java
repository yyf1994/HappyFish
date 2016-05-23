package com.yyf.happyfish.wechat.model;

import java.util.List;

public class ResultEntity {

        private int totalPage;
        private int ps;
        private int pno;
        /**
         * id : wechat_20150401071581
         * title : 号外：集宁到乌兰花的班车出事了！！！！！
         * source : 内蒙那点事儿
         * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-214279.jpg/168
         * mark :
         * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20150401071581
         */

        private List<ListEntity> list;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getPno() {
            return pno;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

}
