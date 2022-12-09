package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public class ResouceInfoBean {

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    /**
     * id : 43
     * need_remark : 需求备注
     * provide_remark : 提供备注
     * cooperation_mode_cn : 联合营销,流量互换
     * view : 13
     * talk : 0
     * is_hot : 0
     * realname : 李四
     * position : null
     * company :
     * company_name : null
     * head_pic : http://img.qiqueqiao.com/sys/default.jpg
     * city : null
     * user_id : 2
     * need_describe : null
     * provide_describe : null
     * create_time : 1511487811
     * city_name : null
     * province_name : null
     * follow_status : 0
     * talk_num : 2
     * z_list : [{"id":15,"create_time":1512556566,"user_id":1,"head_pic":"1","realname":"111","company":"12335","company_name":null,"position":"wqewqe"},{"id":74,"create_time":1512635816,"user_id":3,"head_pic":"http://img.qiqueqiao.com/sys/default.jpg","realname":"王五","company":"null","company_name":null,"position":"null"}]
     */
    private String share_url;
    private int id;
    private String need_remark;
    private String provide_remark;
    private String cooperation_mode_cn;
    private int view;
    private int talk;
    private int is_hot;
    private String realname;
    private String position;
    private String company;
    private String company_name;
    private String head_pic;
    private String city;
    private int user_id;
    private String need_describe;
    private String provide_describe;
    private long create_time;
    private String city_name;
    private String province_name;
    private int follow_status;
    private int talk_num;
    private List<ZListBean> z_list;
    private String provide_img;
    private String need_img;
    private int is_v;
    private int corporate_id;
    private int follow_num;
    private long last_login;


    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
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

    public String getProvide_img() {
        return provide_img;
    }

    public void setProvide_img(String provide_img) {
        this.provide_img = provide_img;
    }

    public String getNeed_img() {
        return need_img;
    }

    public void setNeed_img(String need_img) {
        this.need_img = need_img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public long getCreate_time() {
        return create_time*1000;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(int follow_status) {
        this.follow_status = follow_status;
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

    public static class ZListBean {
        /**
         * id : 15
         * create_time : 1512556566
         * user_id : 1
         * head_pic : 1
         * realname : 111
         * company : 12335
         * company_name : null
         * position : wqewqe
         */

        private int id;
        private long create_time;
        private int user_id;
        private String head_pic;
        private String realname;
        private String company;
        private String company_name;
        private String position;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreate_time() {
            return create_time * 1000;
        }

        public void setCreate_time(long create_time) {
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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
