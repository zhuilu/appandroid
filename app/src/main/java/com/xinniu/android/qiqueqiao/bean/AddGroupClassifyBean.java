package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by yuchance on 2018/9/27.
 */

public class AddGroupClassifyBean implements MultiItemEntity{

    /**
     * id : 134
     * category : 4
     * name : 测试
     * sort_order : 0
     * is_show : 1
     * create_time : 1537516364
     * img :
     * describe : 12
     * is_spec : 0
     */

    private int id;
    private int category;
    private String name;
    private int sort_order;
    private int is_show;
    private int create_time;
    private String img;
    private String describe;
    private int is_spec;
    private boolean isCheck;

    public AddGroupClassifyBean(int category, String name, int itemType,boolean isCheck) {
        this.category = category;
        this.name = name;
        this.itemType = itemType;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getIs_spec() {
        return is_spec;
    }

    public void setIs_spec(int is_spec) {
        this.is_spec = is_spec;
    }

    public static final int RECOMMEND = 1;
    public static final int COMMMON = 2;
    private int itemType;

    public AddGroupClassifyBean(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
