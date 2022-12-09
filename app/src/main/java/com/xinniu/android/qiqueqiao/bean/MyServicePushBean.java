package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class MyServicePushBean {

    /**
     * list : [{"id":1,"is_verify":1,"status":1,"view":0,"is_del":0,"title":"第一次服务商发布","attr":"13137_13138","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/41524026.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/41524026_thumb.jpg","p_id":20,"user_id":1376,"is_closeComment":0,"p_name":"122","remark":"服务商标签,211","case_count":0},{"id":10,"is_verify":1,"status":1,"view":0,"is_del":0,"title":"1221212","attr":"13138","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg","p_id":20,"user_id":1376,"is_closeComment":0,"p_name":"122","remark":"211","case_count":3},{"id":11,"is_verify":1,"status":1,"view":0,"is_del":0,"title":"1221212","attr":"13137_13138","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg","p_id":20,"user_id":1376,"is_closeComment":0,"p_name":"122","remark":"服务商标签,211","case_count":0}]
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
         * id : 1
         * is_verify : 1
         * status : 1
         * view : 0
         * is_del : 0
         * title : 第一次服务商发布
         * attr : 13137_13138
         * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/41524026.jpg
         * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/41524026_thumb.jpg
         * p_id : 20
         * user_id : 1376
         * is_closeComment : 0
         * p_name : 122
         * remark : 服务商标签,211
         * case_count : 0
         *
         */

        private int id;
        private int is_verify;
        private int status;
        private int view;
        private int is_del;
        private String title;
        private String attr;
        private String images;
        private String thumb_img;
        private int p_id;
        private int user_id;
        private int is_closeComment;
        private String p_name;
        private String remark;
        private int case_count;
        private String p_img;

        public String getP_img() {
            return p_img;
        }

        public void setP_img(String p_img) {
            this.p_img = p_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
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

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getIs_closeComment() {
            return is_closeComment;
        }

        public void setIs_closeComment(int is_closeComment) {
            this.is_closeComment = is_closeComment;
        }

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCase_count() {
            return case_count;
        }

        public void setCase_count(int case_count) {
            this.case_count = case_count;
        }
    }
}
