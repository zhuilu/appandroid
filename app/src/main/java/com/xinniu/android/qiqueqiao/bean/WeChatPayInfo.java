package com.xinniu.android.qiqueqiao.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lzq on 2017/12/22.
 */

public class WeChatPayInfo {

    /**
     * appid : wx430262a62d31dff9
     * partnerid : 1494809442
     * prepayid : wx20171222101201372b09bee80546679504
     * timestamp : 1513908721
     * noncestr : ldgGmDbrEasheAgE
     * package : Sign=WXPay
     * sign : F9215E19506C6BD6D295D1DE1B356026
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private int timestamp;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
