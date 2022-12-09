package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuchance on 2018/12/13.
 */

public class GetReleaseTemplateBean {


    /**
     * need_category : {"user_category":[{"id":13035,"name":"ffff","is_custom":0,"is_type":1,"user_id":1376}],"system_category":[{"id":56,"name":"写字楼资源","is_custom":0,"is_type":1,"user_id":0},{"id":11982,"name":"商超","is_custom":0,"is_type":1,"user_id":0}]}
     * provide_category : {"system_category":[{"id":2,"name":"线下门店","is_custom":0,"is_type":0,"user_id":0},{"id":55,"name":"社区资源","is_custom":0,"is_type":0,"user_id":0}],"user_category":[{"id":13035,"name":"ffff","is_custom":0,"is_type":1,"user_id":1376}]}
     * need_description : 1.月销量：
     2.保证金：
     3.一键代发：
     4.结算周期：
     5.合作模式：
     6.补充：
     * provide_description :
     */

    private NeedCategoryBean need_category;
    private ProvideCategoryBean provide_category;
    private String need_description;
    private String provide_description;
    private String provide_category_title;
    private String need_category_title;
    private String provide_description_title;
    private String need_description_title;

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

    public NeedCategoryBean getNeed_category() {
        return need_category;
    }

    public void setNeed_category(NeedCategoryBean need_category) {
        this.need_category = need_category;
    }

    public ProvideCategoryBean getProvide_category() {
        return provide_category;
    }

    public void setProvide_category(ProvideCategoryBean provide_category) {
        this.provide_category = provide_category;
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

    public static class NeedCategoryBean {



        private List<UserCategoryBean> user_category;
        private List<SystemCategoryBean> system_category;

        public List<UserCategoryBean> getUser_category() {
            return user_category;
        }

        public void setUser_category(List<UserCategoryBean> user_category) {
            this.user_category = user_category;
        }

        public List<SystemCategoryBean> getSystem_category() {
            return system_category;
        }

        public void setSystem_category(List<SystemCategoryBean> system_category) {
            this.system_category = system_category;
        }

        public static class UserCategoryBean {
            /**
             * id : 13035
             * name : ffff
             * is_custom : 0
             * is_type : 1
             * user_id : 1376
             */

            private int id;
            private String name;
            private int is_custom;
            private int is_type;
            private int user_id;
            private boolean isCheck =false;
            private boolean isAdd;

            public boolean isAdd() {
                return isAdd;
            }

            public void setAdd(boolean add) {
                isAdd = add;
            }

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
        }

        public static class SystemCategoryBean implements MultiItemEntity{

            public static final int NEEDSYSTEMTYPE = 1;
            public static final int NEEDUSERTYPE = 2;

            private int itemType;

            public SystemCategoryBean(int itemType, int id, String name, int is_custom, int is_type, int user_id, boolean isCheck) {
                this.itemType = itemType;
                this.id = id;
                this.name = name;
                this.is_custom = is_custom;
                this.is_type = is_type;
                this.user_id = user_id;
                this.isCheck = isCheck;
            }

            public SystemCategoryBean(int itemType) {
                this.itemType = itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }

            @Override
            public int getItemType() {
                return itemType;
            }


            /**
             * id : 56
             * name : 写字楼资源
             * is_custom : 0
             * is_type : 1
             * user_id : 0
             */

            private int id;
            private String name;
            private int is_custom;
            private int is_type;
            private int user_id;
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
        }
    }

    public static class ProvideCategoryBean {



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

        public static class SystemCategoryBeanX implements MultiItemEntity{
            public static final int OFFERSYSTEMTYPE = 1;
            public static final int OFFERUSERTYPE = 2;


            private int itemType;

            public SystemCategoryBeanX(int itemType) {
                this.itemType = itemType;
            }

            public SystemCategoryBeanX(int itemType, int id, String name, int is_custom, int is_type, int user_id, boolean isCheck) {
                this.itemType = itemType;
                this.id = id;
                this.name = name;
                this.is_custom = is_custom;
                this.is_type = is_type;
                this.user_id = user_id;
                this.isCheck = isCheck;
            }

            @Override
            public int getItemType() {
                return itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }
            /**
             * id : 2
             * name : 线下门店
             * is_custom : 0
             * is_type : 0
             * user_id : 0
             */

            private int id;
            private String name;
            private int is_custom;
            private int is_type;
            private int user_id;
            private boolean isCheck =false;

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
        }

        public static class UserCategoryBeanX {
            /**
             * id : 13035
             * name : ffff
             * is_custom : 0
             * is_type : 1
             * user_id : 1376
             */

            private int id;
            private String name;
            private int is_custom;
            private int is_type;
            private int user_id;
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
        }
    }
}
