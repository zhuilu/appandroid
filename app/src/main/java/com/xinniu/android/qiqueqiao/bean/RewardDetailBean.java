package com.xinniu.android.qiqueqiao.bean;

public class RewardDetailBean {

    /**
     * id : 19
     * order_sn : R2019072424489887781173
     * user_id : 1044
     * type_name : 找人
     * title : 回合肥
     * detail : 发广告
     * amount : 0.01
     * total_amount : 0.05
     * number : 5
     * freeze_number : 0
     * status : 1
     * is_pay : 1
     * is_cancel : 0
     * create_time : 1563948988
     * remaining_number : 5
     * realname : 画江湖
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg
     * is_v : 0
     * is_vip : 2
     * position : 经理
     * company : 你最近
     * is_order_taking:0
     */

    private int id;
    private String order_sn;
    private int user_id;
    private String type_name;
    private String title;
    private String detail;
    private String amount;
    private String total_amount;
    private int number;
    private int freeze_number;
    private int status;
    private int is_pay;
    private int is_cancel;
    private long create_time;
    private int remaining_number;
    private String realname;
    private String head_pic;
    private int is_v;
    private int is_vip;
    private String position;
    private String company;
    private int is_cloud_auth;
    private int is_order_taking;
    private String share_url;
    private int is_corporate_vip = 0;

    public int getIs_corporate_vip() {
        return is_corporate_vip;
    }

    public void setIs_corporate_vip(int is_corporate_vip) {
        this.is_corporate_vip = is_corporate_vip;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getIs_order_taking() {
        return is_order_taking;
    }

    public void setIs_order_taking(int is_order_taking) {
        this.is_order_taking = is_order_taking;
    }

    public int getIs_cloud_auth() {
        return is_cloud_auth;
    }

    public void setIs_cloud_auth(int is_cloud_auth) {
        this.is_cloud_auth = is_cloud_auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFreeze_number() {
        return freeze_number;
    }

    public void setFreeze_number(int freeze_number) {
        this.freeze_number = freeze_number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public int getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getRemaining_number() {
        return remaining_number;
    }

    public void setRemaining_number(int remaining_number) {
        this.remaining_number = remaining_number;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getIs_v() {
        return is_v;
    }

    public void setIs_v(int is_v) {
        this.is_v = is_v;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
