package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2019/1/8.
 */

public class MyActListBean {

    /**
     * list : [{"id":5,"poster":"http://timg.qiqueqiao.com/activity/2018/12-29/5c2732c4bc8c3.png","title":"123123","city_name":"北京","province_name":"北京","start_time":1546304400,"status":1,"end_time":1546344000},{"id":3,"poster":"http://timg.qiqueqiao.com/activity/2018/12-29/5c2732d171e8b.png","title":"活动测试1","city_name":"合肥","province_name":"安徽","start_time":1546012800,"status":1,"end_time":1546084800}]
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
         * id : 5
         * poster : http://timg.qiqueqiao.com/activity/2018/12-29/5c2732c4bc8c3.png
         * title : 123123
         * city_name : 北京
         * province_name : 北京
         * start_time : 1546304400
         * status : 1
         * end_time : 1546344000
         */

        private int id;
        private String poster;
        private String title;
        private String city_name;
        private String province_name;
        private long start_time;
        private int status;
        private long end_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }
    }
}
