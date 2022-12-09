package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2018/1/9.
 */

public class AppVertion {


    /**
     * id : 2
     * app_name : 企鹊桥
     * app_type : android
     * version : 1.0.2
     * inside_version : 1
     * create_time : 1514540286
     * remark : 发布版本
     * url : http://img.qiqueqiao.com/app/app.apk
     * status : 1
     * is_force_update : 0
     */

    private int id;
    private String app_name;
    private String app_type;
    private String version;
    private int inside_version;
    private int create_time;
    private String remark;
    private String url;
    private int status;
    private int is_force_update;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getInside_version() {
        return inside_version;
    }

    public void setInside_version(int inside_version) {
        this.inside_version = inside_version;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_force_update() {
        return is_force_update;
    }

    public void setIs_force_update(int is_force_update) {
        this.is_force_update = is_force_update;
    }
}
