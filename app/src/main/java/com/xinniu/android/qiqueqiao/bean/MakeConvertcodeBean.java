package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/11/1.
 */

public class MakeConvertcodeBean {

    /**
     * id : 19
     * name : 兑换码购买
     * desc : 兑换码购买一年SVIP
     * recommend : 0
     * price : 0.01
     * month : 12
     * vip_type : svip
     */

    private int id;
    private String name;
    private String desc;
    private int recommend;
    private String price;
    private int month;
    private String vip_type;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getVip_type() {
        return vip_type;
    }

    public void setVip_type(String vip_type) {
        this.vip_type = vip_type;
    }
}
