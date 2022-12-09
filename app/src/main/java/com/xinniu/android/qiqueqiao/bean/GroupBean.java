package com.xinniu.android.qiqueqiao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yuchance on 2018/6/12.
 */
@Entity
public class GroupBean {

    /**
     * id : 2
     * name : 互联网商务合作圈子
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/5a447d72a7f51.jpg
     * status : 1
     * create_time : 1512568545
     * category : 0
     * level : 1
     * is_top : 1
     * sort_order : 50
     * user_id : 126
     * notice : 圈子公告：该圈为互联网行业交流圈，以合作交流为主。切勿发其他广告信息
     * type : 0
     * introduction :
     * is_verify : 1
     * is_send : 1
     * is_v : 0
     * is_vip : 0
     * num : 648
     * share_url : http://t.qiqueqiao.com/share/circle/2
     */
    @Id
    private Long theid;
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
    private int type;
    private String introduction;
    private int is_verify;
    private int is_send;
    private int is_v;
    private int is_vip;
    private int num;
    private String share_url;

    private int unReadMesCount;

    public int getUnReadMesCount() {
        return unReadMesCount;
    }

    public void setUnReadMesCount(int unReadMesCount) {
        this.unReadMesCount = unReadMesCount;
    }

    @Generated(hash = 106280055)
    public GroupBean(Long theid, int id, String name, String head_pic, int status,
            int create_time, int category, int level, int is_top, int sort_order,
            int user_id, String notice, int type, String introduction,
            int is_verify, int is_send, int is_v, int is_vip, int num,
            String share_url, int unReadMesCount) {
        this.theid = theid;
        this.id = id;
        this.name = name;
        this.head_pic = head_pic;
        this.status = status;
        this.create_time = create_time;
        this.category = category;
        this.level = level;
        this.is_top = is_top;
        this.sort_order = sort_order;
        this.user_id = user_id;
        this.notice = notice;
        this.type = type;
        this.introduction = introduction;
        this.is_verify = is_verify;
        this.is_send = is_send;
        this.is_v = is_v;
        this.is_vip = is_vip;
        this.num = num;
        this.share_url = share_url;
        this.unReadMesCount = unReadMesCount;
    }

    @Generated(hash = 405578774)
    public GroupBean() {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
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

    public Long getTheid() {
        return this.theid;
    }

    public void setTheid(Long theid) {
        this.theid = theid;
    }
}
