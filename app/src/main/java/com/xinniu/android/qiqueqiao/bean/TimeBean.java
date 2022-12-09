package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class TimeBean {

    /**
     * list : [{"reservation_time":1558022400}]
     * today_time : 1557417600
     */

    private long today_time;
    private List<ListBean> list;

    public long getToday_time() {
        return today_time;
    }

    public void setToday_time(long today_time) {
        this.today_time = today_time;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * reservation_time : 1558022400
         * count :2
         */

        private long reservation_time;
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getReservation_time() {
            return reservation_time;
        }

        public void setReservation_time(long reservation_time) {
            this.reservation_time = reservation_time;
        }
    }
}
