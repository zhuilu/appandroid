package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2019/2/13.
 */

public class RecommendBean {

    /**
     * tag : 有合作意向
     * user_id : 1320
     * company : 技术部
     * realname : 高手
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/71576538.jpg
     * is_v : 1
     * position : 测试大佬
     * friend_status : 0
     */

    private String tag;
    private int user_id;
    private String company;
    private String realname;
    private String head_pic;
    private int is_v;
    private String position;
    private int friend_status = 0;

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
