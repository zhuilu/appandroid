package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2019/3/18.
 * project : newbridge--- android xs
 */

public class ReleaseTemplateBean {
    /**
     * id : 3
     * p_id : 3
     * title : 1222
     * provide_remark : [{"name":"你好"},{"name":"何佳佳"}]
     * need_remark : [{"name":"电商销售渠道"},{"name":"小星星"}]
     * need_describe : 4444
     * provide_describe : 213123
     *provide_category_title
     *  need_category_title
     *provide_description_title
     *need_description_title
     */

    private int id;
    private int p_id;
    private String title;
    private String need_describe;
    private String provide_describe;
    private List<ProvideRemarkBean> provide_tags;
    private List<NeedRemarkBean> need_tags;
    private String provide_category_title;
    private String need_category_title;
    private String provide_description_title;
    private String need_description_title;

    public String getProvide_category_title() {
        return provide_category_title;
    }

    public void setProvide_category_title(String provide_category_title) {
        this.provide_category_title = provide_category_title;
    }

    public String getNeed_category_title() {
        return need_category_title;
    }

    public void setNeed_category_title(String need_category_title) {
        this.need_category_title = need_category_title;
    }

    public String getProvide_description_title() {
        return provide_description_title;
    }

    public void setProvide_description_title(String provide_description_title) {
        this.provide_description_title = provide_description_title;
    }

    public String getNeed_description_title() {
        return need_description_title;
    }

    public void setNeed_description_title(String need_description_title) {
        this.need_description_title = need_description_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNeed_describe() {
        return need_describe;
    }

    public void setNeed_describe(String need_describe) {
        this.need_describe = need_describe;
    }

    public String getProvide_describe() {
        return provide_describe;
    }

    public void setProvide_describe(String provide_describe) {
        this.provide_describe = provide_describe;
    }

    public List<ProvideRemarkBean> getProvide_tags() {
        return provide_tags;
    }

    public void setProvide_tags(List<ProvideRemarkBean> provide_tags) {
        this.provide_tags = provide_tags;
    }

    public List<NeedRemarkBean> getNeed_tags() {
        return need_tags;
    }

    public void setNeed_tags(List<NeedRemarkBean> need_tags) {
        this.need_tags = need_tags;
    }

    public static class ProvideRemarkBean {
        /**
         * name : 你好
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NeedRemarkBean {
        /**
         * name : 电商销售渠道
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
