package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class CommunicationRecordBean {


    /**
     * time : success
     * data : {"hasMore":0,"list":[{"to_user_id":1503,"type":2,"realname":"pp","position":"运营","company":"技术部","images":"","create_time":1571036294,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/92825604.jpg"},{"to_user_id":141,"type":3,"realname":"","position":"","company":"","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/images/2019/10-14/5da41c6612494.jpg,https://qqq2017.oss-cn-hangzhou.aliyuncs.com/images/2019/10-14/5da41c663fe85.jpg","create_time":1571036263,"head_pic":""},{"to_user_id":1376,"type":1,"realname":"1234","position":"","company":"","images":"","create_time":1571021972,"head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132"}]}
     */

    private String time;
    private DataBean data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hasMore : 0
         * list : [{"to_user_id":1503,"type":2,"realname":"pp","position":"运营","company":"技术部","images":"","create_time":1571036294,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/92825604.jpg"},{"to_user_id":141,"type":3,"realname":"","position":"","company":"","images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/images/2019/10-14/5da41c6612494.jpg,https://qqq2017.oss-cn-hangzhou.aliyuncs.com/images/2019/10-14/5da41c663fe85.jpg","create_time":1571036263,"head_pic":""},{"to_user_id":1376,"type":1,"realname":"1234","position":"","company":"","images":"","create_time":1571021972,"head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132"}]
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
             * to_user_id : 1503
             * type : 2
             * realname : pp
             * position : 运营
             * company : 技术部
             * images :
             * create_time : 1571036294
             * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/92825604.jpg
             */

            private int to_user_id;
            private int type;
            private String realname;
            private String position;
            private String company;
            private String images;
            private long create_time;
            private String head_pic;
            private String thumb_img;
            private int is_v;

            public String getThumb_img() {
                return thumb_img;
            }

            public void setThumb_img(String thumb_img) {
                this.thumb_img = thumb_img;
            }

            public int getIs_v() {
                return is_v;
            }

            public void setIs_v(int is_v) {
                this.is_v = is_v;
            }

            public int getTo_user_id() {
                return to_user_id;
            }

            public void setTo_user_id(int to_user_id) {
                this.to_user_id = to_user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }
        }
    }
}
