package com.xinniu.android.qiqueqiao.bean;

public class AddGroupBean {

    /**
     * group_id : 14
     * "old_group_id": 0,
     * "new_group_id": "5"
     * "id":1
     */

    private int group_id;

    private int old_group_id;
    private int new_group_id;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getOld_group_id() {
        return old_group_id;
    }

    public void setOld_group_id(int old_group_id) {
        this.old_group_id = old_group_id;
    }

    public int getNew_group_id() {
        return new_group_id;
    }

    public void setNew_group_id(int new_group_id) {
        this.new_group_id = new_group_id;
    }
}
