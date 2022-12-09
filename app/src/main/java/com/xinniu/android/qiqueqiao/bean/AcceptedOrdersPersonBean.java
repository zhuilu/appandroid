package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class AcceptedOrdersPersonBean {

    /**
     * title : 找合作
     * status : 1
     * total_amount : 400.00
     * number : 2
     * amount : 200.00
     * order_sn : R2019072424390003151445
     * numberOrders : 2
     * settlementNumber : 0
     * lockingShare : 2
     * remaining_number : 0
     * taking_order_list : [{"user_id":1442,"realname":"张莉莉","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/37462058.jpg","position":"会记三个","company":"刚刚","status":0,"is_settlement":0,"id":4,"amount":"200.00","is_cancel":0,"is_service":0},{"user_id":1044,"realname":"画江湖","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/23549225.jpg","position":"经理","company":"你最近","status":0,"is_settlement":0,"id":2,"amount":"200.00","is_cancel":0,"is_service":0}]
     */

    private String title;
    private int status;
    private String total_amount;
    private int number;
    private String amount;
    private String order_sn;
    private int numberOrders;
    private int settlementNumber;
    private int lockingShare;
    private int remaining_number;
    private List<TakingOrderListBean> taking_order_list;
    private int is_vip;
    private int refund_number;

    public int getRefund_number() {
        return refund_number;
    }

    public void setRefund_number(int refund_number) {
        this.refund_number = refund_number;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(int numberOrders) {
        this.numberOrders = numberOrders;
    }

    public int getSettlementNumber() {
        return settlementNumber;
    }

    public void setSettlementNumber(int settlementNumber) {
        this.settlementNumber = settlementNumber;
    }

    public int getLockingShare() {
        return lockingShare;
    }

    public void setLockingShare(int lockingShare) {
        this.lockingShare = lockingShare;
    }

    public int getRemaining_number() {
        return remaining_number;
    }

    public void setRemaining_number(int remaining_number) {
        this.remaining_number = remaining_number;
    }

    public List<TakingOrderListBean> getTaking_order_list() {
        return taking_order_list;
    }

    public void setTaking_order_list(List<TakingOrderListBean> taking_order_list) {
        this.taking_order_list = taking_order_list;
    }

    public static class TakingOrderListBean {
        /**
         * user_id : 1442
         * realname : 张莉莉
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/37462058.jpg
         * position : 会记三个
         * company : 刚刚
         * status : 0
         * is_settlement : 0
         * id : 4
         * amount : 200.00
         * is_cancel : 0
         * is_service : 0
         * refund_number:1
         * remaining_number:1
         */

        private int user_id;
        private String realname;
        private String head_pic;
        private String position;
        private String company;
        private int status;
        private int is_settlement;
        private int id;
        private String amount;
        private int is_cancel;
        private int is_service;
        private int is_vip;


        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_settlement() {
            return is_settlement;
        }

        public void setIs_settlement(int is_settlement) {
            this.is_settlement = is_settlement;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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
    }
}
