package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2018/1/31.
 */

public class MemberInfoBean {

    /**
     * realname : fan
     * nickname : 1
     * user_id : 2
     * company : 摆渡星空
     * position : Android
     * status : 1
     */

    private String realname;
    private String nickname;
    private int user_id;
    private String company;
    private String position;
    private int status;
    private String head_pic;
    private boolean isCheck;
    private int is_v;

    public int getIs_v() {
        return is_v;
    }

    public void setIs_v(int is_v) {
        this.is_v = is_v;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
