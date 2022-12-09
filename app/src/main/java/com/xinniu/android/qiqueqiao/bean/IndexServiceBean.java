package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class IndexServiceBean {

    /**
     * list : [{"id":6,"title":"Shiite","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","brand":"百分点","is_corporate_vip":1,"attr":"13137","remark":"服务商标签"},{"id":5,"title":"0090909","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/10700245.jpg","brand":"百分点","is_corporate_vip":1,"attr":"13137_13138","remark":"服务商标签,211"},{"id":4,"title":"Nihaaasankj","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/88584083.jpg,https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/79227273.jpg","brand":"百分点","is_corporate_vip":1,"attr":"13137_13138","remark":"服务商标签,211"},{"id":3,"title":"第一次服务商发布","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/41524026.jpg","brand":"百分点","is_corporate_vip":1,"attr":"13137_13138","remark":"服务商标签,211"}]
     * hasMore : 0
     */

    private int hasMore;
    private List<ListBean> list;

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 6
         * title : Shiite
         * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
         * brand : 百分点
         * is_corporate_vip : 1
         * attr : 13137
         * remark : 服务商标签
         * corporate_vip
         */

        private int id;
        private String title;
        private String images;
        private String brand;
        private int is_corporate_vip;
        private int corporate_vip;
        private String attr;
        private String remark;

        public int getCorporate_vip() {
            return corporate_vip;
        }

        public void setCorporate_vip(int corporate_vip) {
            this.corporate_vip = corporate_vip;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
