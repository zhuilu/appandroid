package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2019/2/13.
 */

public class AddressListBean {

    /**
     * hasMore : 0
     * list : [{"tag":"通讯录好友,同事","user_id":1376,"company":"天下第一","realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","is_v":1,"position":"经理","friend_status":0},{"tag":"通讯录好友","user_id":1401,"company":"","realname":"","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/default1.jpg","is_v":0,"position":"","friend_status":0}]
     */

    private int hasMore;
    private List<ListBean> list;
    private String uid_data;

    public String getUid_data() {
        return uid_data;
    }

    public void setUid_data(String uid_data) {
        this.uid_data = uid_data;
    }

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
         * tag : 通讯录好友,同事
         * user_id : 1376
         * company : 天下第一
         * realname : 曾杨
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * is_v : 1
         * position : 经理
         * friend_status : 0
         */

        private String tag;
        private int user_id;
        private String company;
        private String realname;
        private String head_pic;
        private int is_v;
        private String position;
        private int friend_status;
        private String mobile;
        private int type;
        private int is_invite;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_invite() {
            return is_invite;
        }

        public void setIs_invite(int is_invite) {
            this.is_invite = is_invite;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
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

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getFriend_status() {
            return friend_status;
        }

        public void setFriend_status(int friend_status) {
            this.friend_status = friend_status;
        }
    }
}
