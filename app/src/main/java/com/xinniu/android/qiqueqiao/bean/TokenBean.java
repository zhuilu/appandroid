package com.xinniu.android.qiqueqiao.bean;

public class TokenBean {

    /**
     * token : 4c59b2cf8dbe4577a23f984c4b8256d4
     */

    private String token;
    private int status;//-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
