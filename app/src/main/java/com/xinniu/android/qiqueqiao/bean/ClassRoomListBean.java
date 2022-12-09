package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class ClassRoomListBean {

    /**
     * list : [{"id":19,"video_id":"94b3176022af4d8e8d1e618a125aea43","title":"777","category":[{"name":"视频分类12"},{"name":"视频分类2"},{"name":"视频分类3"}],"poster":"http://timg.qiqueqiao.com/activity_category/2019/04-02/5ca3189b2cbac.png","status":1,"upvote_num":0,"create_time":1554192494,"is_upvote":0,"comment_count":0},{"id":18,"video_id":"eb30a52f1273449eb2050c36bb3c6709","title":"777","category":[{"name":"视频分类2"},{"name":"视频分类3"}],"poster":"http://timg.qiqueqiao.com/activity_category/2019/04-02/5ca317ffe4e89.png","status":1,"upvote_num":0,"create_time":1554192359,"is_upvote":0,"comment_count":0},{"id":17,"video_id":"c4585ec39c764dccb2fcffee95b833d1","title":"12","category":[{"name":"视频分类12"},{"name":"视频分类2"}],"poster":"http://timg.qiqueqiao.com/activity_category/2019/04-01/5ca2041263d9e.png","status":1,"upvote_num":0,"create_time":1554120776,"is_upvote":0,"comment_count":0}]
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
         * id : 19
         * video_id : 94b3176022af4d8e8d1e618a125aea43
         * title : 777
         * category : [{"name":"视频分类12"},{"name":"视频分类2"},{"name":"视频分类3"}]
         * poster : http://timg.qiqueqiao.com/activity_category/2019/04-02/5ca3189b2cbac.png
         * status : 1
         * upvote_num : 0
         * create_time : 1554192494
         * is_upvote : 0
         * comment_count : 0
         * type:1
         */

        private int id;
        private String video_id;
        private String title;
        private String poster;
        private int status;
        private int upvote_num;
        private int create_time;
        private int is_upvote;
        private int comment_count;
        private List<CategoryBean> category;
        private int type;//	1:视频，2：图文

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUpvote_num() {
            return upvote_num;
        }

        public void setUpvote_num(int upvote_num) {
            this.upvote_num = upvote_num;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIs_upvote() {
            return is_upvote;
        }

        public void setIs_upvote(int is_upvote) {
            this.is_upvote = is_upvote;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            /**
             * name : 视频分类12
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
