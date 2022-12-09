package com.xinniu.android.qiqueqiao.utils.wxpay.entry;

/**
 * Created by ql
 * 2017/7/25.
 * Description: 统一下单的实体类（为了使用更直观）
 */

public class WxPay {

    private String appid;           // 应用ID
    private String mch_id;          // 商户号
    private String device_info;     // 设备号（可无）
    private String nonce_str;       // 随机字符串
    private String key;             // 注：用于生成签名
    private String sign_type;       // 签名类型（可无）
    private String body;            // 商品描述
    private String detail;          // 商品详情（可无）
    private String attach;          // 附加数据（可无）
    private String out_trade_no;    // 商户订单号
    private String fee_type;        // 货币类型（可无）
    private int total_fee;          // 总金额
    private String spbill_create_ip;// 终端IP
    private String time_start;      // 交易起始时间（可无）
    private String time_expire;     // 交易结束时间（可无）
    private String goods_tag;       // 商品标记（可无）
    private String notify_url;      // 通知地址
    private String trade_type;      // 交易类型
    private String limit_pay;       // 指定支付方式（可无）

    public WxPay() {
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    @Override
    public String toString() {
        return "appid=" + appid +
                "&attach=" + attach +
                "&body=" + body +
                "&mch_id=" + mch_id +
                "&nonce_str=" + nonce_str +
                "&notify_url=" + notify_url +
                "&out_trade_no=" + out_trade_no +
                "&spbill_create_ip=" + spbill_create_ip +
                "&total_fee=" + total_fee +
                "&trade_type=" + trade_type +
                "&key=" + key;
    }
}
