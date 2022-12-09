package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class ActivityColumnListBean {

    private List<BannerBean> banner=new ArrayList<>();
    private List<ListBean> list=new ArrayList<>();

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class BannerBean {
        /**
         * name : 活动banner
         * img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/category/2019/09-23/5d888c7560b45.png
         * url :
         */

        private String name;
        private String img;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ListBean {
        /**
         * title : 精彩推荐
         * content_id : 9,10
         * activity_list : [{"id":9,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c2e04df49b67.png","title":"123123123123123123123","city":52,"address":"手动阀手动阀","registration_time":1546444800,"start_time":1546617600,"end_time":1546617600,"user_id":1417,"registration_num":1,"create_time":1546519941,"status":1,"check_in_qrcode":"","category_id":141,"is_top":0,"detail_url":null,"city_name":"北京","province_name":"北京","corporate_name":"北京百分点信息科技有限公司"},{"id":10,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c2e060cf022b.png","title":"撒旦发射点发射点发射点发射点发","city":49,"address":"啊手动阀手动阀手动阀","registration_time":1548777600,"start_time":1547654400,"end_time":1547740800,"user_id":1417,"registration_num":100,"create_time":1546520078,"status":1,"check_in_qrcode":"","category_id":140,"is_top":0,"detail_url":null,"city_name":"芜湖","province_name":"安徽","corporate_name":"北京百分点信息科技有限公司"}]
         */

        private String title;
        private String content_id;
        private List<ActivityListBean> activity_list=new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public List<ActivityListBean> getActivity_list() {
            return activity_list;
        }

        public void setActivity_list(List<ActivityListBean> activity_list) {
            this.activity_list = activity_list;
        }

        public static class ActivityListBean {
            /**
             * id : 9
             * poster : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c2e04df49b67.png
             * title : 123123123123123123123
             * city : 52
             * address : 手动阀手动阀
             * registration_time : 1546444800
             * start_time : 1546617600
             * end_time : 1546617600
             * user_id : 1417
             * registration_num : 1
             * create_time : 1546519941
             * status : 1
             * check_in_qrcode :
             * category_id : 141
             * is_top : 0
             * detail_url : null
             * city_name : 北京
             * province_name : 北京
             * corporate_name : 北京百分点信息科技有限公司
             * url:
             */

            private int id;
            private String poster;
            private String title;
            private int city;
            private String address;
            private int registration_time;
            private int start_time;
            private int end_time;
            private int user_id;
            private int registration_num;
            private int create_time;
            private int status;
            private String check_in_qrcode;
            private int category_id;
            private int is_top;
            private String detail_url;
            private String city_name;
            private String province_name;
            private String corporate_name;
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            public int getRegistration_time() {
                return registration_time;
            }

            public void setRegistration_time(int registration_time) {
                this.registration_time = registration_time;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCheck_in_qrcode() {
                return check_in_qrcode;
            }

            public void setCheck_in_qrcode(String check_in_qrcode) {
                this.check_in_qrcode = check_in_qrcode;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getIs_top() {
                return is_top;
            }

            public void setIs_top(int is_top) {
                this.is_top = is_top;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
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

            public String getCorporate_name() {
                return corporate_name;
            }

            public void setCorporate_name(String corporate_name) {
                this.corporate_name = corporate_name;
            }
        }
    }
}
