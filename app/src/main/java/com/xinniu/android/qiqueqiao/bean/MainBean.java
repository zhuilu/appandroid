package com.xinniu.android.qiqueqiao.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainBean {

    /**
     * banner : [{"img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a08ce5b054.png","url":"http://t.qiqueqiao.com/resource/pages/guarTran/qqqgt.html?needLogin=1"}]
     * nav : [{"id":10,"img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09eb3463d.png","name":"全部","is_all":1}]
     * scrollingInfo : [{"id":30926,"company":"企鹊桥","provide_remark":"效果01","position":"商务群负责人","p_name":"效果渠道"}]
     * recommendNav : [{"id":4,"name":"优质效果渠道","resources":[{"position":"商务群负责人","title":"测试滴滴","status":1,"company":"企鹊桥","user_id":149,"create_time":1563875162,"p_id":8,"is_v":2,"city":383,"realname":"青风","p_img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09d663e88.png","id":30926,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/2089035.jpg","is_vip":0,"talk":5,"is_recommend":0,"need_describe":"","p_name":"效果渠道","view":179,"is_verify":1,"provide_describe":"测试","is_corporate_vip":"0","comment_count":0,"logo":"sssssss"}]},{"id":5,"name":"优质产品","resources":[{"position":"商务群负责人","title":"测试滴滴","status":1,"company":"企鹊桥","user_id":149,"create_time":1563875162,"p_id":8,"is_v":2,"city":383,"realname":"青风","p_img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09d663e88.png","id":30926,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/2089035.jpg","is_vip":0,"talk":5,"is_recommend":0,"need_describe":"","p_name":"效果渠道","view":179,"is_verify":1,"provide_describe":"测试","is_corporate_vip":"0","comment_count":0,"logo":"sssssss"}]}]
     * is_transaction : 0
     * serviceProvider : [{"id":12,"title":"1221212","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","brand":"百分点","corporate_vip":1,"is_corporate_vip":1,"attr":"13138","realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","position":"经理","remark":"211"}]
     * event : [{"id":10,"poster":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c2e060cf022b.png","title":"撒旦发射点发射点发射点发射点发","city_name":"芜湖","province_name":"安徽","create_time":1546520078,"url":"http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=10"}]
     * reward : [{"id":44,"detail":"大兄弟","order_sn":"R2019080202365426869415","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/69493208.jpg","amount":"0.00","realname":"乔俊华","position":"会刚发完","user_id":1417,"is_v":0,"title":"臭臭的","company":"百分点","is_vip":1}]
     * video : [{"id":18,"video_id":"eb30a52f1273449eb2050c36bb3c6709","title":"777","category":[{"name":"视频分类2"},{"name":"视频分类3"}],"poster":"http://timg.qiqueqiao.com/activity_category/2019/04-02/5ca317ffe4e89.png","status":1,"upvote_num":0,"create_time":1554192359,"is_upvote":0,"comment_count":0}]
     */

    private int is_transaction = 1;
    private List<BannerBean> banner;
    private List<NavBean> nav;
    private List<ScrollingInfoBean> scrollingInfo;
    private List<RecommendNavBean> recommendNav = new ArrayList<>();
    private List<ServiceProviderBean> serviceProvider = new ArrayList<>();
    private List<EventBean> event = new ArrayList<>();
    private List<RewardBean> reward = new ArrayList<>();
    private List<VideoBean> video = new ArrayList<>();

    public int getIs_transaction() {
        return is_transaction;
    }

    public void setIs_transaction(int is_transaction) {
        this.is_transaction = is_transaction;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<NavBean> getNav() {
        return nav;
    }

    public void setNav(List<NavBean> nav) {
        this.nav = nav;
    }

    public List<ScrollingInfoBean> getScrollingInfo() {
        return scrollingInfo;
    }

    public void setScrollingInfo(List<ScrollingInfoBean> scrollingInfo) {
        this.scrollingInfo = scrollingInfo;
    }

    public List<RecommendNavBean> getRecommendNav() {
        return recommendNav;
    }

    public void setRecommendNav(List<RecommendNavBean> recommendNav) {
        this.recommendNav = recommendNav;
    }

    public List<ServiceProviderBean> getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(List<ServiceProviderBean> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public List<RewardBean> getReward() {
        return reward;
    }

    public void setReward(List<RewardBean> reward) {
        this.reward = reward;
    }

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }

    public static class BannerBean {
        /**
         * img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a08ce5b054.png
         * url : http://t.qiqueqiao.com/resource/pages/guarTran/qqqgt.html?needLogin=1
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

    public static class NavBean {
        /**
         * id : 10
         * img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a09eb3463d.png
         * name : 全部
         * is_all : 1
         */

        private int id;
        private String img;
        private String name;
        private int is_all;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_all() {
            return is_all;
        }

        public void setIs_all(int is_all) {
            this.is_all = is_all;
        }
    }

    public static class ScrollingInfoBean {
        /**
         * id : 30926
         * company : 企鹊桥
         * provide_remark : 效果01
         * position : 商务群负责人
         * p_name : 效果渠道
         */

        private int id;
        private String company;
        private String provide_remark;
        private String position;
        private String p_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }
    }

    public static class RecommendNavBean {
        /**
         * id : 4
         * name : 优质效果渠道
         * resources : [{"position":"商务群负责人","title":"测试滴滴","status":1,"company":"企鹊桥","user_id":149,"create_time":1563875162,"p_id":8,"is_v":2,"city":383,"realname":"青风","p_img":"http://timg.qiqueqiao.com/category/2018/09-13/5b9a09d663e88.png","id":30926,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/2089035.jpg","is_vip":0,"talk":5,"is_recommend":0,"need_describe":"","p_name":"效果渠道","view":179,"is_verify":1,"provide_describe":"测试","is_corporate_vip":"0","comment_count":0,"logo":"sssssss"}]
         */

        private int id;
        private String name;
        private List<ResourcesBean> resources = new ArrayList<>();
        private String p_name;

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

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

        public List<ResourcesBean> getResources() {
            return resources;
        }

        public void setResources(List<ResourcesBean> resources) {
            this.resources = resources;
        }

        public static class ResourcesBean {
            /**
             * position : 商务群负责人
             * title : 测试滴滴
             * status : 1
             * company : 企鹊桥
             * user_id : 149
             * create_time : 1563875162
             * p_id : 8
             * is_v : 2
             * city : 383
             * realname : 青风
             * p_img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a09d663e88.png
             * id : 30926
             * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/2089035.jpg
             * is_vip : 0
             * talk : 5
             * is_recommend : 0
             * need_describe :
             * p_name : 效果渠道
             * view : 179
             * is_verify : 1
             * provide_describe : 测试
             * is_corporate_vip : 0
             * comment_count : 0
             * logo : sssssss
             */

            private String position;
            private String title;
            private int status;
            private String company;
            private int user_id;
            private long create_time;
            private int p_id;
            private int is_v;
            private int city;
            private String realname;
            private String p_img;
            private int id;
            private String head_pic;
            private int is_vip;
            private int talk;
            private int is_recommend;
            private String need_describe;
            private String p_name;
            private int view;
            private int is_verify;
            private String provide_describe;
            private int is_corporate_vip;
            private int comment_count;
            private String logo;
            private boolean isU = false;//是否是服务经理名下用户
            private int is_cloud_auth = 0;
            private List<CategoryListBean> category_list=new ArrayList<>();


            public int getIs_cloud_auth() {
                return is_cloud_auth;
            }

            public void setIs_cloud_auth(int is_cloud_auth) {
                this.is_cloud_auth = is_cloud_auth;
            }

            public boolean isU() {
                return isU;
            }

            public void setU(boolean u) {
                isU = u;
            }


            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
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

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getP_id() {
                return p_id;
            }

            public void setP_id(int p_id) {
                this.p_id = p_id;
            }

            public int getIs_v() {
                return is_v;
            }

            public void setIs_v(int is_v) {
                this.is_v = is_v;
            }

            public int getCity() {
                return city;
            }

            public void setCity(int city) {
                this.city = city;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getP_img() {
                return p_img;
            }

            public void setP_img(String p_img) {
                this.p_img = p_img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public int getTalk() {
                return talk;
            }

            public void setTalk(int talk) {
                this.talk = talk;
            }

            public int getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(int is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getNeed_describe() {
                return need_describe;
            }

            public void setNeed_describe(String need_describe) {
                this.need_describe = need_describe;
            }

            public String getP_name() {
                return p_name;
            }

            public void setP_name(String p_name) {
                this.p_name = p_name;
            }

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public int getIs_verify() {
                return is_verify;
            }

            public void setIs_verify(int is_verify) {
                this.is_verify = is_verify;
            }

            public String getProvide_describe() {
                return provide_describe;
            }

            public void setProvide_describe(String provide_describe) {
                this.provide_describe = provide_describe;
            }

            public int getIs_corporate_vip() {
                return is_corporate_vip;
            }

            public void setIs_corporate_vip(int is_corporate_vip) {
                this.is_corporate_vip = is_corporate_vip;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public List<CategoryListBean> getCategory_list() {
                return category_list;
            }

            public void setCategory_list(List<CategoryListBean> category_list) {
                this.category_list = category_list;
            }


            public static class CategoryListBean {
                /**
                 * id : 2
                 * title : 需求标签1
                 * list : [{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]
                 */
                private int id;
                private String title;
                private List<ListBean> list;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    /**
                     * id : 11981
                     * name : 线下销售渠道
                     * is_custom : 0
                     * is_type : 0
                     * user_id : 0
                     * sort_order : 1
                     * title_id : 2
                     */

                    private int id;
                    private String name;
                    private int is_custom;
                    private int is_type;
                    private int user_id;
                    private int sort_order;
                    private int title_id;

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

                    public int getIs_custom() {
                        return is_custom;
                    }

                    public void setIs_custom(int is_custom) {
                        this.is_custom = is_custom;
                    }

                    public int getIs_type() {
                        return is_type;
                    }

                    public void setIs_type(int is_type) {
                        this.is_type = is_type;
                    }

                    public int getUser_id() {
                        return user_id;
                    }

                    public void setUser_id(int user_id) {
                        this.user_id = user_id;
                    }

                    public int getSort_order() {
                        return sort_order;
                    }

                    public void setSort_order(int sort_order) {
                        this.sort_order = sort_order;
                    }

                    public int getTitle_id() {
                        return title_id;
                    }

                    public void setTitle_id(int title_id) {
                        this.title_id = title_id;
                    }
                }
            }
        }
    }

    public static class ServiceProviderBean {
        /**
         * id : 12
         * title : 1221212
         * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
         * brand : 百分点
         * corporate_vip : 1
         * is_corporate_vip : 1
         * attr : 13138
         * realname : 曾杨
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * position : 经理
         * remark : 211
         * logo:"ooooooooo"
         */

        private int id;
        private String title;
        private String images;
        private String brand;
        private int corporate_vip;
        private int is_corporate_vip;
        private String attr;
        private String realname;
        private String head_pic;
        private String position;
        private String remark;
        private String logo;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getCorporate_vip() {
            return corporate_vip;
        }

        public void setCorporate_vip(int corporate_vip) {
            this.corporate_vip = corporate_vip;
        }

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class EventBean {
        /**
         * id : 10
         * poster : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5c2e060cf022b.png
         * title : 撒旦发射点发射点发射点发射点发
         * city_name : 芜湖
         * province_name : 安徽
         * create_time : 1546520078
         * url : http://t.qiqueqiao.com/resource/pages/qqqAct/actdetail.html?id=10
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

    public static class RewardBean {
        /**
         * id : 44
         * detail : 大兄弟
         * order_sn : R2019080202365426869415
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/69493208.jpg
         * amount : 0.00
         * realname : 乔俊华
         * position : 会刚发完
         * user_id : 1417
         * is_v : 0
         * title : 臭臭的
         * company : 百分点
         * is_vip : 1
         */

        private int id;
        private String detail;
        private String order_sn;
        private String head_pic;
        private String amount;
        private String realname;
        private String position;
        private int user_id;
        private int is_v;
        private String title;
        private String company;
        private int is_vip;
        private boolean isU = false;//是否是服务经理名下用户

        public boolean isU() {
            return isU;
        }

        public void setU(boolean u) {
            isU = u;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }
    }

    public static class VideoBean {
        /**
         * id : 18
         * video_id : eb30a52f1273449eb2050c36bb3c6709
         * title : 777
         * category : [{"name":"视频分类2"},{"name":"视频分类3"}]
         * poster : http://timg.qiqueqiao.com/activity_category/2019/04-02/5ca317ffe4e89.png
         * status : 1
         * upvote_num : 0
         * create_time : 1554192359
         * is_upvote : 0
         * comment_count : 0
         */

        private int id;
        private String video_id;
        private String title;
        private String poster;
        private int status;
        private int upvote_num;
        private long create_time;
        private int is_upvote;
        private int comment_count;
        private int type;
        private List<CategoryBean> category = new ArrayList<>();

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUpvote_num() {
            return upvote_num;
        }

        public void setUpvote_num(int upvote_num) {
            this.upvote_num = upvote_num;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getIs_upvote() {
            return is_upvote;
        }

        public void setIs_upvote(int is_upvote) {
            this.is_upvote = is_upvote;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            /**
             * name : 视频分类2
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
