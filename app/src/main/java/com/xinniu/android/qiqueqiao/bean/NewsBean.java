package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2017/12/25.
 */

public class NewsBean {

    /**
     * id : 22
     * content : 22232312213213213
     * create_time : 1506647623
     * status : 1
     */

    private int id;
    private String content;
    private long create_time;
    private int status;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time * 1000;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
