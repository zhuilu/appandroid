package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class CommentBean {


    /**
     * list : [{"user_id":1376,"realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","create_time":1554347356,"upvote_num":3,"brand":"百分点","position":"经理","is_v":1,"is_vip":2,"id":1,"content":"这个视频不咋地啊","p_id":0,"is_upvote":1,"reply":[{"user_id":1376,"realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","create_time":1554360545,"upvote_num":0,"brand":"百分点","position":"经理","is_v":1,"is_vip":2,"id":13,"content":"你说的都对aaa12","p_id":1}]}]
     * hasMore : 0
     */

    private int hasMore;
    private List<ListBean> list=new ArrayList<>();

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
         * user_id : 1376
         * realname : 曾杨
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * create_time : 1554347356
         * upvote_num : 3
         * brand : 百分点
         * position : 经理
         * is_v : 1
         * is_vip : 2
         * id : 1
         * content : 这个视频不咋地啊
         * p_id : 0
         * is_upvote : 1
         * reply : [{"user_id":1376,"realname":"曾杨","head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","create_time":1554360545,"upvote_num":0,"brand":"百分点","position":"经理","is_v":1,"is_vip":2,"id":13,"content":"你说的都对aaa12","p_id":1}]
         */

        private int user_id;
        private String realname;
        private String head_pic;
        private int create_time;
        private int upvote_num;
        private String brand;
        private String position;
        private int is_v;
        private int is_vip;
        private int id;
        private String content;
        private int p_id;
        private int is_upvote;
        private ReplyBean reply;
        private boolean isNew = false;

        public boolean isNew() {
            return isNew;
        }

        public void setNew(boolean aNew) {
            isNew = aNew;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpvote_num() {
            return upvote_num;
        }

        public void setUpvote_num(int upvote_num) {
            this.upvote_num = upvote_num;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public int getIs_upvote() {
            return is_upvote;
        }

        public void setIs_upvote(int is_upvote) {
            this.is_upvote = is_upvote;
        }

        public ReplyBean getReply() {
            return reply;
        }

        public void setReply(ReplyBean reply) {
            this.reply = reply;
        }

        public static class ReplyBean {
            /**
             * user_id : 1376
             * realname : 曾杨
             * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
             * create_time : 1554360545
             * upvote_num : 0
             * brand : 百分点
             * position : 经理
             * is_v : 1
             * is_vip : 2
             * id : 13
             * content : 你说的都对aaa12
             * p_id : 1
             */

            private int user_id;
            private String realname;
            private String head_pic;
            private int create_time;
            private int upvote_num;
            private String brand;
            private String position;
            private int is_v;
            private int is_vip;
            private int id;
            private String content;
            private int p_id;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpvote_num() {
                return upvote_num;
            }

            public void setUpvote_num(int upvote_num) {
                this.upvote_num = upvote_num;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public int getIs_v() {
                return is_v;
            }

            public void setIs_v(int is_v) {
                this.is_v = is_v;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getP_id() {
                return p_id;
            }

            public void setP_id(int p_id) {
                this.p_id = p_id;
            }
        }
    }
}
