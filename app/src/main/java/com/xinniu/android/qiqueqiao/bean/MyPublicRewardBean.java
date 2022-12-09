package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class MyPublicRewardBean {


    /**
     * list : [{"id":4,"order_sn":"R2019072323749037533951","amount":"0.00","status":1,"number":3,"freeze_number":0,"title":"找人测试","remaining_number":"3","numberOrders":0}]
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
         * id : 4
         * order_sn : R2019072323749037533951
         * amount : 0.00
         * status : 1
         * number : 3
         * freeze_number : 0
         * title : 找人测试
         * remaining_number : 3
         * numberOrders : 0
         */

        private int id;
        private String order_sn;
        private String amount;
        private int status;
        private int number;
        private int freeze_number;
        private String title;
        private int remaining_number;
        private int numberOrders;

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRemaining_number() {
            return remaining_number;
        }

        public void setRemaining_number(int remaining_number) {
            this.remaining_number = remaining_number;
        }

        public int getNumberOrders() {
            return numberOrders;
        }

        public void setNumberOrders(int numberOrders) {
            this.numberOrders = numberOrders;
        }
    }
}
