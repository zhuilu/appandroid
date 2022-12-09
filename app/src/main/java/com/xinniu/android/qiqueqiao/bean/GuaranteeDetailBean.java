package com.xinniu.android.qiqueqiao.bean;

public class GuaranteeDetailBean {


    /**
     * id : 5
     * order_sn : H2019070404239334236670
     * initiate_user_id : 1376
     * party_a_user_id : 1376
     * party_b_user_id : 1276
     * text : 第一次交易内容
     * estimated_amount : 5000.23
     * status : 6
     * recharge_status : 0
     * billing_status : 0
     * refund_status : 0
     * cancel_user_id : 1376
     * create_time : 1562223933
     * agree_time : 0
     * end_time : 0
     * obj_user_info : {"user_id":1376,"realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","position":"经理","company":"百分点","mobile":"18605811452","party_a_or_party_b":1}
     * is_initiate : 0
     * remaining_amount : 5000.23
     * now_time : 1562580019
     * settlement_amount : 0
     * refund_amount : 0
     * serving_status:0
     * serving_desc:"iiiii"
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
    private ObjUserInfoBean obj_user_info;
    private int is_initiate;
    private String remaining_amount;
    private long now_time;
    private long refund_time;
    private String settlement_amount;
    private String refund_amount;
    private int serving_status;//0：无客服介入，1：甲方，2：乙方
    private int serving_type;
    private String serving_desc;

    public String getServing_desc() {
        return serving_desc;
    }

    public void setServing_desc(String serving_desc) {
        this.serving_desc = serving_desc;
    }

    public int getServing_type() {
        return serving_type;
    }

    public void setServing_type(int serving_type) {
        this.serving_type = serving_type;
    }

    public int getServing_status() {
        return serving_status;
    }

    public void setServing_status(int serving_status) {
        this.serving_status = serving_status;
    }

    public long getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(long refund_time) {
        this.refund_time = refund_time;
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

    public ObjUserInfoBean getObj_user_info() {
        return obj_user_info;
    }

    public void setObj_user_info(ObjUserInfoBean obj_user_info) {
        this.obj_user_info = obj_user_info;
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

    public long getNow_time() {
        return now_time;
    }

    public void setNow_time(long now_time) {
        this.now_time = now_time;
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

    public static class ObjUserInfoBean {
        /**
         * user_id : 1376
         * realname : 曾杨
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * position : 经理
         * company : 百分点
         * mobile : 18605811452
         * party_a_or_party_b : 1
         */

        private int user_id;
        private String realname;
        private String head_pic;
        private String position;
        private String company;
        private String mobile;
        private int party_a_or_party_b;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getParty_a_or_party_b() {
            return party_a_or_party_b;
        }

        public void setParty_a_or_party_b(int party_a_or_party_b) {
            this.party_a_or_party_b = party_a_or_party_b;
        }
    }
}
