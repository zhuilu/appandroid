package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/11/29.
 */

public class GetFriendListBean {


    /**
     * "is_internal": 0,  1:是服务经理，0：不是服务经理
     * f : {"user_id":1373,"realname":"小师叔abcd","company":"千里","position":"万里ggg","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/49758392.jpg"}
     * group : [{"name":"S","list":[{"id":6,"user_id":1188,"realname":"Sdcs","company":"Qwqw12","position":"i1216","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/38214018.jpg","pinyin":"S"}]}]
     */

    private FBean f;
    private List<GroupBean> group;
    private int is_internal;

    public int getIs_internal() {
        return is_internal;
    }

    public void setIs_internal(int is_internal) {
        this.is_internal = is_internal;
    }

    public FBean getF() {
        return f;
    }

    public void setF(FBean f) {
        this.f = f;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class FBean {
        /**
         * user_id : 1373
         * realname : 小师叔abcd
         * company : 千里
         * position : 万里ggg
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/49758392.jpg
         */

        private int user_id;
        private String realname;
        private String company;
        private String position;
        private String head_pic;
        private int is_vip = 0;

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }
    }

    public static class GroupBean {
        /**
         * name : S
         * list : [{"id":6,"user_id":1188,"realname":"Sdcs","company":"Qwqw12","position":"i1216","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/38214018.jpg","pinyin":"S"}]
         */

        private String name;
        private List<ListBean> list;

        public GroupBean(String name, List<ListBean> list) {
            this.name = name;
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            public ListBean(int user_id, String realname, String company, String position, String head_pic, int is_vip) {
                this.user_id = user_id;
                this.realname = realname;
                this.company = company;
                this.position = position;
                this.head_pic = head_pic;
                this.is_vip = is_vip;
            }

            /**
             * id : 6
             * user_id : 1188
             * realname : Sdcs
             * company : Qwqw12
             * position : i1216
             * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/38214018.jpg
             * pinyin : S
             */

            private int id;
            private int user_id;
            private String realname;
            private String company;
            private String position;
            private String head_pic;
            private String pinyin;
            private int is_vip;

            public int getIs_vip() {
                return is_vip;
            }

            public void setIs_vip(int is_vip) {
                this.is_vip = is_vip;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getPinyin() {
                return pinyin;
            }

            public void setPinyin(String pinyin) {
                this.pinyin = pinyin;
            }
        }
    }
}
