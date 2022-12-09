package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class ServiceCategoryAndTag {

    /**
     * id : 20
     * name : 122
     * "img": "http://timg.qiqueqiao.com/category/2019/03-05/5c7e5015ce4bd.png",
     * zlist : [{"id":13137,"name":"122"}]
     */

    private int id;
    private String name;
    private String img;
    private List<ZlistBean> zlist;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public List<ZlistBean> getZlist() {
        return zlist;
    }

    public void setZlist(List<ZlistBean> zlist) {
        this.zlist = zlist;
    }

    public static class ZlistBean implements MultiItemEntity {

        public static final int SYSTEMTYPE = 1;
        public static final int USERTYPE = 2;
        private int itemType;

        public ZlistBean(int itemType, int id, String name, boolean isCheck) {
            this.itemType = itemType;
            this.id = id;
            this.name = name;
            this.isCheck = isCheck;
        }

        public ZlistBean(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
        /**
         * id : 13137
         * name : 122
         */

        private int id;
        private String name;
        private boolean isCheck =false;
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

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }


    }
}
