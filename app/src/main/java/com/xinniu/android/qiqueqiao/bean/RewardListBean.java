package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class RewardListBean {

    /**
     * list : [{"status":1,"id":48,"order_sn":"R2019081616405919474594","title":"哈哈","detail":"回家哈哈哈哈哈赶紧看看近几年迷迷糊糊国家机关放假结婚哥哥哥我刚看到喝咖啡就几个手机壳感觉很好急急急急急啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啥啥啥啊啊啊啊啊啊啊啊啊傻傻啊啊啊啊啊啊啊啊啊啥啥啥啊啊啊啊啊啊啊啊啊啊啊爱啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","amount":"1.00","realname":"张莉莉","number":1,"freeze_number":0,"refund_number":0,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/84394710.jpg","user_id":1442,"is_v":0,"is_vip":0,"position":"会记三个","company":"刚刚","remaining_number":"1"},{"status":2,"id":35,"order_sn":"R2019080202151956328153","title":"测试支付宝1x10","detail":"擦擦擦擦擦","amount":"1.00","realname":"乔俊华","number":10,"freeze_number":0,"refund_number":10,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/69493208.jpg","user_id":1417,"is_v":0,"is_vip":0,"position":"会刚发完","company":"百分点","remaining_number":"0"}]
     * hasMore : 1
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
         * status : 1
         * id : 48
         * order_sn : R2019081616405919474594
         * title : 哈哈
         * detail : 回家哈哈哈哈哈赶紧看看近几年迷迷糊糊国家机关放假结婚哥哥哥我刚看到喝咖啡就几个手机壳感觉很好急急急急急啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啥啥啥啊啊啊啊啊啊啊啊啊傻傻啊啊啊啊啊啊啊啊啊啥啥啥啊啊啊啊啊啊啊啊啊啊啊爱啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
         * amount : 1.00
         * realname : 张莉莉
         * number : 1
         * freeze_number : 0
         * refund_number : 0
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/84394710.jpg
         * user_id : 1442
         * is_v : 0
         * is_vip : 0
         * position : 会记三个
         * company : 刚刚
         * remaining_number : 1
         */

        private int status;
        private int id;
        private String order_sn;
        private String title;
        private String detail;
        private String amount;
        private String realname;
        private int number;
        private int freeze_number;
        private int refund_number;
        private String head_pic;
        private int user_id;
        private int is_v;
        private int is_vip;
        private String position;
        private String company;
        private int remaining_number;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getFreeze_number() {
            return freeze_number;
        }

        public void setFreeze_number(int freeze_number) {
            this.freeze_number = freeze_number;
        }

        public int getRefund_number() {
            return refund_number;
        }

        public void setRefund_number(int refund_number) {
            this.refund_number = refund_number;
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

        public int getRemaining_number() {
            return remaining_number;
        }

        public void setRemaining_number(int remaining_number) {
            this.remaining_number = remaining_number;
        }
    }
}
