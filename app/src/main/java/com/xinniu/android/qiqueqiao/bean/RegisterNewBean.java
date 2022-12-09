package com.xinniu.android.qiqueqiao.bean;

public class RegisterNewBean {

    /**
     * user_id : 1376
     * token : cfb1a1be430907ca3ccf05f6e305ef39
     * rong_token : tchApst1BbQ0fVTytjFTyFS2WZaozHnMp5uxfa6inMKFl83VSdJA99fADKsu8Q904dc4QIesBBSCGy/fd9kyDA==
     * status : 0
     * city_name : 江西,南昌
     * city_pid:1
     */

    private int user_id;
    private String token;
    private String rong_token;
    private int status;
    private String city_name;
    private int newcomer_package;
    private int city_pid;

    public int getCity_pid() {
        return city_pid;
    }

    public void setCity_pid(int city_pid) {
        this.city_pid = city_pid;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRong_token() {
        return rong_token;
    }

    public void setRong_token(String rong_token) {
        this.rong_token = rong_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
