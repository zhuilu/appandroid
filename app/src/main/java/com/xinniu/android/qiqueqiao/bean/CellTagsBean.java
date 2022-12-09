package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/6/26.
 */

public class CellTagsBean {


    /**
     * provide_category : [{"id":1,"title":"提供标签1111","list":[{"id":13139,"name":"12222","is_custom":0,"is_type":1,"user_id":0,"sort_order":0,"title_id":1}]}]
     * need_category : [{"id":2,"title":"需求标签1","list":[{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]},{"id":3,"title":"测试发布","list":[{"id":11980,"name":"电商销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":2,"title_id":3}]}]
     * provide_category_title : 销售渠道1
     * need_category_title : 销售渠道2
     */

    private String provide_category_title;
    private String need_category_title;
    private List<ProvideCategoryBean> provide_category;
    private List<NeedCategoryBean> need_category;

    public String getProvide_category_title() {
        return provide_category_title;
    }

    public void setProvide_category_title(String provide_category_title) {
        this.provide_category_title = provide_category_title;
    }

    public String getNeed_category_title() {
        return need_category_title;
    }

    public void setNeed_category_title(String need_category_title) {
        this.need_category_title = need_category_title;
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
            private boolean isCheck = false;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
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
            private boolean isCheck = false;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
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
