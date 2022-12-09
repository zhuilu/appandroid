package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class GetConfigBean {


    /**
     * default_config : {"sort_order_default":"36","sort_order_name":"最新排序"}
     * nav : [{"id":3,"name":"销售渠道","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09784f20f.png","is_all":0},{"id":4,"name":"线下渠道","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a099e03182.png","is_all":0},{"id":5,"name":"线上流量","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09ace8510.png","is_all":0},{"id":6,"name":"联合活动","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09bd10769.png","is_all":0},{"id":19,"name":"bsdfb","img":"http://timg.qiqueqiao.com/category/2019/03-05/5c7e5052caeb8.png","is_all":0},{"id":7,"name":"广告位","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09c9119c2.png","is_all":0},{"id":8,"name":"科技产品","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09d663e88.png","is_all":0},{"id":9,"name":"礼品赞助","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09e12241f.png","is_all":0},{"id":13,"name":"礼品攒1","img":"http://timg.qiqueqiao.com/category/2019/03-05/5c7e501ec75ce.png","is_all":0},{"id":14,"name":"礼品攒2","img":"http://timg.qiqueqiao.com/category/2019/03-05/5c7e50275b0f8.png","is_all":0},{"id":16,"name":"反对反对反对","img":"http://timg.qiqueqiao.com/category/2018/12-18/5c18de0b23ccf.jpg","is_all":0},{"id":18,"name":"123123","img":"http://timg.qiqueqiao.com/category/2019/03-05/5c7e503a4b65d.png","is_all":0},{"id":10,"name":"全部","img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09eb3463d.png","is_all":1}]
     * banner : [{"img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a08ce5b054.png","url":"http://t.qiqueqiao.com/resource/pages/luckyDraw/luckyDraw.html?needLogin=1"},{"img":"http://timg.qiqueqiao.com/category/2018/06-29/5b35cda001dac.png","url":"qiqueqiao://www.xinniu.com/invitation"},{"img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a10b9ce8ec.jpg","url":"http://t.qiqueqiao.com/resource/activity/activity.html?isApp=1&needLogin=1"},{"img":"http://timg.qiqueqiao.com/category/2019/01-11/5c385237c7334.png","url":"http://t.qiqueqiao.com/resource/pages/luckyDraw/luckyDraw.html?needLogin=1"}]
     * scrollingInfo : [{"id":4721,"position":"22","company":"百分点","provide_remark":"合作中,活动曝光,活动场地,联合举办方,在","p_name":"联合活动"},{"id":4717,"position":"i1185","company":"企鹊桥","provide_remark":"","p_name":"销售渠道"},{"id":4699,"position":"i1212","company":"企鹊桥","provide_remark":"写字楼资源","p_name":"线下渠道"},{"id":4698,"position":"断断续续小弟","company":"t\u2006t\u2006y","provide_remark":"","p_name":"销售渠道"},{"id":4695,"position":"人事部","company":"河湖","provide_remark":null,"p_name":"销售渠道"},{"id":4693,"position":"i1212","company":"企鹊桥","provide_remark":"haha","p_name":"广告位"},{"id":4687,"position":"i1210","company":"百分点","provide_remark":"","p_name":"销售渠道"},{"id":4684,"position":"会计","company":"百分点","provide_remark":null,"p_name":"销售渠道"},{"id":4683,"position":"会计","company":"百分点","provide_remark":null,"p_name":"销售渠道"},{"id":4682,"position":"对的","company":"企鹊桥","provide_remark":null,"p_name":"销售渠道"}]
     * entranceList : [{"name":"精选资源","img":"http://timg.qiqueqiao.com/category/2019/01-03/5c2db248ceabf.png","url":"qiqueqiao://www.xinniu.com/featuredResources","content":"精选资源精选资源","type":2,"create_time":1546498633,"wechat_url":"/pages/index/viewResource/viewResource?type=1"},{"name":"优质服务","img":"http://timg.qiqueqiao.com/category/2019/04-25/5cc161875b0e8.png","url":"qiqueqiao://www.xinniu.com/homeQualityService","content":"优质服务优质服务","type":4,"create_time":1556177313,"wechat_url":"/pages/index/viewResource/viewResource?type=3"},{"name":"找企业","img":"http://timg.qiqueqiao.com/category/2019/04-25/5cc161b5650e8.png","url":"www.baidu.com","content":"找企业找企业找企业","type":5,"create_time":1556177335,"wechat_url":"/pages/index/viewResource/viewResource?type=4"}]
     * event : [{"id":26,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/41710155.jpg","title":"活动后台发布测试11111111111111","city_name":"嘉峪关","province_name":"甘肃","create_time":1548864000,"url":"http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=26"},{"id":27,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/41202292.jpg","title":"活动发布测试2222222222222活动发布测试2222222222222活动发布测试2222222222222活动发布测试2222222222222活动发布测试2222222222222","city_name":"莆田","province_name":"福建","create_time":1548777600,"url":"http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=27"},{"id":25,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c3314259ad75.png","title":"活动修改测试","city_name":"铜陵","province_name":"安徽","create_time":1547827200,"url":"http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=25"}]
     */

    private DefaultConfigBean default_config = null;
    private List<NavBean> nav = new ArrayList<>();
    private List<BannerBean> banner = new ArrayList<>();
    private List<ScrollingInfoBean> scrollingInfo = new ArrayList<>();
    private List<EntranceListBean> entranceList = new ArrayList<>();
    private List<EventBean> event = new ArrayList<>();

    public DefaultConfigBean getDefault_config() {
        return default_config;
    }

    public void setDefault_config(DefaultConfigBean default_config) {
        this.default_config = default_config;
    }

    public List<NavBean> getNav() {
        return nav;
    }

    public void setNav(List<NavBean> nav) {
        this.nav = nav;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<ScrollingInfoBean> getScrollingInfo() {
        return scrollingInfo;
    }

    public void setScrollingInfo(List<ScrollingInfoBean> scrollingInfo) {
        this.scrollingInfo = scrollingInfo;
    }

    public List<EntranceListBean> getEntranceList() {
        return entranceList;
    }

    public void setEntranceList(List<EntranceListBean> entranceList) {
        this.entranceList = entranceList;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public static class DefaultConfigBean {
        /**
         * sort_order_default : 36
         * sort_order_name : 最新排序
         */

        private String sort_order_default;
        private String sort_order_name;

        public String getSort_order_default() {
            return sort_order_default;
        }

        public void setSort_order_default(String sort_order_default) {
            this.sort_order_default = sort_order_default;
        }

        public String getSort_order_name() {
            return sort_order_name;
        }

        public void setSort_order_name(String sort_order_name) {
            this.sort_order_name = sort_order_name;
        }
    }

    public static class NavBean {
        /**
         * id : 3
         * name : 销售渠道
         * img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a09784f20f.png
         * is_all : 0
         */

        private int id;
        private String name;
        private String img;
        private int is_all;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public int getIs_all() {
            return is_all;
        }

        public void setIs_all(int is_all) {
            this.is_all = is_all;
        }
    }

    public static class BannerBean {
        /**
         * img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a08ce5b054.png
         * url : http://t.qiqueqiao.com/resource/pages/luckyDraw/luckyDraw.html?needLogin=1
         */

        private String img;
        private String url;

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

    public static class ScrollingInfoBean {
        /**
         * id : 4721
         * position : 22
         * company : 百分点
         * provide_remark : 合作中,活动曝光,活动场地,联合举办方,在
         * p_name : 联合活动
         */

        private int id;
        private String position;
        private String company;
        private String provide_remark;
        private String p_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getProvide_remark() {
            return provide_remark;
        }

        public void setProvide_remark(String provide_remark) {
            this.provide_remark = provide_remark;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }
    }

    public static class EntranceListBean {
        /**
         * name : 精选资源
         * img : http://timg.qiqueqiao.com/category/2019/01-03/5c2db248ceabf.png
         * url : qiqueqiao://www.xinniu.com/featuredResources
         * content : 精选资源精选资源
         * type : 2
         * create_time : 1546498633
         * wechat_url : /pages/index/viewResource/viewResource?type=1
         */

        private String name;
        private String img;
        private String url;
        private String content;
        private int type;
        private int create_time;
        private String wechat_url;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getWechat_url() {
            return wechat_url;
        }

        public void setWechat_url(String wechat_url) {
            this.wechat_url = wechat_url;
        }
    }

    public static class EventBean {
        /**
         * id : 26
         * poster : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/41710155.jpg
         * title : 活动后台发布测试11111111111111
         * city_name : 嘉峪关
         * province_name : 甘肃
         * create_time : 1548864000
         * url : http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=26
         */

        private int id;
        private String poster;
        private String title;
        private String city_name;
        private String province_name;
        private long create_time;
        private String url;

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

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
