package com.xinniu.android.qiqueqiao.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class IndexNewBean {


    private int hasMore;
    private List<IndexNewBean.ListBean> list;

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public List<IndexNewBean.ListBean> getList() {
        return list;
    }

    public void setList(List<IndexNewBean.ListBean> list) {
        this.list = list;
    }

    /**
     * c_sort : 5
     * realname :
     * id : 7348
     * cooperation_mode_cn : 流量互换
     * title : 测试标题
     * position : 滚滚滚滚
     * company :
     * head_pic : http://img.qiqueqiao.com/sys/default.jpg
     * city : null
     * user_id : 110
     * is_v : 0
     * is_vip : 1
     * view : 0
     * talk : 0
     */

    public static class ListBean {

        private double c_sort;
        private String realname;
        private int id;
        private String cooperation_mode_cn;
        private String title;
        private String position;
        private String company;
        private String head_pic;
        private Object city;
        private int user_id;
        private int is_v;
        private int is_vip;
        private int view;
        private int talk;
        private String need_describe;
        private String provide_describe;
        private boolean isSearchPerch;
        private int comment_count;
        private String need_remark;
        private String provide_remark;
        private long create_time;
        private int is_recommend;
        private int is_corporate_vip = 0;//设置默认值，缓存数据处理
        private boolean isU = false;//是否是服务经理名下用户
        private int is_cloud_auth = 0;
        private String p_name;
        private List<CategoryListBean> category_list = new ArrayList<>();

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

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

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getNeed_remark() {
            return need_remark;
        }

        public void setNeed_remark(String need_remark) {
            this.need_remark = need_remark;
        }

        public String getProvide_remark() {
            return provide_remark;
        }

        public void setProvide_remark(String provide_remark) {
            this.provide_remark = provide_remark;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public ListBean(double c_sort, String realname, int id, String cooperation_mode_cn, String title, String position, String company, String head_pic, Object city, int user_id, int is_v, int is_vip, int view, int talk, String need_describe, String provide_describe, boolean isSearchPerch) {
            this.c_sort = c_sort;
            this.realname = realname;
            this.id = id;
            this.cooperation_mode_cn = cooperation_mode_cn;
            this.title = title;
            this.position = position;
            this.company = company;
            this.head_pic = head_pic;
            this.city = city;
            this.user_id = user_id;
            this.is_v = is_v;
            this.is_vip = is_vip;
            this.view = view;
            this.talk = talk;
            this.need_describe = need_describe;
            this.provide_describe = provide_describe;
            this.isSearchPerch = isSearchPerch;
        }

        public boolean isSearchPerch() {
            return isSearchPerch;
        }

        public void setSearchPerch(boolean searchPerch) {
            isSearchPerch = searchPerch;
        }

        public String getNeed_describe() {
            return need_describe;
        }

        public void setNeed_describe(String need_describe) {
            this.need_describe = need_describe;
        }

        public String getProvide_describe() {
            return provide_describe;
        }

        public void setProvide_describe(String provide_describe) {
            this.provide_describe = provide_describe;
        }

        public double getC_sort() {
            return c_sort;
        }

        public void setC_sort(double c_sort) {
            this.c_sort = c_sort;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCooperation_mode_cn() {
            return cooperation_mode_cn;
        }

        public void setCooperation_mode_cn(String cooperation_mode_cn) {
            this.cooperation_mode_cn = cooperation_mode_cn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
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

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getTalk() {
            return talk;
        }

        public void setTalk(int talk) {
            this.talk = talk;
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
            private List<ListBeanX> list;

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

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
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
