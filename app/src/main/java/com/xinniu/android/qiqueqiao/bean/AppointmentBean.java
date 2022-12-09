package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class AppointmentBean {

    /**
     * user_id : 1376
     * resources_id : 1112
     * p_id : 3
     * reservation_time : ["1598065546","1598065546"]
     * fixed_top_card_num : 4
     */

    private String user_id;
    private String resources_id;
    private String p_id;
    private int fixed_top_card_num;
    private List<Long> reservation_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResources_id() {
        return resources_id;
    }

    public void setResources_id(String resources_id) {
        this.resources_id = resources_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public int getFixed_top_card_num() {
        return fixed_top_card_num;
    }

    public void setFixed_top_card_num(int fixed_top_card_num) {
        this.fixed_top_card_num = fixed_top_card_num;
    }

    public List<Long> getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(List<Long> reservation_time) {
        this.reservation_time = reservation_time;
    }
}
