package com.xinniu.android.qiqueqiao.bean;

public class CaseBean {

    /**
     * id : 2
     * service_id : 10
     * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
     * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg
     * title : 案例第二次
     * details : 案例第一次案例第一次案例第一次案例第一次案例第一次案例第一次案例第一次案例第一次案例第一次
     */

    private int id;
    private int service_id;
    private String images;
    private String thumb_img;
    private String title;
    private String details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
