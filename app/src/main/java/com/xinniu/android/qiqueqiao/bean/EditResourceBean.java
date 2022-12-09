package com.xinniu.android.qiqueqiao.bean;

import java.io.Serializable;

/**
 * Created by qinlei
 * Created on 2017/12/20
 * Created description :
 */

public class EditResourceBean implements Serializable {

    /**
     * id : 51
     * user_id : 1
     * provide_top : 1
     * provide_remark : 提供备注
     * need_top : 2
     * need_remark : 需求备注
     * cooperation_mode : 16,17
     * cooperation_mode_cn : 联合营销,流量互换
     * need_describe : null
     * provide_describe : null
     * status : 1
     * provide : 75,76,77,78
     * need : 79,80,81,82
     */

    private int id;
    private int user_id;
    private int provide_top;
    private String provide_remark;
    private int need_top;
    private String need_remark;
    private String cooperation_mode;
    private String cooperation_mode_cn;
    private String need_describe;
    private String provide_describe;
    private int status;
    private String provide;
    private String need;
    private String provide_img;
    private String need_img;

    public String getProvide_img() {
        return provide_img;
    }

    public void setProvide_img(String provide_img) {
        this.provide_img = provide_img;
    }

    public String getNeed_img() {
        return need_img;
    }

    public void setNeed_img(String need_img) {
        this.need_img = need_img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProvide_top() {
        return provide_top;
    }

    public void setProvide_top(int provide_top) {
        this.provide_top = provide_top;
    }

    public String getProvide_remark() {
        return provide_remark;
    }

    public void setProvide_remark(String provide_remark) {
        this.provide_remark = provide_remark;
    }

    public int getNeed_top() {
        return need_top;
    }

    public void setNeed_top(int need_top) {
        this.need_top = need_top;
    }

    public String getNeed_remark() {
        return need_remark;
    }

    public void setNeed_remark(String need_remark) {
        this.need_remark = need_remark;
    }

    public String getCooperation_mode() {
        return cooperation_mode;
    }

    public void setCooperation_mode(String cooperation_mode) {
        this.cooperation_mode = cooperation_mode;
    }

    public String getCooperation_mode_cn() {
        return cooperation_mode_cn;
    }

    public void setCooperation_mode_cn(String cooperation_mode_cn) {
        this.cooperation_mode_cn = cooperation_mode_cn;
    }

    public String getNeed_describe() {
        return need_describe;
    }

    public void setNeed_describe(String need_describe) {
        this.need_describe = need_describe;
    }

    public String getProvide_describe() {
        return provide_describe;
    }

    public void setProvide_describe(String provide_describe) {
        this.provide_describe = provide_describe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProvide() {
        return provide;
    }

    public void setProvide(String provide) {
        this.provide = provide;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    @Override
    public String toString() {
        return "EditResourceBean{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", provide_top=" + provide_top +
                ", provide_remark='" + provide_remark + '\'' +
                ", need_top=" + need_top +
                ", need_remark='" + need_remark + '\'' +
                ", cooperation_mode='" + cooperation_mode + '\'' +
                ", cooperation_mode_cn='" + cooperation_mode_cn + '\'' +
                ", need_describe='" + need_describe + '\'' +
                ", provide_describe='" + provide_describe + '\'' +
                ", status=" + status +
                ", provide='" + provide + '\'' +
                ", need='" + need + '\'' +
                '}';
    }
}
