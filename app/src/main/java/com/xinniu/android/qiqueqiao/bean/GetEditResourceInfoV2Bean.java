package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/17.
 */

public class GetEditResourceInfoV2Bean {


    /**
     * p_name : 销售渠道
     * id : 30940
     * user_id : 1320
     * title : 就斤斤计较
     * provide_top : 0
     * provide_remark : 12222
     * need_top : 0
     * need_remark : 阿里啦咯了,电商销售渠道
     * cooperation_mode : null
     * cooperation_mode_cn : null
     * need_describe : 1.月销量：
     2.保证金：
     3.一键代发：
     4.结算周期：
     5.合作模式：
     6.补充：
     * provide_describe : 你好啊你好你好考试都能看见谁能看到你说可能的可是你都快三年的开始呢可是你都快九十年代可能是可是你111222333都快三年的开始呢可是你都看见你的开始呢可是你都快能看到你可是你都快睡觉呢可是你的开始呢可是你都快睡觉呢
     * images :
     * thumb_img :
     * city : 220
     * status : 1
     * p_id : 3
     * is_combing : 0
     * is_transaction : 0
     * city_name : 南京
     * provide_attr : 13139
     * need_attr : 13088_11980
     * provide_category : [{"id":1,"title":"提供标签1111","list":[{"id":13139,"name":"12222","is_custom":0,"is_type":1,"user_id":0,"sort_order":0,"title_id":1}]}]
     * need_category : [{"id":3,"title":"测试发布","list":[{"id":11980,"name":"电商销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":2,"title_id":3},{"id":13088,"name":"阿里啦咯了","is_custom":0,"is_type":0,"user_id":1320,"sort_order":50,"title_id":0}]}]
     */

    private String p_name;
    private int id;
    private int user_id;
    private String title;
    private int provide_top;
    private String provide_remark;
    private int need_top;
    private String need_remark;
    private Object cooperation_mode;
    private Object cooperation_mode_cn;
    private String need_describe;
    private String provide_describe;
    private String images;
    private String thumb_img;
    private int city;
    private int status;
    private int p_id;
    private int is_combing;
    private int is_transaction;
    private String city_name;
    private String provide_attr;
    private String need_attr;
    private List<ProvideCategoryBean> provide_category;
    private List<NeedCategoryBean> need_category;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProvide_top() {
        return provide_top;
    }

    public void setProvide_top(int provide_top) {
        this.provide_top = provide_top;
    }

    public String getProvide_remark() {
        return provide_remark;
    }

    public void setProvide_remark(String provide_remark) {
        this.provide_remark = provide_remark;
    }

    public int getNeed_top() {
        return need_top;
    }

    public void setNeed_top(int need_top) {
        this.need_top = need_top;
    }

    public String getNeed_remark() {
        return need_remark;
    }

    public void setNeed_remark(String need_remark) {
        this.need_remark = need_remark;
    }

    public Object getCooperation_mode() {
        return cooperation_mode;
    }

    public void setCooperation_mode(Object cooperation_mode) {
        this.cooperation_mode = cooperation_mode;
    }

    public Object getCooperation_mode_cn() {
        return cooperation_mode_cn;
    }

    public void setCooperation_mode_cn(Object cooperation_mode_cn) {
        this.cooperation_mode_cn = cooperation_mode_cn;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getIs_combing() {
        return is_combing;
    }

    public void setIs_combing(int is_combing) {
        this.is_combing = is_combing;
    }

    public int getIs_transaction() {
        return is_transaction;
    }

    public void setIs_transaction(int is_transaction) {
        this.is_transaction = is_transaction;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvide_attr() {
        return provide_attr;
    }

    public void setProvide_attr(String provide_attr) {
        this.provide_attr = provide_attr;
    }

    public String getNeed_attr() {
        return need_attr;
    }

    public void setNeed_attr(String need_attr) {
        this.need_attr = need_attr;
    }

    public List<ProvideCategoryBean> getProvide_category() {
        return provide_category;
    }

    public void setProvide_category(List<ProvideCategoryBean> provide_category) {
        this.provide_category = provide_category;
    }

    public List<NeedCategoryBean> getNeed_category() {
        return need_category;
    }

    public void setNeed_category(List<NeedCategoryBean> need_category) {
        this.need_category = need_category;
    }

    public static class ProvideCategoryBean {
        /**
         * id : 1
         * title : 提供标签1111
         * list : [{"id":13139,"name":"12222","is_custom":0,"is_type":1,"user_id":0,"sort_order":0,"title_id":1}]
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
             * id : 13139
             * name : 12222
             * is_custom : 0
             * is_type : 1
             * user_id : 0
             * sort_order : 0
             * title_id : 1
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

    public static class NeedCategoryBean {
        /**
         * id : 3
         * title : 测试发布
         * list : [{"id":11980,"name":"电商销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":2,"title_id":3},{"id":13088,"name":"阿里啦咯了","is_custom":0,"is_type":0,"user_id":1320,"sort_order":50,"title_id":0}]
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
             * id : 11980
             * name : 电商销售渠道
             * is_custom : 0
             * is_type : 0
             * user_id : 0
             * sort_order : 2
             * title_id : 3
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
