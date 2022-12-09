package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2019/1/9.
 */

public class GoseeBillBean {

    /**
     * id : 5
     * title : 123123
     * address : 是范德萨地方
     * start_time : 1546304400
     * end_time : 1546344000
     * realname : 曾杨
     * mobile : 18605811452
     * signUp_time : 1545990960
     * status : 1
     * ticket_number : 100000000002
     * event_url : https://q.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=5
     */

    private int id;
    private String title;
    private String address;
    private long start_time;
    private long end_time;
    private String realname;
    private String mobile;
    private long signUp_time;
    private int status;
    private String ticket_number;
    private String event_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getSignUp_time() {
        return signUp_time;
    }

    public void setSignUp_time(long signUp_time) {
        this.signUp_time = signUp_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }
}
