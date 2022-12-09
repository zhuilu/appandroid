package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuchance on 2018/10/26.
 */

public class InquireBean {


    /**
     * list : [{"id":34,"topic_id":4249,"content":"这是第二条","from_uid":114,"nickname":"李金付123","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg","company":"企鹊桥","position":"CEO","is_v":0,"create_time":1540522630,"reply":[]},{"id":33,"topic_id":4249,"content":"这是第二条","from_uid":114,"nickname":"李金付12","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg","company":"企鹊桥","position":"CEO","is_v":1,"create_time":1540519644,"reply":[{"id":23,"comment_id":33,"reply_type":1,"topic_id":4249,"reply_id":33,"content":"少时诵诗书所111111","from_uid":1320,"from_thumb_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKakTnXw45Kvj2iawm0h8xtPekXkNibXTKibKAKcvDRWMXXAeWWhdpCCLX39Y9BkBo2GbVpMmww34n3A/132","from_company":"伍方会议公司","from_position":"项目经理","from_nickname":"宋冲122222222","create_time":1540519923,"to_nickname":"李金付12","to_company":"企鹊桥","to_position":"CEO","is_v":0}]},{"id":29,"topic_id":4249,"content":"这是第一条","from_uid":114,"nickname":"李金付","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg","company":"企鹊桥","position":"CEO","is_v":1,"create_time":1540517702,"reply":[{"id":19,"comment_id":29,"reply_type":1,"topic_id":4249,"reply_id":29,"content":"1122222","from_uid":132,"from_thumb_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKakTnXw45Kvj2iawm0h8xtPekXkNibXTKibKAKcvDRWMXXAeWWhdpCCLX39Y9BkBo2GbVpMmww34n3A/132","from_company":"伍方会议公司","from_position":"项目经理","from_nickname":"宋冲","create_time":1540518358,"to_nickname":"李金付","to_company":"企鹊桥","to_position":"CEO","is_v":0},{"id":21,"comment_id":29,"reply_type":2,"topic_id":4249,"reply_id":19,"content":"少时诵诗书所","from_uid":1320,"from_thumb_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKakTnXw45Kvj2iawm0h8xtPekXkNibXTKibKAKcvDRWMXXAeWWhdpCCLX39Y9BkBo2GbVpMmww34n3A/132","from_company":"伍方会议公司","from_position":"项目经理","from_nickname":"宋冲12","create_time":1540518628,"to_nickname":"宋冲","to_company":"企鹊桥","to_position":"CEO","is_v":0}]}]
     * total : 23
     * hasMore : 1
     */

    private int total;
    private int hasMore;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
         * id : 34
         * topic_id : 4249
         * content : 这是第二条
         * from_uid : 114
         * nickname : 李金付123
         * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg
         * company : 企鹊桥
         * position : CEO
         * is_v : 0
         * create_time : 1540522630
         * reply : []
         */

        private int id;
        private int topic_id;
        private String content;
        private int from_uid;
        private String nickname;
        private String thumb_img;
        private String company;
        private String position;
        private int is_v;
        private int create_time;
        private List<ReplyBean> reply;
        private boolean isOpen = true;
        private boolean isNew = false;
        private int is_vip;
        private boolean isU;

        public boolean isU() {
            return isU;
        }

        public void setU(boolean u) {
            isU = u;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public boolean isNew() {
            return isNew;
        }

        public void setNew(boolean aNew) {
            isNew = aNew;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFrom_uid() {
            return from_uid;
        }

        public void setFrom_uid(int from_uid) {
            this.from_uid = from_uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
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

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class ReplyBean {

            /**
             * id : 19
             * comment_id : 29
             * reply_type : 1
             * topic_id : 4249
             * reply_id : 29
             * content : 1122222
             * from_uid : 132
             * from_thumb_img : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKakTnXw45Kvj2iawm0h8xtPekXkNibXTKibKAKcvDRWMXXAeWWhdpCCLX39Y9BkBo2GbVpMmww34n3A/132
             * from_company : 伍方会议公司
             * from_position : 项目经理
             * from_nickname : 宋冲
             * create_time : 1540518358
             * to_nickname : 李金付
             * to_company : 企鹊桥
             * to_position : CEO
             * is_v : 0
             */

            private int id;
            private int comment_id;
            private int reply_type;
            private int topic_id;
            private int reply_id;
            private String content;
            private int from_uid;
            private String from_thumb_img;
            private String from_company;
            private String from_position;
            private String from_nickname;
            private int create_time;
            private String to_nickname;
            private String to_company;
            private String to_position;
            private int is_v;
            private boolean isNew = false;
            private int is_vip;
            private int to_is_vip;
            private boolean isU;

            public boolean isU() {
                return isU;
            }

            public void setU(boolean u) {
                isU = u;
            }

            public int getTo_is_vip() {
                return to_is_vip;
            }

            public void setTo_is_vip(int to_is_vip) {
                this.to_is_vip = to_is_vip;
            }

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public boolean isNew() {
                return isNew;
            }

            public void setNew(boolean aNew) {
                isNew = aNew;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getReply_type() {
                return reply_type;
            }

            public void setReply_type(int reply_type) {
                this.reply_type = reply_type;
            }

            public int getTopic_id() {
                return topic_id;
            }

            public void setTopic_id(int topic_id) {
                this.topic_id = topic_id;
            }

            public int getReply_id() {
                return reply_id;
            }

            public void setReply_id(int reply_id) {
                this.reply_id = reply_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getFrom_uid() {
                return from_uid;
            }

            public void setFrom_uid(int from_uid) {
                this.from_uid = from_uid;
            }

            public String getFrom_thumb_img() {
                return from_thumb_img;
            }

            public void setFrom_thumb_img(String from_thumb_img) {
                this.from_thumb_img = from_thumb_img;
            }

            public String getFrom_company() {
                return from_company;
            }

            public void setFrom_company(String from_company) {
                this.from_company = from_company;
            }

            public String getFrom_position() {
                return from_position;
            }

            public void setFrom_position(String from_position) {
                this.from_position = from_position;
            }

            public String getFrom_nickname() {
                return from_nickname;
            }

            public void setFrom_nickname(String from_nickname) {
                this.from_nickname = from_nickname;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getTo_nickname() {
                return to_nickname;
            }

            public void setTo_nickname(String to_nickname) {
                this.to_nickname = to_nickname;
            }

            public String getTo_company() {
                return to_company;
            }

            public void setTo_company(String to_company) {
                this.to_company = to_company;
            }

            public String getTo_position() {
                return to_position;
            }

            public void setTo_position(String to_position) {
                this.to_position = to_position;
            }

            public int getIs_v() {
                return is_v;
            }

            public void setIs_v(int is_v) {
                this.is_v = is_v;
            }
            public static final int MORE = 1;
            public static final int COMMMON = 2;
            private int itemType;

            public ReplyBean(int itemType) {
                this.itemType = itemType;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }

            public int getItemType() {
                return itemType;
            }
        }




    }
}
