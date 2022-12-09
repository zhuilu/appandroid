package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2017/12/14.
 */

public class ExchangeInfoBean {

    /**
     * type : 0
     * info : 1
     * user_info : 信息
     * to_user_info : 信息
     * id : 1
     */

    private int action;
    private int info;
    private String user_info;
    private String to_user_info;
    private int id;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String getTo_user_info() {
        return to_user_info;
    }

    public void setTo_user_info(String to_user_info) {
        this.to_user_info = to_user_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
