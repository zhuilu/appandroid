package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/11/22.
 * project : qiqueqiao--- android xs
 */

public class SelectCategory {

    /**
     * id : 8
     * name : 资源类型
     * level : 2
     * z_index ：[]
     */
    private int id;
    private String name;
    private int level;
    private List<SelectCategory> z_index;
    private boolean isCheck;//是否选中,自定义添加的字段
    private boolean isLaunch;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public SelectCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SelectCategory(int id, String name, int level, List<SelectCategory> z_index, boolean isCheck, boolean isLaunch, int is_custom) {

        this.id = id;
        this.name = name;
        this.level = level;
        this.z_index = z_index;
        this.isCheck = isCheck;
        this.isLaunch = isLaunch;
        this.is_custom = is_custom;
    }

    public SelectCategory(int id, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
    }

    public SelectCategory() {
    }
    public boolean isLaunch() {
        return isLaunch;
    }

    public void setLaunch(boolean launch) {
        isLaunch = launch;
    }

    public int getIs_custom() {
        return is_custom;
    }

    public void setIs_custom(int is_custom) {
        this.is_custom = is_custom;
    }

    private int is_custom;


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<SelectCategory> getZ_index() {
        return z_index;
    }

    public void setZ_index(List<SelectCategory> z_index) {
        this.z_index = z_index;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
