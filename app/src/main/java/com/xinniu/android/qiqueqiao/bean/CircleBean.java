package com.xinniu.android.qiqueqiao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description : 获取圈子列表，及获取我的圈子列表使用
 */
public class CircleBean {


    /**
     * id : 1
     * name : 互联网IT圈子
     * head_pic : http://img.qiqueqiao.com/category/2017/12-12/5a2f88c7914ba.jpg
     * status : 1
     * create_time : 1512568545
     * category : 38
     * level : 1
     * is_top : 1
     * sort_order : 50
     * user_id : 2
     * notice : 群消息
     * num : 15
     * share_url : http://q.qiqueqiao.com/share/circle/1
     * z_list : [{"head_pic":"http://img.qiqueqiao.com/img/20171221/40682667.jpg"},{"head_pic":"http://img.qiqueqiao.com/img/20171219/57.jpg"},{"head_pic":"http://img.qiqueqiao.com/sys/default.jpg"},{"head_pic":"http://img.qiqueqiao.com/sys/default.jpg"}]
     */
    private int id;
    private String name;
    private String head_pic;
    private int status;
    private int create_time;
    private int category;
    private int level;
    private int is_top;
    private int sort_order;
    private int user_id;
    private String notice;
    private int num;
    private String share_url;
    private int is_verify;

    private int unReadMesCount;

    public int getUnReadMesCount() {
        return unReadMesCount;
    }

    public void setUnReadMesCount(int unReadMesCount) {
        this.unReadMesCount = unReadMesCount;
    }

    private List<ZListBean> z_list;

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
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

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<ZListBean> getZ_list() {
        return z_list;
    }

    public void setZ_list(List<ZListBean> z_list) {
        this.z_list = z_list;
    }

    public static class ZListBean {
        /**
         * head_pic : http://img.qiqueqiao.com/img/20171221/40682667.jpg
         */

        private String head_pic;

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }
}
