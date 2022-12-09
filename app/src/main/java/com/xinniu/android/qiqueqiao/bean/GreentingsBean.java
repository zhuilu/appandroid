package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class GreentingsBean  {

    private List<SystemBean> system;
    private List<CustomizeBean> customize;


    public List<SystemBean> getSystem() {
        return system;
    }

    public void setSystem(List<SystemBean> system) {
        this.system = system;
    }

    public List<CustomizeBean> getCustomize() {
        return customize;
    }

    public void setCustomize(List<CustomizeBean> customize) {
        this.customize = customize;
    }

    public static class SystemBean implements MultiItemEntity{
        /**
         * id : 1
         * user_id : 0
         * title : hello,大家好，我是戴超，我是沙雕
         * status : 1
         */

        private int id;
        private int user_id;
        private String title;
        private int status;
        private int itemType;

        public SystemBean(int id, int user_id, String title, int status, int itemType) {
            this.id = id;
            this.user_id = user_id;
            this.title = title;
            this.status = status;
            this.itemType = itemType;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

    public static class CustomizeBean {
        /**
         * id : 1
         * user_id : 1
         * title : hello,大家好，我是戴超，我是沙雕
         * status : 0
         */

        private int id;
        private int user_id;
        private String title;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
