package com.xinniu.android.qiqueqiao.bean;

import androidx.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

/**
 * Created by lzq on 2017/12/19.
 */
@Entity
public class OtherUserInfo {

    /**
     * user_id : 1
     * realname : 张三
     * head_pic : http://img.qiqueqiao.com/sys/default.jpg
     * company : 杭州摆渡文化传媒有限公司
     * position : 程序猿
     * company_name : 礼品赞助
     * follow_status : 1
     *   "is_vip": 2
     */
    @Id
    private Long id;
    private int user_id;
    private String realname;
    private String head_pic;
    private String company="";
    private String position;
    private String company_name;
    private int follow_status;

    private String is_vip;
    private String is_corporate_vip;





    @Generated(hash = 1835811945)
    public OtherUserInfo(Long id, int user_id, String realname, String head_pic,
            String company, String position, String company_name, int follow_status,
            String is_vip, String is_corporate_vip) {
        this.id = id;
        this.user_id = user_id;
        this.realname = realname;
        this.head_pic = head_pic;
        this.company = company;
        this.position = position;
        this.company_name = company_name;
        this.follow_status = follow_status;
        this.is_vip = is_vip;
        this.is_corporate_vip = is_corporate_vip;
    }

    @Generated(hash = 1977033426)
    public OtherUserInfo() {
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIs_vip() {
        return this.is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getIs_corporate_vip() {
        return this.is_corporate_vip;
    }

    public void setIs_corporate_vip(String is_corporate_vip) {
        this.is_corporate_vip = is_corporate_vip;
    }

}
