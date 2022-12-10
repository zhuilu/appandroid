package com.xinniu.android.qiqueqiao.bean;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuchance on 2018/6/27.
 */

public class SeclectCateBean{

    private List<UserCategoryBean> userCategory;
    private List<CommonCategoryBean> commonCategory;

    public List<UserCategoryBean> getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(List<UserCategoryBean> userCategory) {
        this.userCategory = userCategory;
    }

    public List<CommonCategoryBean> getCommonCategory() {
        return commonCategory;
    }

    public void setCommonCategory(List<CommonCategoryBean> commonCategory) {
        this.commonCategory = commonCategory;
    }

    public static class UserCategoryBean {


        /**
         * id : 11875
         * name : nihao
         */


        private int id;
        private String name;
        private boolean isCheck;

        public UserCategoryBean(int id, String name, boolean isCheck) {
            this.id = id;
            this.name = name;
            this.isCheck = isCheck;
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

    }

    public static class CommonCategoryBean {
        /**
         * id : 3
         * name : 销售渠道
         * zlist : [{"id":156,"name":"全部"}]
         */

        private int id;
        private String name;
        private List<ZlistBean> zlist;

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

        public List<ZlistBean> getZlist() {
            return zlist;
        }

        public void setZlist(List<ZlistBean> zlist) {
            this.zlist = zlist;
        }

        public static class ZlistBean {
            /**
             * id : 156
             * name : 全部
             */

            private int id;
            private String name;
            private boolean isCheck;

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
        }
    }
}
