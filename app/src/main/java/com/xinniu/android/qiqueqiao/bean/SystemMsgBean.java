package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lzq on 2017/12/25.
 */

public class SystemMsgBean implements MultiItemEntity {






    public static final int GROUPTYPE = 1;
    public static final int COMMMON = 2;
    private int itemType;
    /**
     * realname : 12
     * create_time : 1526376014
     * content : 测试内容
     * title : 标题
     * url : http://211.70.176.153/
     * type : 1
     * resource_id : 0
     * head_pic :
     * application_status : 1
     * operate_identity : 1
     * operate_username :
     * join_id : 44
     * uid :
     */

    private String realname;
    private long create_time;
    private String content;
    private String title;
    private String url;
    private int type;
    private int resource_id;
    private String head_pic;
    private int application_status;
    private int operate_identity;
    private String operate_username;
    private int join_id;
    private String uid;
    private boolean isNew = false;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public SystemMsgBean(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getApplication_status() {
        return application_status;
    }

    public void setApplication_status(int application_status) {
        this.application_status = application_status;
    }

    public int getOperate_identity() {
        return operate_identity;
    }

    public void setOperate_identity(int operate_identity) {
        this.operate_identity = operate_identity;
    }

    public String getOperate_username() {
        return operate_username;
    }

    public void setOperate_username(String operate_username) {
        this.operate_username = operate_username;
    }

    public int getJoin_id() {
        return join_id;
    }

    public void setJoin_id(int join_id) {
        this.join_id = join_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
