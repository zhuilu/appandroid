package com.xinniu.android.qiqueqiao.bean;

public class RewardOrderBean {

    /**
     * id : 1
     * order_sn : R2019072424489887781173
     * user_id : 1376
     * is_settlement : 0
     * received_status : 0
     * cancel_time : 0
     * is_cancel : 0
     * is_service : 0
     * create_time : 1563962769
     * now_time : 1564108240
     * reward_info : {"amount":"0.01","title":"回合肥","order_sn":"R2019072424489887781173","user_id":1044,"realname":"画江湖","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg","is_v":0,"is_vip":2,"position":"经理","company":"你最近"}
     */

    private int id;
    private String order_sn;
    private int user_id;
    private int is_settlement;
    private int received_status;
    private long cancel_time;
    private int is_cancel;
    private int is_service;
    private long create_time;
    private long now_time;
    private RewardInfoBean reward_info;

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

    public int getIs_settlement() {
        return is_settlement;
    }

    public void setIs_settlement(int is_settlement) {
        this.is_settlement = is_settlement;
    }

    public int getReceived_status() {
        return received_status;
    }

    public void setReceived_status(int received_status) {
        this.received_status = received_status;
    }

    public long getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(long cancel_time) {
        this.cancel_time = cancel_time;
    }

    public int getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(int is_cancel) {
        this.is_cancel = is_cancel;
    }

    public int getIs_service() {
        return is_service;
    }

    public void setIs_service(int is_service) {
        this.is_service = is_service;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getNow_time() {
        return now_time;
    }

    public void setNow_time(long now_time) {
        this.now_time = now_time;
    }

    public RewardInfoBean getReward_info() {
        return reward_info;
    }

    public void setReward_info(RewardInfoBean reward_info) {
        this.reward_info = reward_info;
    }

    public static class RewardInfoBean {
        /**
         * amount : 0.01
         * title : 回合肥
         * order_sn : R2019072424489887781173
         * user_id : 1044
         * realname : 画江湖
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg
         * is_v : 0
         * is_vip : 2
         * position : 经理
         * company : 你最近
         */

        private String amount;
        private String title;
        private String order_sn;
        private int user_id;
        private String realname;
        private String head_pic;
        private int is_v;
        private int is_vip;
        private String position;
        private String company;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
}
