package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class CashWithdrawalBean {


    /**
     * list : [{"id":2,"withdrawals_amount":"20.00","actual_amount_achieved":"19.80","account_type":1,"status":0,"create_time":1562840426},{"id":1,"withdrawals_amount":"20.00","actual_amount_achieved":"19.80","account_type":1,"status":0,"create_time":1562837578}]
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
         * id : 2
         * withdrawals_amount : 20.00
         * actual_amount_achieved : 19.80
         * account_type : 1
         * status : 0
         * create_time : 1562840426
         * water_bills_id:1
         */

        private int id;
        private String withdrawals_amount;
        private String actual_amount_achieved;
        private int account_type;
        private int status;
        private long create_time;
        private int water_bills_id;

        public int getWater_bills_id() {
            return water_bills_id;
        }

        public void setWater_bills_id(int water_bills_id) {
            this.water_bills_id = water_bills_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWithdrawals_amount() {
            return withdrawals_amount;
        }

        public void setWithdrawals_amount(String withdrawals_amount) {
            this.withdrawals_amount = withdrawals_amount;
        }

        public String getActual_amount_achieved() {
            return actual_amount_achieved;
        }

        public void setActual_amount_achieved(String actual_amount_achieved) {
            this.actual_amount_achieved = actual_amount_achieved;
        }

        public int getAccount_type() {
            return account_type;
        }

        public void setAccount_type(int account_type) {
            this.account_type = account_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }
    }
}
