package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class GroupFriendBean {

    /**
     * group_id : 6
     * user_id : 1373
     * name : 分组1
     * user_list : [{"user_id":1362,"realname":"De","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/default1.jpg","company":"Baidu","position":"i1189","sex":0}]
     * count : 1
     */

    private int group_id;
    private int user_id;
    private String name;
    private int count;
    private List<UserListBean> user_list;

    public GroupFriendBean(int group_id, int user_id, String name, int count) {
        this.group_id = group_id;
        this.user_id = user_id;
        this.name = name;
        this.count = count;
    }

    public GroupFriendBean(int group_id, int user_id, String name, int count, List<UserListBean> user_list) {
        this.group_id = group_id;
        this.user_id = user_id;
        this.name = name;
        this.count = count;
        this.user_list=user_list;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserListBean> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListBean> user_list) {
        this.user_list = user_list;
    }

    public static class UserListBean {
        /**
         * user_id : 1362
         * realname : De
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/default1.jpg
         * company : Baidu
         * position : i1189
         * sex : 0
         * "is_vip": 1
         */

        private int user_id;
        private String realname;
        private String head_pic;
        private String company;
        private String position;
        private int sex;
        private int is_vip;

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }
}
