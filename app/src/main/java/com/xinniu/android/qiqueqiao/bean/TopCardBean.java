package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2019/2/18.
 */

public class TopCardBean {

    /**
     * id : 23
     * name : 置顶卡
     * apply_pay_id : com.xinniu.qiqueqiao.topcard.6cny
     * android_price : 6
     * ios_price : 6
     * desc : 置顶卡1张
     * price_desc :
     */

    private int id;
    private String name;
    private String apply_pay_id;
    private String android_price;
    private String ios_price;
    private String desc;
    private String price_desc;

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

    public String getApply_pay_id() {
        return apply_pay_id;
    }

    public void setApply_pay_id(String apply_pay_id) {
        this.apply_pay_id = apply_pay_id;
    }

    public String getAndroid_price() {
        return android_price;
    }

    public void setAndroid_price(String android_price) {
        this.android_price = android_price;
    }

    public String getIos_price() {
        return ios_price;
    }

    public void setIos_price(String ios_price) {
        this.ios_price = ios_price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice_desc() {
        return price_desc;
    }

    public void setPrice_desc(String price_desc) {
        this.price_desc = price_desc;
    }
}
