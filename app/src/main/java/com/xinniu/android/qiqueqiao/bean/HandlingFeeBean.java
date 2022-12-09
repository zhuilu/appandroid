package com.xinniu.android.qiqueqiao.bean;

public class HandlingFeeBean {

    /**
     * handling_fee : 0.50
     */

    private String handling_fee;//提现返回计算交易手续费
    private int id;//生成交易订单id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(String handling_fee) {
        this.handling_fee = handling_fee;
    }
}
