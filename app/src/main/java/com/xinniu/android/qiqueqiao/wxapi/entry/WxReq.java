package com.xinniu.android.qiqueqiao.wxapi.entry;

/**
 * Created by qinlei
 * Created on 2017/12/14
 * Created description : 发起微信支付的实体类（为了使用更直观）
 */

public class WxReq {
    private String appid;          // 应用ID
    private String partnerid;      // 商户号
    private String prepayid;       // 预支付交易会话ID
    private String _package;       // 扩展字段
    private String noncestr;       // 随机字符串
    private String timestamp;      // 时间戳
    private String key;            // 签名

    public WxReq() {
    }

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

    public String get_package() {
        return _package;
    }

    public void set_package(String _package) {
        this._package = _package;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 注意生成签名按照ASCII码排序
     * @return
     */
    @Override
    public String toString() {
        return "appid=" + appid +
                "&noncestr=" + noncestr +
                "&package=" + _package +
                "&partnerid=" + partnerid +
                "&prepayid=" + prepayid +
                "&timestamp=" + timestamp +
                "&key=" + key;
    }
}
