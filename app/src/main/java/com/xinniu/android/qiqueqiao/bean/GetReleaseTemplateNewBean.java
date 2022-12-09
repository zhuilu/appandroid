package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public class GetReleaseTemplateNewBean {


    /**
     * provide_category : {"system_category":[{"id":1,"title":"提供标签1111","list":[{"id":13139,"name":"12222","is_custom":0,"is_type":1,"user_id":0,"sort_order":0,"title_id":1}]}],"user_category":[{"id":2,"title":"需求标签1","list":[{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]}]}
     * need_category : {"system_category":[{"id":2,"title":"需求标签1","list":[{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]},{"id":3,"title":"测试发布","list":[{"id":11980,"name":"电商销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":2,"title_id":3}]}],"user_category":[{"id":2,"title":"需求标签1","list":[{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]}]}
     * need_description : 1.月销量：
     * 2.保证金：
     * 3.一键代发：
     * 4.结算周期：
     * 5.合作模式：
     * 6.补充：
     * provide_description : 你好啊你好你好考试都能看见谁能看到你说可能的可是你都快三年的开始呢可是你都快九十年代可能是可是你111222333都快三年的开始呢可是你都看见你的开始呢可是你都快能看到你可是你都快睡觉呢可是你的开始呢可是你都快睡觉呢
     * provide_category_title : 销售渠道1
     * need_category_title : 销售渠道2
     * provide_description_title : 销售渠道3
     * need_description_title : 销售渠道4
     */

    private ProvideCategoryBean provide_category;
    private NeedCategoryBean need_category;
    private String need_description;
    private String provide_description;
    private String provide_category_title;
    private String need_category_title;
    private String provide_description_title;
    private String need_description_title;

    public ProvideCategoryBean getProvide_category() {
        return provide_category;
    }

    public void setProvide_category(ProvideCategoryBean provide_category) {
        this.provide_category = provide_category;
    }

    public NeedCategoryBean getNeed_category() {
        return need_category;
    }

    public void setNeed_category(NeedCategoryBean need_category) {
        this.need_category = need_category;
    }

    public String getNeed_description() {
        return need_description;
    }

    public void setNeed_description(String need_description) {
        this.need_description = need_description;
    }

    public String getProvide_description() {
        return provide_description;
    }

    public void setProvide_description(String provide_description) {
        this.provide_description = provide_description;
    }

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

    public String getProvide_description_title() {
        return provide_description_title;
    }

    public void setProvide_description_title(String provide_description_title) {
        this.provide_description_title = provide_description_title;
    }

    public String getNeed_description_title() {
        return need_description_title;
    }

    public void setNeed_description_title(String need_description_title) {
        this.need_description_title = need_description_title;
    }

    public static class ProvideCategoryBean {
        private List<SystemCategoryBean> system_category;
        private List<UserCategoryBean> user_category;

        public List<SystemCategoryBean> getSystem_category() {
            return system_category;
        }

        public void setSystem_category(List<SystemCategoryBean> system_category) {
            this.system_category = system_category;
        }

        public List<UserCategoryBean> getUser_category() {
            return user_category;
        }

        public void setUser_category(List<UserCategoryBean> user_category) {
            this.user_category = user_category;
        }

        public static class SystemCategoryBean {
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

        public static class UserCategoryBean {
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

    public static class NeedCategoryBean {
        private List<SystemCategoryBeanX> system_category;
        private List<UserCategoryBeanX> user_category;

        public List<SystemCategoryBeanX> getSystem_category() {
            return system_category;
        }

        public void setSystem_category(List<SystemCategoryBeanX> system_category) {
            this.system_category = system_category;
        }

        public List<UserCategoryBeanX> getUser_category() {
            return user_category;
        }

        public void setUser_category(List<UserCategoryBeanX> user_category) {
            this.user_category = user_category;
        }

        public static class SystemCategoryBeanX {
            /**
             * id : 2
             * title : 需求标签1
             * list : [{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]
             */

            private int id;
            private String title;
            private List<ListBeanXX> list;

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

            public List<ListBeanXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXX> list) {
                this.list = list;
            }

            public static class ListBeanXX {
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

        public static class UserCategoryBeanX {
            /**
             * id : 2
             * title : 需求标签1
             * list : [{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]
             */

            private int id;
            private String title;
            private List<ListBeanXXX> list;

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

            public List<ListBeanXXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXXX> list) {
                this.list = list;
            }

            public static class ListBeanXXX {
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
}
