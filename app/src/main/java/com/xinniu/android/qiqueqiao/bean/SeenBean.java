package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by lzq on 2017/12/12.
 */

public class SeenBean {
    /*
     "realname": "张三",
            "position": "程序猿",
            "company": "杭州摆渡文化传媒有限公司",
            "head_pic": "http://img.qiqueqiao.com/sys/default.jpg"
            "user_id": 1,
            "follow_status": 1
     */

    private int hasMore;

    private List<SeenBean.ListBean> list;

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

        public String realname;
        public String position;
        public String company;
        public String head_pic;
        public int user_id;
        public int follow_status;
    }
}
