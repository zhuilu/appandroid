package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class ServiceDetailBean {

    /**
     * id : 10
     * is_verify : 1
     * status : 1
     * view : 243
     * is_del : 0
     * title : 1221212
     * attr : 13138
     * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
     * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg
     * p_id : 20
     * details : sdfdsgfasdfsfsdf
     * user_id : 1376
     * is_closeComment : 0
     * remark : 211
     * is_collect : 1
     * corporate_info : {"id":9,"name":"北京百分点信息科技有限公司","company_industry":70,"city":64,"logo":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/67993091.jpg","brand":"百分点","corporate_view":2,"service_count":6}
     * service_case : [{"id":2,"service_id":10,"images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg","title":"案例第一次"},{"id":3,"service_id":10,"images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg","title":"案例第一次"},{"id":1,"service_id":10,"images":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg","thumb_img":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg","title":"案例第一次"}]
     * share_url :
     * "is_vip": 2,
     * "realname": "Dai",
     * "head_pic": "https://qqq2017.oss-cn-hangzhou.aliyuncs.com/4827971.jpg",
     * "position": "i1210",
     * "p_zlist": [
     * {
     * "id": 13137,
     * "name": "服务商标签"
     * },
     * {
     * "id": 13138,
     * "name": "211"
     * },
     * {
     * "id": 13140,
     * "name": "哈哈哈哈"
     * }
     * ]
     */

    private int id;
    private int is_verify;
    private int status;
    private int view;
    private int is_del;
    private String title;
    private String attr;
    private String images;
    private String thumb_img;
    private int p_id;
    private String p_name;
    private String details;
    private int user_id;
    private int is_closeComment;
    private String remark;
    private int is_collect;
    private CorporateInfoBean corporate_info;
    private String share_url;
    private int is_vip;
    private String realname;
    private String head_pic;
    private String position;
    private List<ServiceCaseBean> service_case;
    private List<Pzlist> p_zlist;


    public List<Pzlist> getP_zlist() {
        return p_zlist;
    }

    public void setP_zlist(List<Pzlist> p_zlist) {
        this.p_zlist = p_zlist;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
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

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_closeComment() {
        return is_closeComment;
    }

    public void setIs_closeComment(int is_closeComment) {
        this.is_closeComment = is_closeComment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public CorporateInfoBean getCorporate_info() {
        return corporate_info;
    }

    public void setCorporate_info(CorporateInfoBean corporate_info) {
        this.corporate_info = corporate_info;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<ServiceCaseBean> getService_case() {
        return service_case;
    }

    public void setService_case(List<ServiceCaseBean> service_case) {
        this.service_case = service_case;
    }

    public static class CorporateInfoBean {
        /**
         * id : 9
         * name : 北京百分点信息科技有限公司
         * company_industry : 70
         * city : 64
         * logo : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/67993091.jpg
         * brand : 百分点
         * corporate_view : 2
         * service_count : 6
         */

        private int id;
        private String name;
        private int company_industry;
        private int city;
        private String logo;
        private String brand;
        private int corporate_view;
        private int service_count;
        private int is_vip;
        private int corporate_vip;

        public int getCorporate_vip() {
            return corporate_vip;
        }

        public void setCorporate_vip(int corporate_vip) {
            this.corporate_vip = corporate_vip;
        }
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCompany_industry() {
            return company_industry;
        }

        public void setCompany_industry(int company_industry) {
            this.company_industry = company_industry;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
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

        public int getCorporate_view() {
            return corporate_view;
        }

        public void setCorporate_view(int corporate_view) {
            this.corporate_view = corporate_view;
        }

        public int getService_count() {
            return service_count;
        }

        public void setService_count(int service_count) {
            this.service_count = service_count;
        }
    }

    public static class ServiceCaseBean {
        /**
         * id : 2
         * service_id : 10
         * images : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/37826961.jpg
         * thumb_img : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/resources/20190417/thumb/37826961_thumb.jpg
         * title : 案例第一次
         */

        private int id;
        private int service_id;
        private String images;
        private String thumb_img;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Pzlist {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
