package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public class CircleInfobean {


    /**
     * id : 5
     * name : 互联网商务合作圈子
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5a447d72a7f51.jpg
     * status : 1
     * user_id : 2
     * introduction : null
     * userList : [{"user_id":2,"realname":"fan","nickname":"1","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/81426937.jpg"},{"user_id":114,"realname":null,"nickname":"企鹊桥0444107615","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/default1.jpg"},{"user_id":148,"realname":"张亚","nickname":"企鹊桥2652276151","head_pic":"http://img.qiqueqiao.com/img/20171228/30594296.jpg"},{"user_id":112,"realname":"秦磊","nickname":"企鹊桥2366837165","head_pic":"http://img.qiqueqiao.com/img/20171219/22903005.jpg"},{"user_id":126,"realname":"李金付","nickname":"金付@企鹊桥掌柜","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg"},{"user_id":105,"realname":"莫言","nickname":"105","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72818197.jpg"},{"user_id":147,"realname":"刘超","nickname":"企鹊桥0012392603","head_pic":"http://img.qiqueqiao.com/img/20171222/44046986.jpg"},{"user_id":104,"realname":"东北猫","nickname":"104","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/89087226.jpg"},{"user_id":151,"realname":"刘超","nickname":"企鹊桥7845884609","head_pic":"http://img.qiqueqiao.com/img/20171226/7379159.jpg"},{"user_id":149,"realname":"青风","nickname":"企鹊桥5558456122","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/2089035.jpg"}]
     * is_admin : 0
     * is_top : 0
     * is_receive : 0
     */

    private int id;
    private String name;
    private String head_pic;
    private int status;
    private int user_id;
    private String introduction;
    private int is_admin;
    private int is_top;
    private int is_receive;
    private List<UserListBean> userList;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getIs_receive() {
        return is_receive;
    }

    public void setIs_receive(int is_receive) {
        this.is_receive = is_receive;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * user_id : 2
         * realname : fan
         * nickname : 1
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/81426937.jpg
         */

        private int user_id;
        private String realname;
        private String nickname;
        private String head_pic;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
