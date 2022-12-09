package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/10/9.
 */

public class GroupMemberManageBean {

    /**
     * user_id : 141
     * realname : 徐东东
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/37258482.jpg
     * identity : 2
     * last_login : 1666272000
     * company : 企鹊桥
     * position : 服务经理
     * is_black : 0
     * pinyin : X
     */

    private int user_id;
    private String realname;
    private String head_pic;
    private int identity;
    private long last_login;
    private String company;
    private String position;
    private int is_black;
    private String pinyin;
    private boolean isManage;

    public boolean isManage() {
        return isManage;
    }

    public void setManage(boolean manage) {
        isManage = manage;
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

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
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

    public int getIs_black() {
        return is_black;
    }

    public void setIs_black(int is_black) {
        this.is_black = is_black;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
