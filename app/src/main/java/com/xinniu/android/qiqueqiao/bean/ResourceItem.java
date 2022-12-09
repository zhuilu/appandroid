package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/11/23.
 * project : qiqueqiao--- android xs
 */

public class ResourceItem {


    /**
     * c_sort : 7.1
     * realname : 口口
     * id : 243
     * cooperation_mode_cn : 友情链接
     * position : 职员
     * company : un
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/39036444.jpg
     * city : 52
     * user_id : 110
     * is_v : 0
     * is_vip : 1
     * font_size : null
     * font_color : null
     * background_color : null
     * circle_level : 1
     * words : null
     * provide_tags : [{"name":"大众用户","icon":"","id":24},{"name":"社群渠道","icon":"","id":38}]
     * need_tags : [{"name":"儿童用户","icon":"","id":40}]
     */

    private double c_sort;
    private String realname;
    private int id;
    private String cooperation_mode_cn;
    private String position;
    private String company;
    private String head_pic;
    private int city;
    private int user_id;
    private int is_v;
    private int is_vip;
    private int font_size;
    private String font_color;
    private String background_color;
    private int circle_level;
    private String words;
    private List<ProvideTagsBean> provide_tags;
    private List<NeedTagsBean> need_tags;

    public double getC_sort() {
        return c_sort;
    }

    public void setC_sort(double c_sort) {
        this.c_sort = c_sort;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCooperation_mode_cn() {
        return cooperation_mode_cn;
    }

    public void setCooperation_mode_cn(String cooperation_mode_cn) {
        this.cooperation_mode_cn = cooperation_mode_cn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_v() {
        return is_v;
    }

    public void setIs_v(int is_v) {
        this.is_v = is_v;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getFont_size() {
        return font_size;
    }

    public void setFont_size(int font_size) {
        this.font_size = font_size;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public int getCircle_level() {
        return circle_level;
    }

    public void setCircle_level(int circle_level) {
        this.circle_level = circle_level;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public List<ProvideTagsBean> getProvide_tags() {
        return provide_tags;
    }

    public void setProvide_tags(List<ProvideTagsBean> provide_tags) {
        this.provide_tags = provide_tags;
    }

    public List<NeedTagsBean> getNeed_tags() {
        return need_tags;
    }

    public void setNeed_tags(List<NeedTagsBean> need_tags) {
        this.need_tags = need_tags;
    }

    public static class ProvideTagsBean {
        /**
         * name : 大众用户
         * icon :
         * id : 24
         */

        private String name;
        private String icon;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class NeedTagsBean {
        /**
         * name : 儿童用户
         * icon :
         * id : 40
         */

        private String name;
        private String icon;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
