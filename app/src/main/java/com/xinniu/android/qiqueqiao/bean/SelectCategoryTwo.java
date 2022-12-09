package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/11/22.
 * project : qiqueqiao--- android xs
 */

public class SelectCategoryTwo {


    /**
     * data : {"hot_search":[{"id":1,"name":"大众"},{"id":2,"name":"男性"}],"hot_corporate":[{"id":3,"name":"流量"},{"id":4,"name":"企业热门"}]}
     */


    private List<HotSearchBean> hot_search;
    private List<HotCorporateBean> hot_corporate;

    public List<HotSearchBean> getHot_search() {
        return hot_search;
    }

    public void setHot_search(List<HotSearchBean> hot_search) {
        this.hot_search = hot_search;
    }

    public List<HotCorporateBean> getHot_corporate() {
        return hot_corporate;
    }

    public void setHot_corporate(List<HotCorporateBean> hot_corporate) {
        this.hot_corporate = hot_corporate;
    }

    public static class HotSearchBean {
        /**
         * id : 1
         * name : 大众
         */

        private int id;
        private String name;

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

    public static class HotCorporateBean {
        /**
         * id : 3
         * name : 流量
         */

        private int id;
        private String name;

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
