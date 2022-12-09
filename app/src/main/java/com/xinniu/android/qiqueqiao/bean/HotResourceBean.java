package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class HotResourceBean {


    /**
     * c_sort : null
     * realname : 徐东东
     * need_describe : 222
     * provide_describe : 1212
     * id : 4495
     * title : 1212
     * position : i1185
     * company : 企鹊桥
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/37258482.jpg
     * city : 36
     * user_id : 141
     * is_v : 1
     * is_vip : 0
     * view : 126
     * talk : 3
     * is_verify : 1
     * need_remark : 写字楼资源
     * status : 1
     * provide_remark : 写字楼资源,社群流量
     * create_time : 1542190509
     * p_id : 4
     * p_img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a099e03182.png
     * p_name : 线下渠道
     */

    private Object c_sort;
    private String realname;
    private String need_describe;
    private String provide_describe;
    private int id;
    private String title;
    private String position;
    private String company;
    private String head_pic;
    private int city;
    private int user_id;
    private int is_v;
    private int is_vip;
    private int view;
    private int talk;
    private int is_verify;
    private String need_remark;
    private int status;
    private String provide_remark;
    private int create_time;
    private int p_id;
    private String p_img;
    private String p_name;
    private int comment_count;
    private List<CategoryListBean> category_list = new ArrayList<>();

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public Object getC_sort() {
        return c_sort;
    }

    public void setC_sort(Object c_sort) {
        this.c_sort = c_sort;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
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

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
    }

    public String getNeed_remark() {
        return need_remark;
    }

    public void setNeed_remark(String need_remark) {
        this.need_remark = need_remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProvide_remark() {
        return provide_remark;
    }

    public void setProvide_remark(String provide_remark) {
        this.provide_remark = provide_remark;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }
    public static class CategoryListBean {
        /**
         * id : 2
         * title : 需求标签1
         * list : [{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]
         */

        private int id;
        private String title;
        private List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> list;

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

        public List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> getList() {
            return list;
        }

        public void setList(List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> list) {
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
