package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class BillBean {

    /**
     * list : [{"id":23,"create_time":1562840426,"disburse_amount":"20.00","income_amount":"0.00","event_status":1,"event_type":1},{"id":22,"create_time":1562837578,"disburse_amount":"20.00","income_amount":"0.00","event_status":1,"event_type":1},{"id":13,"create_time":1562746972,"disburse_amount":"0.00","income_amount":"601.00","event_status":2,"event_type":2}]
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
         * id : 23
         * create_time : 1562840426
         * disburse_amount : 20.00
         * income_amount : 0.00
         * event_status : 1
         * event_type : 1
         */

        private int id;
        private long create_time;
        private String disburse_amount;
        private String income_amount;
        private int event_status;
        private int event_type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
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

        public int getEvent_status() {
            return event_status;
        }

        public void setEvent_status(int event_status) {
            this.event_status = event_status;
        }

        public int getEvent_type() {
            return event_type;
        }

        public void setEvent_type(int event_type) {
            this.event_type = event_type;
        }
    }
}
