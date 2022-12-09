package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/10/15.
 */

public class GroupNoticeBean {

    /**
     * id : 9
     * circle_id : 6
     * realname : 徐东东
     * content : 群里的小子都听着
     * company : Ali
     * position : iOS 开发
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/37258482.jpg
     * create_time : 1517479900
     */

    private int id;
    private int circle_id;
    private String realname;
    private String content;
    private String company;
    private String position;
    private String head_pic;
    private int create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
