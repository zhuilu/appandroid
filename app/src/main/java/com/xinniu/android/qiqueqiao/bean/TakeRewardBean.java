package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class TakeRewardBean {

    /**
     * list : [{"id":1,"order_sn":"R2019072424489887781173","title":"回合肥","amount":"0.01","realname":"画江湖","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg","user_id":1044,"is_v":0,"is_vip":2,"position":"经理","company":"你最近","status":0}]
     * hasMore : 0
     */

    private int hasMore;
    private List<ListBean> list;

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * order_sn : R2019072424489887781173
         * title : 回合肥
         * amount : 0.01
         * realname : 画江湖
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg
         * user_id : 1044
         * is_v : 0
         * is_vip : 2
         * position : 经理
         * company : 你最近
         * received_status : 0
         */

        private int id;
        private String order_sn;
        private String title;
        private String amount;
        private String realname;
        private String head_pic;
        private int user_id;
        private int is_v;
        private int is_vip;
        private String position;
        private String company;
        private int received_status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getReceived_status() {
            return received_status;
        }

        public void setReceived_status(int received_status) {
            this.received_status = received_status;
        }

    }
}
