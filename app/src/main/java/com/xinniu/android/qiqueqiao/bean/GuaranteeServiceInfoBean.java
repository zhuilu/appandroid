package com.xinniu.android.qiqueqiao.bean;

public class GuaranteeServiceInfoBean {

    /**
     * user_id : 1044
     * desc : 回家
     * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190711/42050215.jpg,https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190711/70409988.jpg
     * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190711/thumb/42050215_thumb.jpg,https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190711/thumb/70409988_thumb.jpg
     * result_desc : 我不同意退款
     * create_time : 1562816279
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg
     * type : 1
     * status : 1
     */

    private int user_id;
    private String desc;
    private String images;
    private String thumb_img;
    private String result_desc;
    private long create_time;
    private String head_pic;
    private int type;
    private int status;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
