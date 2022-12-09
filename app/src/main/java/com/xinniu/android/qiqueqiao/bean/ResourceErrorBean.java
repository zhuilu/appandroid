package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2018/1/26.
 */

public class ResourceErrorBean {


    /**
     * code : 220
     * msg : 该资源已经被下架
     * data : {"id":4262,"status":0,"need_img":null,"provide_img":null,"need_remark":null,"provide_remark":"车身广告可互换广告位","cooperation_mode_cn":"资源互换","view":0,"talk":0,"is_hot":0,"realname":"柳钟泉","nickname":"企鹊桥8469776416","position":"安卓","company":"摆渡星空","head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/62495764.jpg","city":61,"user_id":164,"is_v":1,"need_describe":null,"provide_describe":null,"create_time":1516947693,"city_name":"漳州","province_name":null}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4262
         * status : 0
         * need_img : null
         * provide_img : null
         * need_remark : null
         * provide_remark : 车身广告可互换广告位
         * cooperation_mode_cn : 资源互换
         * view : 0
         * talk : 0
         * is_hot : 0
         * realname : 柳钟泉
         * nickname : 企鹊桥8469776416
         * position : 安卓
         * company : 摆渡星空
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/62495764.jpg
         * city : 61
         * user_id : 164
         * is_v : 1
         * need_describe : null
         * provide_describe : null
         * create_time : 1516947693
         * city_name : 漳州
         * province_name : null
         */

        private int id;
        private int status;
        private Object need_img;
        private Object provide_img;
        private Object need_remark;
        private String provide_remark;
        private String cooperation_mode_cn;
        private int view;
        private int talk;
        private int is_hot;
        private String realname;
        private String nickname;
        private String position;
        private String company;
        private String head_pic;
        private int city;
        private int user_id;
        private int is_v;
        private Object need_describe;
        private Object provide_describe;
        private int create_time;
        private String city_name;
        private Object province_name;

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

        public Object getNeed_img() {
            return need_img;
        }

        public void setNeed_img(Object need_img) {
            this.need_img = need_img;
        }

        public Object getProvide_img() {
            return provide_img;
        }

        public void setProvide_img(Object provide_img) {
            this.provide_img = provide_img;
        }

        public Object getNeed_remark() {
            return need_remark;
        }

        public void setNeed_remark(Object need_remark) {
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

        public Object getNeed_describe() {
            return need_describe;
        }

        public void setNeed_describe(Object need_describe) {
            this.need_describe = need_describe;
        }

        public Object getProvide_describe() {
            return provide_describe;
        }

        public void setProvide_describe(Object provide_describe) {
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

        public void setProvince_name(Object province_name) {
            this.province_name = province_name;
        }
    }
}
