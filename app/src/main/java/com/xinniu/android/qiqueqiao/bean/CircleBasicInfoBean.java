package com.xinniu.android.qiqueqiao.bean;


/**
 * Created by yuchance on 2018/5/14.
 * 圈子基本消息
 */
public class CircleBasicInfoBean {


    /**
     * id : 5
     * name : 测试2
     * head_pic : http://img.qiqueqiao.com/category/2017/12-12/5a2f88c7914ba.jpg
     * num : 81
     * status : 1
     * create_time : 1511935954
     * category : 0
     * level : 2
     * is_top : 1
     * sort_order : 50
     * user_id : 3
     * notice : 群消息
     * introduction : null
     * share_url : q.qiqueqiao.com/share/circle/5
     */
    private Long mId;
    private int id;
    private String name;
    private String head_pic;
    private int num;
    private int status;
    private int create_time;
    private int category;
    private int level;
    private int is_top;
    private int sort_order;

    private int user_id;
    private String notice;
    private Object introduction;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
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

    public Object getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Object introduction) {
        this.introduction = introduction;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
