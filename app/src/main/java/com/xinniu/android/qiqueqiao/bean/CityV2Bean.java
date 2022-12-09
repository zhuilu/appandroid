package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/7/31.
 */

public class CityV2Bean {

    /**
     * name : 热门城市
     * id : 0
     * zlist : [{"id":52,"name":"北京"},{"id":76,"name":"广州"},{"id":77,"name":"深圳"},{"id":180,"name":"武汉"},{"id":220,"name":"南京"},{"id":322,"name":"成都"},{"id":343,"name":"天津"},{"id":383,"name":"杭州"}]
     */

    private String name;
    private int id;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private List<ZlistBean> zlist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ZlistBean> getZlist() {
        return zlist;
    }

    public void setZlist(List<ZlistBean> zlist) {
        this.zlist = zlist;
    }

    public static class ZlistBean {
        /**
         * id : 52
         * name : 北京
         */

        private int id;
        private String name;
        private boolean isCheck;

        public ZlistBean(int id, String name, boolean isCheck) {
            this.id = id;
            this.name = name;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
