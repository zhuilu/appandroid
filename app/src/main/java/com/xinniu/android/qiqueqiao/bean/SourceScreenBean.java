package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/4/16.
 */

public class SourceScreenBean {


    private String offerneed;

    public String getOfferneed() {
        return offerneed;
    }

    public void setOfferneed(String offerneed) {
        this.offerneed = offerneed;
    }

    private List<CategoryListBean> category_list;
    private List<CooperationListBean> cooperation_list;
    private List<CompanyListBean> company_list;

    public List<CategoryListBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<CategoryListBean> category_list) {
        this.category_list = category_list;
    }

    public List<CooperationListBean> getCooperation_list() {
        return cooperation_list;
    }

    public void setCooperation_list(List<CooperationListBean> cooperation_list) {
        this.cooperation_list = cooperation_list;
    }

    public List<CompanyListBean> getCompany_list() {
        return company_list;
    }

    public void setCompany_list(List<CompanyListBean> company_list) {
        this.company_list = company_list;
    }

    public static class CategoryListBean {
        /**
         * id : 3710
         * name : 可互换*广告位
         */

        private int id;
        private String name;
        private boolean isCheck;

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

    public static class CooperationListBean {
        /**
         * id : 54
         * name : 广告位互换
         */

        private int id;
        private String name;
        private boolean isCheck;

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

    public static class CompanyListBean {
        /**
         * id : 98
         * name : 物联网
         */

        private int id;
        private String name;
        private boolean isCheck;
        private String headUrl;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
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
