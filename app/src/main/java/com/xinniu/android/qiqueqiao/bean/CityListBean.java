package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public class CityListBean {

    private List<HostCityBean> host_city;
    private List<GroupBean> group;

    public List<HostCityBean> getHost_city() {
        return host_city;
    }

    public void setHost_city(List<HostCityBean> host_city) {
        this.host_city = host_city;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class HostCityBean {
        /**
         * id : 41
         * name : 阜阳
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

    public static class GroupBean {
        /**
         * pinyin : a
         * list : [{"id":36,"name":"安庆"},{"id":112,"name":"安顺"},{"id":152,"name":"安阳"},{"id":246,"name":"鞍山"},{"id":259,"name":"阿拉善盟"},{"id":312,"name":"安康"},{"id":324,"name":"阿坝"},{"id":345,"name":"阿里"},{"id":352,"name":"阿克苏"},{"id":353,"name":"阿拉尔"},{"id":396,"name":"澳门"}]
         */

        private String pinyin;
        private List<ListBean> list;

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 36
             * name : 安庆
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
}
