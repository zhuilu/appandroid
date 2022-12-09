package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public class AddGroupListBean {


    /**
     * list : [{"id":19,"name":"测试群组","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/circle/153803680710.jpg","status":1,"create_time":1538036807,"category":137,"level":1,"is_top":0,"sort_order":50,"user_id":1376,"notice":"","type":0,"introduction":"这是一个测试数据","is_verify":1,"is_send":0,"is_v":0,"is_vip":0,"actual_number":1,"number":100,"city":14,"group_number":"680710","realname":"曾杨","position":"好好说","company":"睡觉睡觉","city_name":"湖南","user_identity":0,"num":1,"share_url":"http://t.qiqueqiao.com/share/circle/19"},{"id":18,"name":"市郊铁路","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/circle/153957111610.jpg","status":1,"create_time":1538034150,"category":134,"level":1,"is_top":0,"sort_order":50,"user_id":1320,"notice":"1212","type":1,"introduction":"市郊铁路运输安全委员会办公室副主任委员有时候会觉得你有本事就不要来001","is_verify":1,"is_send":0,"is_v":0,"is_vip":0,"actual_number":5,"number":100,"city":52,"group_number":"680712","realname":"Gggg","position":"Q","company":"Q","city_name":"北京","user_identity":2,"num":5,"share_url":"http://t.qiqueqiao.com/share/circle/18"},{"id":17,"name":"测试群组01","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/circle/153803226910.jpg","status":1,"create_time":1538032269,"category":137,"level":1,"is_top":0,"sort_order":50,"user_id":1376,"notice":"222222","type":0,"introduction":"这是一个测试数据","is_verify":1,"is_send":0,"is_v":0,"is_vip":0,"actual_number":1,"number":100,"city":14,"group_number":"680711","realname":"曾杨","position":"好好说","company":"睡觉睡觉","city_name":"湖南","user_identity":2,"num":1,"share_url":"http://t.qiqueqiao.com/share/circle/17"}]
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
         * id : 19
         * name : 测试群组
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/circle/153803680710.jpg
         * status : 1
         * create_time : 1538036807
         * category : 137
         * level : 1
         * is_top : 0
         * sort_order : 50
         * user_id : 1376
         * notice :
         * type : 0
         * introduction : 这是一个测试数据
         * is_verify : 1
         * is_send : 0
         * is_v : 0
         * is_vip : 0
         * actual_number : 1
         * number : 100
         * city : 14
         * group_number : 680710
         * realname : 曾杨
         * position : 好好说
         * company : 睡觉睡觉
         * city_name : 湖南
         * user_identity : 0
         * num : 1
         * share_url : http://t.qiqueqiao.com/share/circle/19
         */

        private int id;
        private String name;
        private String head_pic;
        private int status;
        private int create_time;
        private int category;
        private int level;
        private int is_top;
        private int sort_order;
        private int user_id;
        private String notice;
        private int type;
        private String introduction;
        private int is_verify;
        private int is_send;
        private int is_v;
        private int is_vip;
        private int actual_number;
        private int number;
        private int city;
        private String group_number;
        private String realname;
        private String position;
        private String company;
        private String city_name;
        private int user_identity;
        private int num;
        private String share_url;

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public int getSort_order() {
            return sort_order;
        }

        public void setSort_order(int sort_order) {
            this.sort_order = sort_order;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public int getIs_send() {
            return is_send;
        }

        public void setIs_send(int is_send) {
            this.is_send = is_send;
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

        public int getActual_number() {
            return actual_number;
        }

        public void setActual_number(int actual_number) {
            this.actual_number = actual_number;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getGroup_number() {
            return group_number;
        }

        public void setGroup_number(String group_number) {
            this.group_number = group_number;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public int getUser_identity() {
            return user_identity;
        }

        public void setUser_identity(int user_identity) {
            this.user_identity = user_identity;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}
