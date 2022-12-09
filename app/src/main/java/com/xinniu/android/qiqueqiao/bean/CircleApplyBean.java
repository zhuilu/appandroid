package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by lzq on 2018/2/5.
 */

public class CircleApplyBean {

    private int hasMore;
    private List<CircleApplyBean.ListBean> list;

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public List<CircleApplyBean.ListBean> getList() {
        return list;
    }

    public void setList(List<CircleApplyBean.ListBean> list) {
        this.list = list;
    }

    /**
     * id : 1
     * user_id : 2
     * realname : fan
     * nickname : 1
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/81426937.jpg
     * remark : 交流
     * status : 0
     * create_time : 1515387970
     */


    public static class ListBean {
        private int id;
        private int user_id;
        private String realname;
        private String nickname;
        private String head_pic;
        private String remark;
        private int status;
        private int create_time;

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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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
    }
}
