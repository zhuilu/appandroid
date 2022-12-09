package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/5/11.
 */

public class CompanyUsersBean {

    /**
     * user_id : 1
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg
     * realname : 12
     * position : 1111
     * is_v : 0
     */

    private int user_id;
    private String head_pic;
    private String realname;
    private String position;
    private int is_v;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getIs_v() {
        return is_v;
    }

    public void setIs_v(int is_v) {
        this.is_v = is_v;
    }
}
