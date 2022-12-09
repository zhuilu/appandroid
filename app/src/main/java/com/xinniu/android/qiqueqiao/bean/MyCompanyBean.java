package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by lzq on 2018/2/28.
 */

public class MyCompanyBean {

    /**
     * id : 6
     * name : 摆渡
     * introduce :
     * introduce_verify 	公司介绍是否审核同，1通过，0未审核
     * logo :
     * brand : 百度
     * city : 中国
     * status : 0
     * url :
     * user_isv : 0
     * user_isjoin : 1
     * company_name : 金融/银行
     * h5_share_url:""
     * resources_count : 9
     * users : [{"user_id":1,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg","realname":"12","position":"1111"},{"user_id":2,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg","realname":"fanfan","position":"php"},{"user_id":3,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg","realname":"王五","is_v":1,"position":"chanpin"},{"user_id":100,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg","realname":"盖伦啊","is_v":1,"position":"服务经理"},{"user_id":101,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/64111668.jpg","realname":"炜哥","is_v":1,"position":"服务经理"}]
     * "service_info": {
     * "id": 10,
     * "title": "1221212",
     * "images": "https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg",
     * "thumb_img": "https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg",
     * "attr": "13138",
     * "head_pic": "https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132",
     * "realname": "曾杨",
     * "position": "经理",
     * "remark": "211"
     * }
     */

    private int id;
    private String name;
    private String introduce;
    private String logo;
    private String brand;
    private int city;
    private int status;
    private String url;
    private int user_isv;
    private int user_isjoin;
    private String company_name;
    private int resources_count;
    private int user_num;
    private String city_name;
    private List<UsersBean> users;
    private int company_industry;
    private ServiceInfo service_info;
    private String share_url;
    private int introduce_verify;

    public int getIntroduce_verify() {
        return introduce_verify;
    }

    public void setIntroduce_verify(int introduce_verify) {
        this.introduce_verify = introduce_verify;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ServiceInfo getService_info() {
        return service_info;
    }

    public void setService_info(ServiceInfo service_info) {
        this.service_info = service_info;
    }

    public int getCompany_industry() {
        return company_industry;
    }

    public void setCompany_industry(int company_industry) {
        this.company_industry = company_industry;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getUser_num() {
        return user_num;
    }

    public void setUser_num(int user_num) {
        this.user_num = user_num;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUser_isv() {
        return user_isv;
    }

    public void setUser_isv(int user_isv) {
        this.user_isv = user_isv;
    }

    public int getUser_isjoin() {
        return user_isjoin;
    }

    public void setUser_isjoin(int user_isjoin) {
        this.user_isjoin = user_isjoin;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getResources_count() {
        return resources_count;
    }

    public void setResources_count(int resources_count) {
        this.resources_count = resources_count;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean implements MultiItemEntity {
        /**
         * user_id : 1
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/72326322.jpg
         * realname : 12
         * position : 1111
         * is_v : 1
         */

        private int user_id;
        private String head_pic;
        private String realname;
        private String position;
        private int is_v;


        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public static final int TEXT = 1;
        public static final int IMG = 2;
        private int itemType;

        public UsersBean(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

    public static class ServiceInfo {

        /**
         * id : 10
         * title : 1221212
         * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
         * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg
         * attr : 13138
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * realname : 曾杨
         * position : 经理
         * remark : 211
         */

        private int id;
        private String title;
        private String images;
        private String thumb_img;
        private String attr;
        private String head_pic;
        private String realname;
        private String position;
        private String remark;
        private int is_v;

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
