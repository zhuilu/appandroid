package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class GuaranteeOrderBean {

    /**
     * list : [{"id":11,"order_sn":"H2019070404341897972571","initiate_user_id":1376,"party_a_user_id":1376,"party_b_user_id":1276,"text":"第一次交易内容","estimated_amount":"1.00","status":2,"recharge_status":0,"billing_status":0,"refund_status":0,"cancel_user_id":0,"create_time":1562234189,"agree_time":0,"end_time":0,"is_initiate":1,"remaining_amount":"1.00","settlement_amount":0,"refund_amount":0}]
     * hasMore : 0
     */

    private int hasMore;
    private List<ListBean> list;

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 11
         * order_sn : H2019070404341897972571
         * initiate_user_id : 1376
         * party_a_user_id : 1376
         * party_b_user_id : 1276
         * text : 第一次交易内容
         * estimated_amount : 1.00
         * status : 2
         * recharge_status : 0
         * billing_status : 0
         * refund_status : 0
         * cancel_user_id : 0
         * create_time : 1562234189
         * agree_time : 0
         * end_time : 0
         * is_initiate : 1
         * remaining_amount : 1.00
         * settlement_amount : 0
         * refund_amount : 0
         *  "obj_user_name": "戴超",
         */

        private int id;
        private String order_sn;
        private int initiate_user_id;
        private int party_a_user_id;
        private int party_b_user_id;
        private String text;
        private String estimated_amount;
        private int status;
        private int recharge_status;
        private int billing_status;
        private int refund_status;
        private int cancel_user_id;
        private long create_time;
        private long agree_time;
        private long end_time;
        private int is_initiate;
        private String remaining_amount;
        private String settlement_amount;
        private String refund_amount;
        private String obj_user_name;

        public String getObj_user_name() {
            return obj_user_name;
        }

        public void setObj_user_name(String obj_user_name) {
            this.obj_user_name = obj_user_name;
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

        public int getInitiate_user_id() {
            return initiate_user_id;
        }

        public void setInitiate_user_id(int initiate_user_id) {
            this.initiate_user_id = initiate_user_id;
        }

        public int getParty_a_user_id() {
            return party_a_user_id;
        }

        public void setParty_a_user_id(int party_a_user_id) {
            this.party_a_user_id = party_a_user_id;
        }

        public int getParty_b_user_id() {
            return party_b_user_id;
        }

        public void setParty_b_user_id(int party_b_user_id) {
            this.party_b_user_id = party_b_user_id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getEstimated_amount() {
            return estimated_amount;
        }

        public void setEstimated_amount(String estimated_amount) {
            this.estimated_amount = estimated_amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getRecharge_status() {
            return recharge_status;
        }

        public void setRecharge_status(int recharge_status) {
            this.recharge_status = recharge_status;
        }

        public int getBilling_status() {
            return billing_status;
        }

        public void setBilling_status(int billing_status) {
            this.billing_status = billing_status;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public int getCancel_user_id() {
            return cancel_user_id;
        }

        public void setCancel_user_id(int cancel_user_id) {
            this.cancel_user_id = cancel_user_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getAgree_time() {
            return agree_time;
        }

        public void setAgree_time(long agree_time) {
            this.agree_time = agree_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public int getIs_initiate() {
            return is_initiate;
        }

        public void setIs_initiate(int is_initiate) {
            this.is_initiate = is_initiate;
        }

        public String getRemaining_amount() {
            return remaining_amount;
        }

        public void setRemaining_amount(String remaining_amount) {
            this.remaining_amount = remaining_amount;
        }

        public String getSettlement_amount() {
            return settlement_amount;
        }

        public void setSettlement_amount(String settlement_amount) {
            this.settlement_amount = settlement_amount;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }
    }
}
