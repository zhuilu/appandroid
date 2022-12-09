package com.xinniu.android.qiqueqiao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description :
 */

public class CenterBean {
    /**
     * users : {"realname":"云","user_id":1,"head_pic":"1","card":"http://img.qiqueqiao.com/sys/default.jpg","company":"公司名称","position":"职位简介","is_v":0,"vip":1,"vip_name":"高级会员","font_size":"11","font_color":"D79D29","background_color":"FFFBF2",  "is_corporate_vip": 1,}
     * followCount : 2
     * circleCount : 1
     * message : 你的会员权限将在5天后到期
     * release : 2
     * welfare_club：1 //是否显示福利社 1显示，0不显示
     * resources : [{"id":53,"provide_remark":"我提供1-10万全网大众App线上资源","need_remark":"我需要1-10万18大行业男性公众号线上资源","cooperation_mode_cn":"联合地推","view":0,"talk":0},{"id":44,"provide_remark":"提供备注","need_remark":"需求备注","cooperation_mode_cn":"联合营销,流量互换","view":0,"talk":0}]
     */

    private UsersBean users;
    private int followCount;
    private int circleCount;
    private String message;
    private int release;
    private int info_status;
    private int over_staus;
    private int collectCount;
    private int comment_count;
    private int welfare_club;
    private int talk_count;

    public int getTalk_count() {
        return talk_count;
    }

    public void setTalk_count(int talk_count) {
        this.talk_count = talk_count;
    }

    public int getWelfare_club() {
        return welfare_club;
    }

    public void setWelfare_club(int welfare_club) {
        this.welfare_club = welfare_club;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public int getOver_staus() {
        return over_staus;
    }

    public void setOver_staus(int over_staus) {
        this.over_staus = over_staus;
    }

    public int getInfo_status() {
        return info_status;
    }

    public void setInfo_status(int info_status) {
        this.info_status = info_status;
    }

    private List<ResourcesBean> resources;

    public UsersBean getUsers() {
        if (users==null){
            return new UsersBean();
        }
        return users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public List<ResourcesBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesBean> resources) {
        this.resources = resources;
    }

    public static class UsersBean {
        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        private int is_join;

        public int getIs_join() {
            return is_join;
        }

        public void setIs_join(int is_join) {
            this.is_join = is_join;
        }

        /**
         * realname : 云
         * user_id : 1
         * head_pic : 1
         * card : http://img.qiqueqiao.com/sys/default.jpg
         * company : 公司名称
         * position : 职位简介
         * is_v : 0
         * vip : 1
         * vip_name : 高级会员
         * font_size : 11
         * font_color : D79D29
         * background_color : FFFBF2
         *   "is_corporate_vip": 1,
         *   "is_cloud_auth": 1,
         *   "identification_name": "xxxx"
         *   corporate_name
         */
        private String num;
        private String realname;
        private int user_id;
        private String head_pic;
        private String card;
        private String company;
        private String position;
        private int is_v;
        private int vip;
        private String vip_name;
        private String font_size;
        private String font_color;
        private String background_color;
        private int corporate_id;
        private int is_vip;
        private int is_sign;
        private int is_corporate_vip;
        private int is_cloud_auth;
        private String identification_name;
        private String corporate_name;

        public String getCorporate_name() {
            return corporate_name;
        }

        public void setCorporate_name(String corporate_name) {
            this.corporate_name = corporate_name;
        }

        public int getIs_cloud_auth() {
            return is_cloud_auth;
        }

        public void setIs_cloud_auth(int is_cloud_auth) {
            this.is_cloud_auth = is_cloud_auth;
        }

        public String getIdentification_name() {
            return identification_name;
        }

        public void setIdentification_name(String identification_name) {
            this.identification_name = identification_name;
        }

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getCorporate_id() {
            return corporate_id;
        }

        public void setCorporate_id(int corporate_id) {
            this.corporate_id = corporate_id;
        }

        public int getF_id() {
            return f_id;
        }

        public void setF_id(int f_id) {
            this.f_id = f_id;
        }

        private int f_id;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
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

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
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

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public String getVip_name() {
            return vip_name;
        }

        public void setVip_name(String vip_name) {
            this.vip_name = vip_name;
        }

        public String getFont_size() {
            return font_size;
        }

        public void setFont_size(String font_size) {
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

        @Override
        public String toString() {
            return "UsersBean{" +
                    "realname='" + realname + '\'' +
                    ", user_id=" + user_id +
                    ", head_pic='" + head_pic + '\'' +
                    ", card='" + card + '\'' +
                    ", company='" + company + '\'' +
                    ", position='" + position + '\'' +
                    ", is_v=" + is_v +
                    ", vip=" + vip +
                    ", vip_name='" + vip_name + '\'' +
                    ", font_size='" + font_size + '\'' +
                    ", font_color='" + font_color + '\'' +
                    ", background_color='" + background_color + '\'' +
                    '}';
        }
    }

    public static class ResourcesBean implements Parcelable {
        /**
         * id : 53
         * provide_remark : 我提供1-10万全网大众App线上资源
         * need_remark : 我需要1-10万18大行业男性公众号线上资源
         * cooperation_mode_cn : 联合地推
         * view : 0
         * talk : 0
         */

        private String need_describe;
        private String provide_describe;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private String share_url;
        private int id;
        private String provide_remark;

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        private String need_remark;
        private String cooperation_mode_cn;
        private int view;
        private int talk;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvide_remark() {
            return provide_remark;
        }

        public void setProvide_remark(String provide_remark) {
            this.provide_remark = provide_remark;
        }

        public String getNeed_remark() {
            return need_remark;
        }

        public void setNeed_remark(String need_remark) {
            this.need_remark = need_remark;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.provide_remark);
            dest.writeString(this.need_remark);
            dest.writeString(this.cooperation_mode_cn);
            dest.writeInt(this.view);
            dest.writeInt(this.talk);
        }

        public ResourcesBean() {
        }

        protected ResourcesBean(Parcel in) {
            this.id = in.readInt();
            this.provide_remark = in.readString();
            this.need_remark = in.readString();
            this.cooperation_mode_cn = in.readString();
            this.view = in.readInt();
            this.talk = in.readInt();
        }

        public static final Parcelable.Creator<ResourcesBean> CREATOR = new Parcelable.Creator<ResourcesBean>() {
            @Override
            public ResourcesBean createFromParcel(Parcel source) {
                return new ResourcesBean(source);
            }

            @Override
            public ResourcesBean[] newArray(int size) {
                return new ResourcesBean[size];
            }
        };

        @Override
        public String toString() {
            return "ResourcesBean{" +
                    "id=" + id +
                    ", provide_remark='" + provide_remark + '\'' +
                    ", need_remark='" + need_remark + '\'' +
                    ", cooperation_mode_cn='" + cooperation_mode_cn + '\'' +
                    ", view=" + view +
                    ", talk=" + talk +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CenterBean{" +
                "users=" + users +
                ", followCount=" + followCount +
                ", circleCount=" + circleCount +
                ", message='" + message + '\'' +
                ", release=" + release +
                ", resources=" + resources +
                '}';
    }
}
