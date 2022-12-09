package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/12/25.
 */

public class MyCommentBean {


    /**
     * hasMore : 0
     * list : [{"id":164,"comment_id":115,"topic_id":4515,"content":"敏敏","from_uid":1376,"nickname":"曾杨","thumb_img":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","is_v":0,"company":"睡觉睡觉","position":"好好说","status":1,"create_time":1542253862,"type":2,"to_company":"Baidu","to_position":"ios1","to_content":"22","to_nickname":"Dai 一二一零","is_del":1},{"id":36,"comment_id":36,"topic_id":4249,"content":"这是第二条","from_uid":1376,"nickname":"李金付125","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/93774876.jpg","is_v":0,"company":"企鹊桥","position":"CEO","status":1,"create_time":1540522636,"type":1,"to_company":"","to_position":"","title":"暂时填充","topic_company":"机场媒体广告影视植入"},{"id":19,"comment_id":29,"topic_id":4249,"content":"1122222","from_uid":1376,"nickname":"宋冲","thumb_img":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKakTnXw45Kvj2iawm0h8xtPekXkNibXTKibKAKcvDRWMXXAeWWhdpCCLX39Y9BkBo2GbVpMmww34n3A/132","is_v":0,"company":"伍方会议公司","position":"项目经理","status":0,"create_time":1540518358,"type":2,"to_company":"企鹊桥","to_position":"CEO","to_content":"这是第一条","to_nickname":"李金付","is_del":1}]
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
         * id : 164
         * comment_id : 115
         * topic_id : 4515
         * content : 敏敏
         * from_uid : 1376
         * nickname : 曾杨
         * thumb_img : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * is_v : 0
         * company : 睡觉睡觉
         * position : 好好说
         * status : 1
         * create_time : 1542253862
         * type : 2
         * to_company : Baidu
         * to_position : ios1
         * to_content : 22
         * to_nickname : Dai 一二一零
         * is_del : 1
         * title : 暂时填充
         * "topic_type": 1,
         * topic_company : 机场媒体广告影视植入
         */

        private int id;
        private int comment_id;
        private int topic_id;
        private String content;
        private int from_uid;
        private String nickname;
        private String thumb_img;
        private int is_v;
        private String company;
        private String position;
        private int status;
        private int create_time;
        private int type;
        private String to_company;
        private String to_position;
        private String to_content;
        private String to_nickname;
        private int is_del;
        private String title;
        private String topic_company;
        private int topic_type;//1:资源，2：服务商

        public int getTopic_type() {
            return topic_type;
        }

        public void setTopic_type(int topic_type) {
            this.topic_type = topic_type;
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

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getTo_content() {
            return to_content;
        }

        public void setTo_content(String to_content) {
            this.to_content = to_content;
        }

        public String getTo_nickname() {
            return to_nickname;
        }

        public void setTo_nickname(String to_nickname) {
            this.to_nickname = to_nickname;
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

        public String getTopic_company() {
            return topic_company;
        }

        public void setTopic_company(String topic_company) {
            this.topic_company = topic_company;
        }
    }
}
