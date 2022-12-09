package com.xinniu.android.qiqueqiao.bean;

public class PayDetailBean {

    /**
     * income_amount : 5000.23
     * status : 1
     * pay_type : 1
     * picture_proof : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/99842292.jpg
     */

    private String income_amount;
    private int status;
    private int pay_type;
    private String picture_proof;

    public String getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(String income_amount) {
        this.income_amount = income_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPicture_proof() {
        return picture_proof;
    }

    public void setPicture_proof(String picture_proof) {
        this.picture_proof = picture_proof;
    }
}
