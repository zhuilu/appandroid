package com.xinniu.android.qiqueqiao.bean;

public class WalletDetailBean {

    /**
     * id : 23
     * disburse_amount : 20.00
     * income_amount : 0.00
     * event_type : 1
     * event_status : 1
     * order_sn : A2019071111404269871717
     * create_time : 1562840426
     * guarantee_order_sn :
     * guarantee_id : 0
     * pay_type : 1
     * handling_fee : 0.20
     * actual_amount_achieved : 19.80
     * withdraw_status : 0
     * reward_id：1
     * reward_order_sn：000000
     * received_id:9
     */

    private int id;
    private String disburse_amount;
    private String income_amount;
    private int event_type;
    private int event_status;
    private String order_sn;
    private long create_time;
    private String guarantee_order_sn;
    private int guarantee_id;
    private int pay_type;
    private String handling_fee;
    private String actual_amount_achieved;
    private int withdraw_status;
    private int reward_id = -1;
    private String reward_order_sn = "";
    private int received_id = -1;

    public int getReceived_id() {
        return received_id;
    }

    public void setReceived_id(int received_id) {
        this.received_id = received_id;
    }

    public int getReward_id() {
        return reward_id;
    }

    public void setReward_id(int reward_id) {
        this.reward_id = reward_id;
    }

    public String getReward_order_sn() {
        return reward_order_sn;
    }

    public void setReward_order_sn(String reward_order_sn) {
        this.reward_order_sn = reward_order_sn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisburse_amount() {
        return disburse_amount;
    }

    public void setDisburse_amount(String disburse_amount) {
        this.disburse_amount = disburse_amount;
    }

    public String getIncome_amount() {
        return income_amount;
    }

    public void setIncome_amount(String income_amount) {
        this.income_amount = income_amount;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public int getEvent_status() {
        return event_status;
    }

    public void setEvent_status(int event_status) {
        this.event_status = event_status;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getGuarantee_order_sn() {
        return guarantee_order_sn;
    }

    public void setGuarantee_order_sn(String guarantee_order_sn) {
        this.guarantee_order_sn = guarantee_order_sn;
    }

    public int getGuarantee_id() {
        return guarantee_id;
    }

    public void setGuarantee_id(int guarantee_id) {
        this.guarantee_id = guarantee_id;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(String handling_fee) {
        this.handling_fee = handling_fee;
    }

    public String getActual_amount_achieved() {
        return actual_amount_achieved;
    }

    public void setActual_amount_achieved(String actual_amount_achieved) {
        this.actual_amount_achieved = actual_amount_achieved;
    }

    public int getWithdraw_status() {
        return withdraw_status;
    }

    public void setWithdraw_status(int withdraw_status) {
        this.withdraw_status = withdraw_status;
    }
}
