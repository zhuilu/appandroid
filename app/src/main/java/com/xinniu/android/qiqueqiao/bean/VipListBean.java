package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by lzq on 2017/12/15.
 */

public class VipListBean {


    /**
     * users : {"is_vip":1,"head_pic":"","realname":"爱","mobile":"18806171210","dateMsg":"你的会员权限将在2019-01-01日到期"}
     * vip : [{"id":9,"name":"季会员","desc":"*每天沟通30人，合作权限3个月<\/br>*千企录第二册1本<\/br>*二星圈子加入权限","recommend":0,"price":"249","apply_pay_id":"33","circle_level":1,"btn":2},{"id":10,"name":"年会员","desc":"*每天沟通40人，合作权限12个月<\/br>*千企录第二册1本<\/br>*千企录第三册1本（印上）<\/br>*三星圈子加入权限","recommend":1,"price":"499.00","apply_pay_id":"22222","circle_level":1,"btn":3,"premium_pirce":"370.00"}]
     */
    private UsersBean users;
    private List<VipBean> vip;

    public UsersBean getUsers() {
        return users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public List<VipBean> getVip() {
        return vip;
    }

    public void setVip(List<VipBean> vip) {
        this.vip = vip;
    }

    public static class UsersBean {
        /**
         * is_vip : 1
         * head_pic :
         * realname : 爱
         * mobile : 18806171210
         * dateMsg : 你的会员权限将在2019-01-01日到期
         */

        private int is_vip;
        private String head_pic;
        private String realname;
        private String mobile;
        private String dateMsg;

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDateMsg() {
            return dateMsg;
        }

        public void setDateMsg(String dateMsg) {
            this.dateMsg = dateMsg;
        }
    }

    public static class VipBean {
        /**
         * id : 9
         * name : 季会员
         * desc : *每天沟通30人，合作权限3个月</br>*千企录第二册1本</br>*二星圈子加入权限
         * recommend : 0
         * price : 249
         * apply_pay_id : 33
         * circle_level : 1
         * btn : 2
         * premium_pirce : 370.00
         */
        public int getIs_new_user() {
            return is_new_user;
        }

        public void setIs_new_user(int is_new_user) {
            this.is_new_user = is_new_user;
        }

        /**
         * users : {"is_vip":1,"head_pic":"","realname":"爱","mobile":"18806171210","dateMsg":"你的会员权限将在2019-01-01日到期"}
         * vip : [{"id":9,"name":"季会员","desc":"*每天沟通30人，合作权限3个月<\/br>*千企录第二册1本<\/br>*二星圈子加入权限","recommend":0,"price":"249","apply_pay_id":"33","circle_level":1,"btn":2},{"id":10,"name":"年会员","desc":"*每天沟通40人，合作权限12个月<\/br>*千企录第二册1本<\/br>*千企录第三册1本（印上）<\/br>*三星圈子加入权限","recommend":1,"price":"499.00","apply_pay_id":"22222","circle_level":1,"btn":3,"premium_pirce":"370.00"}]
         */
        private int is_new_user;
        private int id;
        private String name;
        private String desc;
        private int recommend;
        private String price;
        private String apply_pay_id;
        private int circle_level;
        private int btn;
        private String premium_price;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public int getCircle_level() {
            return circle_level;
        }

        public void setCircle_level(int circle_level) {
            this.circle_level = circle_level;
        }

        public int getBtn() {
            return btn;
        }

        public void setBtn(int btn) {
            this.btn = btn;
        }
        public String getPremium_pirce() {
            return premium_price;
        }

        public void setPremium_pirce(String premium_pirce) {
            this.premium_price = premium_pirce;
        }
    }
}
