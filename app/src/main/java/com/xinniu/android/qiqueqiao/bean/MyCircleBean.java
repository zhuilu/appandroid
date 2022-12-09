package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description : 我都圈子
 */

public class MyCircleBean {
    /**
     * id : 14
     * name : 测试3
     * head_pic :
     * num : 2
     * status : 1
     * create_time : 1512631373
     * category : 37
     * level : 1
     * is_top : 0
     * sort_order : 50
     * user_id : 104
     * notice : 123123
     123123
     * realname : 李四
     * position : null
     * company :
     * share_url : q.qiqueqiao.com/share/circle/14
     */

    private int id;
    private String name;
    private String head_pic;
    private int num;
    private int status;
    private long create_time;
    private int category;
    private int level;
    private int is_top;
    private int sort_order;
    private int user_id;
    private String notice;
    private String realname;
    private Object position;
    private String company;
    private String share_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time * 1000;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
