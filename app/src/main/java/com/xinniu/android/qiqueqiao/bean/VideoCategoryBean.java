package com.xinniu.android.qiqueqiao.bean;

public class VideoCategoryBean {

    /**
     * id : 21
     * name : 课堂分类
     * img : http://timg.qiqueqiao.com/category/2019/05-06/5ccfa1cb64d92.png
     */

    private int id;
    private String name;
    private String img;
    private boolean Check;

    public VideoCategoryBean(int id, String name, String img, boolean check) {
        this.id = id;
        this.name = name;
        this.img = img;
        Check = check;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
