package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class CoopDetailBean implements Serializable{


    /**
     * id : 4245
     * status : 1
     * images :
     * title : 提供10万以上金融用户借条系统资源
     * need_remark : null
     * provide_remark : 10万以上金融用户借条系统资源
     * cooperation_mode_cn : 方式不限
     * view : 26
     * talk : 1
     * is_hot : 0
     * realname : 张雷
     * nickname : 企鹊桥1923738775
     * position : 商务总监
     * last_login : 1521514982
     * company : 睿灯信贷云借条系统+企业薪酬福利管理
     * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/87152462.jpg
     * city : 321
     * user_id : 1545
     * company_industry : 22
     * corporate_id : 0
     * is_v : 0
     * need_describe : null
     * provide_describe : 全新的C2C借条系统，提供获客+风控+催收全流程运营服务，saas合作模式，价格低低低
     * create_time : 1516862807
     * city_name : 上海
     * province_name : null
     * follow_num : 2
     * company_name : 金融/银行
     * follow_status : 0
     * share_url : http://q.qiqueqiao.com/share/resources/4245
     * talk_num : 1
     * reservation_status：4
     * is_internal:0//是否服务经理
     * is_cloud_auth:1
     * z_list : [{"id":32259,"create_time":1520567403,"user_id":7529,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/48968651.jpg","realname":"仲海钦","is_vip":0,"company":"信用百科","position":"运营总监"}]
     */

    private int id;
    private int status;
    private String images;
    private String thumb_img;
    private String title;
    private String need_remark;
    private String provide_remark;
    private String cooperation_mode_cn;
    private int view;
    private int talk;
    private int is_hot;
    private String realname;
    private String nickname;
    private String position;
    private int last_login;
    private String company;
    private String head_pic;
    private int city;
    private int user_id;
    private int company_industry;
    private int corporate_id;
    private int is_v;
    private String need_describe;
    private String provide_describe;
    private int create_time;
    private String city_name;
    private String province_name;
    private int follow_num;
    private String company_name;
    private int follow_status;
    private String share_url;
    private int talk_num;
    private int is_vip;
    private String wechat_url;
    private int is_verify;
    private int is_collect;
    private String qrcode_url;
    private int is_del;
    private int hasMore;
    private int p_id;
    private String p_name;
    private String provide_category_title;
    private String need_category_title;
    private String provide_description_title;
    private String need_description_title;
    private int is_colleagueTalk;//	1:沟通人列表里面有同事，0,：没有
    private int is_closeComment;//是否关闭留言，1：关闭，0:开启
    private int is_corporate_vip = 0;
    private int reservation_status;
    private int is_internal;
    private int is_transaction;
    private int is_cloud_auth;
    private List<ProvideCategoryBean> provide_category=new ArrayList<>();
    private List<NeedCategoryBean> need_category=new ArrayList<>();


    public int getIs_cloud_auth() {
        return is_cloud_auth;
    }

    public void setIs_cloud_auth(int is_cloud_auth) {
        this.is_cloud_auth = is_cloud_auth;
    }
    public int getIs_transaction() {
        return is_transaction;
    }

    public void setIs_transaction(int is_transaction) {
        this.is_transaction = is_transaction;
    }

    public int getIs_internal() {
        return is_internal;
    }

    public void setIs_internal(int is_internal) {
        this.is_internal = is_internal;
    }

    public int getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(int reservation_status) {
        this.reservation_status = reservation_status;
    }

    public int getIs_corporate_vip() {
        return is_corporate_vip;
    }

    public void setIs_corporate_vip(int is_corporate_vip) {
        this.is_corporate_vip = is_corporate_vip;
    }

    public int getIs_colleagueTalk() {
        return is_colleagueTalk;
    }

    public void setIs_colleagueTalk(int is_colleagueTalk) {
        this.is_colleagueTalk = is_colleagueTalk;
    }

    public int getIs_closeComment() {
        return is_closeComment;
    }

    public void setIs_closeComment(int is_closeComment) {
        this.is_closeComment = is_closeComment;
    }

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

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
    }

    /**
     * corporate_info : {"id":1,"brand":"平拍","logo":"","name":"杭州摆渡文化传媒有限公司","company_industry":1,"company_name":"","user_num":7,"resources_count":0}
     */

    private CorporateInfoBean corporate_info;


    public String getWechat_url() {
        return wechat_url;
    }


    public void setWechat_url(String wechat_url) {
        this.wechat_url = wechat_url;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    private List<ZListBean> z_list;
    private List<ProvideTags> provide_tags;
    private List<NeedTags> need_tags;

    public List<NeedTags> getNeed_tags() {
        return need_tags;
    }

    public void setNeed_tags(List<NeedTags> need_tags) {
        this.need_tags = need_tags;
    }

    public List<ProvideTags> getProvide_tags() {
        return provide_tags;
    }

    public void setProvide_tags(List<ProvideTags> provide_tags) {
        this.provide_tags = provide_tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNeed_remark() {
        return need_remark;
    }

    public void setNeed_remark(String need_remark) {
        this.need_remark = need_remark;
    }

    public String getProvide_remark() {
        return provide_remark;
    }

    public void setProvide_remark(String provide_remark) {
        this.provide_remark = provide_remark;
    }

    public String getCooperation_mode_cn() {
        return cooperation_mode_cn;
    }

    public void setCooperation_mode_cn(String cooperation_mode_cn) {
        this.cooperation_mode_cn = cooperation_mode_cn;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getTalk() {
        return talk;
    }

    public void setTalk(int talk) {
        this.talk = talk;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getCompany_industry() {
        return company_industry;
    }

    public void setCompany_industry(int company_industry) {
        this.company_industry = company_industry;
    }

    public int getCorporate_id() {
        return corporate_id;
    }

    public void setCorporate_id(int corporate_id) {
        this.corporate_id = corporate_id;
    }

    public int getIs_v() {
        return is_v;
    }

    public void setIs_v(int is_v) {
        this.is_v = is_v;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Object getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getTalk_num() {
        return talk_num;
    }

    public void setTalk_num(int talk_num) {
        this.talk_num = talk_num;
    }

    public List<ZListBean> getZ_list() {
        return z_list;
    }

    public void setZ_list(List<ZListBean> z_list) {
        this.z_list = z_list;
    }

    public CorporateInfoBean getCorporate_info() {
        return corporate_info;
    }

    public void setCorporate_info(CorporateInfoBean corporate_info) {
        this.corporate_info = corporate_info;
    }

    public List<ProvideCategoryBean> getProvide_category() {
        return provide_category;
    }

    public void setProvide_category(List<ProvideCategoryBean> provide_category) {
        this.provide_category = provide_category;
    }

    public List<NeedCategoryBean> getNeed_category() {
        return need_category;
    }

    public void setNeed_category(List<NeedCategoryBean> need_category) {
        this.need_category = need_category;
    }

    public static class ZListBean implements MultiItemEntity,Serializable{
        /**
         * id : 32259
         * create_time : 1520567403
         * user_id : 7529
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/48968651.jpg
         * realname : 仲海钦
         * is_vip : 0
         * company : 信用百科
         * position : 运营总监
         */

        private int id;
        private int create_time;
        private int user_id;
        private String head_pic;
        private String realname;
        private int is_vip;
        private String company;
        private String position;
        private int is_corporate_vip = 0;

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

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

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
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

        public static final int COMMON = 1;
        public static final int MORE = 2;
        private int itemType;
        public ZListBean(int itemType) {
            this.itemType = itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return this.itemType;
        }
    }
    public static class ProvideTags implements Serializable{
        private String name;
        private int id;

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
    }
    public static class NeedTags implements Serializable{
        private String name;
        private int id;

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
    }


    public static class CorporateInfoBean implements Serializable{
        /**
         * id : 1
         * brand : 平拍
         * logo :
         * name : 杭州摆渡文化传媒有限公司
         * company_industry : 1
         * company_name :
         * user_num : 7
         * resources_count : 0
         */

        private int id;
        private String brand;
        private String logo;
        private String name;
        private int company_industry;
        private String company_name;
        private int user_num;
        private int resources_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public int getUser_num() {
            return user_num;
        }

        public void setUser_num(int user_num) {
            this.user_num = user_num;
        }

        public int getResources_count() {
            return resources_count;
        }

        public void setResources_count(int resources_count) {
            this.resources_count = resources_count;
        }
    }

    public static class ProvideCategoryBean {
        /**
         * id : 1
         * title : 提供标签1111
         * list : [{"id":13139,"name":"12222","is_custom":0,"is_type":1,"user_id":0,"sort_order":0,"title_id":1}]
         */

        @SerializedName("id")
        private int idX;
        @SerializedName("title")
        private String titleX;
        private List<ListBean> list;

        public int getIdX() {
            return idX;
        }

        public void setIdX(int idX) {
            this.idX = idX;
        }

        public String getTitleX() {
            return titleX;
        }

        public void setTitleX(String titleX) {
            this.titleX = titleX;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 13139
             * name : 12222
             * is_custom : 0
             * is_type : 1
             * user_id : 0
             * sort_order : 0
             * title_id : 1
             */

            @SerializedName("id")
            private int idX;
            private String name;
            private int is_custom;
            private int is_type;
            @SerializedName("user_id")
            private int user_idX;
            private int sort_order;
            private int title_id;

            public int getIdX() {
                return idX;
            }

            public void setIdX(int idX) {
                this.idX = idX;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIs_custom() {
                return is_custom;
            }

            public void setIs_custom(int is_custom) {
                this.is_custom = is_custom;
            }

            public int getIs_type() {
                return is_type;
            }

            public void setIs_type(int is_type) {
                this.is_type = is_type;
            }

            public int getUser_idX() {
                return user_idX;
            }

            public void setUser_idX(int user_idX) {
                this.user_idX = user_idX;
            }

            public int getSort_order() {
                return sort_order;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public int getTitle_id() {
                return title_id;
            }

            public void setTitle_id(int title_id) {
                this.title_id = title_id;
            }
        }
    }

    public static class NeedCategoryBean {
        /**
         * id : 3
         * title : 测试发布
         * list : [{"id":11980,"name":"电商销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":2,"title_id":3},{"id":13088,"name":"阿里啦咯了","is_custom":0,"is_type":0,"user_id":1320,"sort_order":50,"title_id":0}]
         */

        @SerializedName("id")
        private int idX;
        @SerializedName("title")
        private String titleX;
        private List<ListBeanX> list;

        public int getIdX() {
            return idX;
        }

        public void setIdX(int idX) {
            this.idX = idX;
        }

        public String getTitleX() {
            return titleX;
        }

        public void setTitleX(String titleX) {
            this.titleX = titleX;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class ListBeanX {
            /**
             * id : 11980
             * name : 电商销售渠道
             * is_custom : 0
             * is_type : 0
             * user_id : 0
             * sort_order : 2
             * title_id : 3
             */

            @SerializedName("id")
            private int idX;
            private String name;
            private int is_custom;
            private int is_type;
            @SerializedName("user_id")
            private int user_idX;
            private int sort_order;
            private int title_id;

            public int getIdX() {
                return idX;
            }

            public void setIdX(int idX) {
                this.idX = idX;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIs_custom() {
                return is_custom;
            }

            public void setIs_custom(int is_custom) {
                this.is_custom = is_custom;
            }

            public int getIs_type() {
                return is_type;
            }

            public void setIs_type(int is_type) {
                this.is_type = is_type;
            }

            public int getUser_idX() {
                return user_idX;
            }

            public void setUser_idX(int user_idX) {
                this.user_idX = user_idX;
            }

            public int getSort_order() {
                return sort_order;
            }

            public void setSort_order(int sort_order) {
                this.sort_order = sort_order;
            }

            public int getTitle_id() {
                return title_id;
            }

            public void setTitle_id(int title_id) {
                this.title_id = title_id;
            }
        }
    }
}