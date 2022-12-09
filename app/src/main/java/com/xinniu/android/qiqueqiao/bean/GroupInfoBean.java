package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuchance on 2018/10/10.
 */

public class GroupInfoBean {


    /**
     * id : 2
     * name : 互联网Android合作圈子
     * number : 100
     * group_number : 200000
     * num : 24
     * status : 1
     * is_verify : 1
     * notice : 圈子公告：该圈为互联网行业交流圈，以合作交流为主。切勿发其他广告信息
     * introduction :
     * city_name : 全国
     * city : 1
     * category_name : 教育机构
     * category : 137
     * user_id : 1044
     * share_url : http://t.qiqueqiao.com/share/circle/2
     * resources_count : 374
     * user_identity : 0
     * userList : [{"user_id":2,"realname":"123","head_pic":"1","identity":0,"company":"123","position":"1234","last_login":1535686647,"is_black":0,"pinyin":""},{"user_id":117,"realname":"11","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/43866206.jpg","identity":0,"company":"123","position":"1","last_login":1526024406,"is_black":0,"pinyin":""},{"user_id":102,"realname":"船长","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/73574257.jpg","identity":0,"company":"企鹊桥","position":"服务经理","last_login":1528855748,"is_black":0,"pinyin":"C"},{"user_id":104,"realname":"东北猫","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/89087226.jpg","identity":0,"company":"企鹊桥","position":"服务经理","last_login":1519989003,"is_black":0,"pinyin":"D"},{"user_id":104,"realname":"东北猫","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/89087226.jpg","identity":0,"company":"企鹊桥","position":"服务经理","last_login":1519989003,"is_black":0,"pinyin":"D"}]
     */

    private int id;
    private String name;
    private int number;
    private String group_number;
    private int num;
    private int status;
    private int is_verify;
    private String notice;
    private String introduction;
    private String city_name;
    private int city;
    private String category_name;
    private int category;
    private int user_id;
    private String share_url;
    private int resources_count;
    private int user_identity;
    private List<UserListBean> userList;
    private String head_pic;

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGroup_number() {
        return group_number;
    }

    public void setGroup_number(String group_number) {
        this.group_number = group_number;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getResources_count() {
        return resources_count;
    }

    public void setResources_count(int resources_count) {
        this.resources_count = resources_count;
    }

    public int getUser_identity() {
        return user_identity;
    }

    public void setUser_identity(int user_identity) {
        this.user_identity = user_identity;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean implements MultiItemEntity{
        /**
         * user_id : 2
         * realname : 123
         * head_pic : 1
         * identity : 0
         * company : 123
         * position : 1234
         * last_login : 1535686647
         * is_black : 0
         * pinyin :
         */

        private int user_id;
        private String realname;
        private String head_pic;
        private int identity;
        private String company;
        private String position;
        private int last_login;
        private int is_black;
        private String pinyin;

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
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

        public int getLast_login() {
            return last_login;
        }

        public void setLast_login(int last_login) {
            this.last_login = last_login;
        }

        public int getIs_black() {
            return is_black;
        }

        public void setIs_black(int is_black) {
            this.is_black = is_black;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public static final int INVITE = 1;
        public static final int COMMON = 2;
        private int itemType;

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public UserListBean(int itemType) {
            this.itemType = itemType;
        }
    }
}
