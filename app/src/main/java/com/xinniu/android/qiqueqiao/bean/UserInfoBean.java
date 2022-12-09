package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2017/12/11.
 */

public class UserInfoBean {
    public int user_id = 0;
    public String rong_token = "";

    public String token = "";
    public int status = 0;
    private int newcomer_package;

    public int getNewcomer_package() {
        return newcomer_package;
    }

    public void setNewcomer_package(int newcomer_package) {
        this.newcomer_package = newcomer_package;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRong_token() {
        return rong_token;
    }

    public void setRong_token(String rong_token) {
        this.rong_token = rong_token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
