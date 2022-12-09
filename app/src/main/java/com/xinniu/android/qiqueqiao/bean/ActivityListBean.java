package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class ActivityListBean {

    /**
     * list : [{"id":3,"poster":"http://timg.qiqueqiao.com/activity/2018/12-28/5c2592459392c.png","title":"活动测试1","city":3401,"address":"所发生的富士康","registration_time":1545926400,"start_time":1546012800,"end_time":1546084800,"user_id":1376,"registration_num":100,"create_time":1545966194,"status":1,"category_id":140,"details":"<p>活动开始了<img src=\"/public/upload/ueditor/20181228/1545966191189117.png\" title=\"1545966191189117.png\" alt=\"page-1-个人资料-2.png\"/><\/p>","city_name":"合肥","province_name":"安徽","applicants_num":1}]
     * hasMore : 0
     */

    private int hasMore;
    private List<ListBean> list=new ArrayList<>();

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
         * id : 3
         * poster : http://timg.qiqueqiao.com/activity/2018/12-28/5c2592459392c.png
         * title : 活动测试1
         * city : 3401
         * address : 所发生的富士康
         * registration_time : 1545926400
         * start_time : 1546012800
         * end_time : 1546084800
         * user_id : 1376
         * registration_num : 100
         * create_time : 1545966194
         * status : 1
         * category_id : 140
         * details : <p>活动开始了<img src="/public/upload/ueditor/20181228/1545966191189117.png" title="1545966191189117.png" alt="page-1-个人资料-2.png"/></p>
         * city_name : 合肥
         * province_name : 安徽
         * applicants_num : 1
         */

        private int id;
        private String poster;
        private String title;
        private int city;
        private String address;
        private long registration_time;
        private long start_time;
        private long end_time;
        private int user_id;
        private int registration_num;
        private long create_time;
        private int status;
        private int category_id;
        private String details;
        private String city_name;
        private String province_name;
        private int applicants_num;
        private String url;
        private long curent_time;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCurent_time() {
            return curent_time;
        }

        public void setCurent_time(long curent_time) {
            this.curent_time = curent_time;
        }

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

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getRegistration_time() {
            return registration_time;
        }

        public void setRegistration_time(long registration_time) {
            this.registration_time = registration_time;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getRegistration_num() {
            return registration_num;
        }

        public void setRegistration_num(int registration_num) {
            this.registration_num = registration_num;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
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

        public int getApplicants_num() {
            return applicants_num;
        }

        public void setApplicants_num(int applicants_num) {
            this.applicants_num = applicants_num;
        }
    }
}
