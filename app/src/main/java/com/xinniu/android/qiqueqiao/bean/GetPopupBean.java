package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/9/25.
 */

public class GetPopupBean {

    /**
     * id : 3
     * name : 测试
     * type : 2
     * start_time : 1537804800
     * end_time : 1538038800
     * img_path : http://timg.qiqueqiao.com/category/2018/09-25/5ba9a9a41af7c.png
     * jump_url : www.baidu.com
     * ctime : 2018-09-25 11:21:20
     * status : 1
     */

    private int id;
    private String name;
    private int type;
    private int start_time;
    private int end_time;
    private String img_path;
    private String jump_url;
    private String ctime;
    private int status;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
