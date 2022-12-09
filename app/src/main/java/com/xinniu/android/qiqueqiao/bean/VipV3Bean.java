package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/4/18.
 */

public class VipV3Bean {


    /**
     * "experience": [{"id": 27,"name": "VIP体验会员","apply_pay_id": "com.xinniu.qiqueqiao.headcard.198cny","android_price": "0.01","ios_price": "6","desc": "VIP体验会员","price_desc": "¥6/天","status": 1,"num": 1,"name_info": "VIP体验会员1天"}],
     * interim : []
     *   "business_package": {
     *            "id": 0,
     *             "name": "企业会员套餐",
     *             "desc": "10个SVIP子账号",
     *             "price_desc": "¥4980起"
     *         }
     * users : {"user_id":1376,"head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","realname":"曾杨","is_vip":2,"package_id":19,"is_v":1,"end_time":1822534959,"is_manager_invited":0,"reg_time":1537409487,"call_num":0,"talk_num":8108,"text":"2027-10-03","time_down":""}
     * vipList : [{"id":9,"name":"1个月","apply_pay_id":"com.xinniu.qiqueqiao.vip.198cny","android_price":"0.02","ios_price":"198","desc":"100次沟通次数","price_desc":"¥198/月","status":0,"name_info":"VIP会员1个月","price_unit":"198","price_suffix":"月"}]
     * svipList : [{"id":21,"name":"12个月","apply_pay_id":"com.xinniu.qiqueqiao.svip.1998cny","android_price":"1998","ios_price":"1998","desc":"4000次沟通次数+4000分钟通话时长","price_desc":"¥1998/年","name_info":"SVIP会员12个月"}]
     * talk_package : {"id":22,"name":"加油包","apply_pay_id":"com.xinniu.qiqueqiao.oilbag.98cny","android_price":"98","ios_price":"98","desc":"50次沟通次数","price_desc":"¥98/50次"}
     */

    private UsersBean users;
    private TalkPackageBean talk_package;
    private List<InterimBean> interim;
    private List<VipListBean> vipList;
    private List<SvipListBean> svipList;
    private List<Experience> experience;
    private BusinessPackage business_package=null;


    public BusinessPackage getBusiness_package() {
        return business_package;
    }

    public void setBusiness_package(BusinessPackage business_package) {
        this.business_package = business_package;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public UsersBean getUsers() {
        return users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public TalkPackageBean getTalk_package() {
        return talk_package;
    }

    public void setTalk_package(TalkPackageBean talk_package) {
        this.talk_package = talk_package;
    }

    public List<InterimBean> getInterim() {
        return interim;
    }

    public void setInterim(List<InterimBean> interim) {
        this.interim = interim;
    }

    public List<VipListBean> getVipList() {
        return vipList;
    }

    public void setVipList(List<VipListBean> vipList) {
        this.vipList = vipList;
    }

    public List<SvipListBean> getSvipList() {
        return svipList;
    }

    public void setSvipList(List<SvipListBean> svipList) {
        this.svipList = svipList;
    }



    public static class UsersBean {
        /**
         * user_id : 1376
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * realname : 曾杨
         * is_vip : 2
         * package_id : 19
         * is_v : 1
         * end_time : 1822534959
         * is_manager_invited : 0
         * reg_time : 1537409487
         * call_num : 0
         * talk_num : 8108
         * text : 2027-10-03
         * time_down :
         */

        private int user_id;
        private String head_pic;
        private String realname;
        private int is_vip;
        private int package_id;
        private int is_v;
        private long end_time;
        private int is_manager_invited;
        private long reg_time;
        private int call_num;
        private int talk_num;
        private String text;
        private String time_down;
        private int is_corporate_vip = 0;

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
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

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public int getIs_v() {
            return is_v;
        }

        public void setIs_v(int is_v) {
            this.is_v = is_v;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public int getIs_manager_invited() {
            return is_manager_invited;
        }

        public void setIs_manager_invited(int is_manager_invited) {
            this.is_manager_invited = is_manager_invited;
        }

        public long getReg_time() {
            return reg_time;
        }

        public void setReg_time(long reg_time) {
            this.reg_time = reg_time;
        }

        public int getCall_num() {
            return call_num;
        }

        public void setCall_num(int call_num) {
            this.call_num = call_num;
        }

        public int getTalk_num() {
            return talk_num;
        }

        public void setTalk_num(int talk_num) {
            this.talk_num = talk_num;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTime_down() {
            return time_down;
        }

        public void setTime_down(String time_down) {
            this.time_down = time_down;
        }
    }

    public static class TalkPackageBean {
        /**
         * id : 22
         * name : 加油包
         * apply_pay_id : com.xinniu.qiqueqiao.oilbag.98cny
         * android_price : 98
         * ios_price : 98
         * desc : 50次沟通次数
         * price_desc : ¥98/50次
         */

        private int id;
        private String name;
        private String apply_pay_id;
        private String android_price;
        private String ios_price;
        private String desc;
        private String price_desc;

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

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public String getAndroid_price() {
            return android_price;
        }

        public void setAndroid_price(String android_price) {
            this.android_price = android_price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }
    }

    public static class VipListBean {
        /**
         * id : 9
         * name : 1个月
         * apply_pay_id : com.xinniu.qiqueqiao.vip.198cny
         * android_price : 0.02
         * ios_price : 198
         * desc : 100次沟通次数
         * price_desc : ¥198/月
         * status : 0
         * name_info : VIP会员1个月
         * price_unit : 198
         * price_suffix : 月
         */

        private int id;
        private String name;
        private String apply_pay_id;
        private String android_price;
        private String ios_price;
        private String desc;
        private String price_desc;
        private int status;
        private String name_info;
        private String price_unit;
        private String price_suffix;
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public String getAndroid_price() {
            return android_price;
        }

        public void setAndroid_price(String android_price) {
            this.android_price = android_price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName_info() {
            return name_info;
        }

        public void setName_info(String name_info) {
            this.name_info = name_info;
        }

        public String getPrice_unit() {
            return price_unit;
        }

        public void setPrice_unit(String price_unit) {
            this.price_unit = price_unit;
        }

        public String getPrice_suffix() {
            return price_suffix;
        }

        public void setPrice_suffix(String price_suffix) {
            this.price_suffix = price_suffix;
        }
    }

    public static class SvipListBean {
        /**
         * id : 21
         * name : 12个月
         * apply_pay_id : com.xinniu.qiqueqiao.svip.1998cny
         * android_price : 1998
         * ios_price : 1998
         * desc : 4000次沟通次数+4000分钟通话时长
         * price_desc : ¥1998/年
         * name_info : SVIP会员12个月
         */

        private int id;
        private String name;
        private String apply_pay_id;
        private String android_price;
        private String ios_price;
        private String desc;
        private String price_desc;
        private String name_info;
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public String getAndroid_price() {
            return android_price;
        }

        public void setAndroid_price(String android_price) {
            this.android_price = android_price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }

        public String getName_info() {
            return name_info;
        }

        public void setName_info(String name_info) {
            this.name_info = name_info;
        }
    }

    public static class InterimBean {
        /**
         * id : 8
         * name : 1个月
         * apply_pay_id : com.xinniu.qiqueqiao.vip.98cny
         * android_price : 0.01
         * ios_price : 98
         * desc : 300次沟通次数
         * price_desc : null
         * status : 1
         */

        private int id;
        private String name;
        private String apply_pay_id;
        private String android_price;
        private String ios_price;
        private String desc;
        private String price_desc;
        private int status;
        private String name_info;
        private String original_price_desc;
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getOriginal_price_desc() {
            return original_price_desc;
        }

        public void setOriginal_price_desc(String original_price_desc) {
            this.original_price_desc = original_price_desc;
        }

        public void setName_info(String name_info) {
            this.name_info = name_info;
        }

        public String getName_info() {
            return name_info;
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

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public String getAndroid_price() {
            return android_price;
        }

        public void setAndroid_price(String android_price) {
            this.android_price = android_price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class BusinessPackage{

        /**
         * id : 0
         * name : 企业会员套餐
         * desc : 10个SVIP子账号
         * price_desc : ¥4980起
         */

        private int id;
        private String name;
        private String desc;
        private String price_desc;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }
    }

    public static class Experience {


        /**
         * id : 27
         * name : VIP体验会员
         * apply_pay_id : com.xinniu.qiqueqiao.headcard.198cny
         * android_price : 0.01
         * ios_price : 6
         * desc : VIP体验会员
         * price_desc : ¥6/天
         * status : 1
         * num : 1
         * name_info : VIP体验会员1天
         */

        private int id;
        private String name;
        private String apply_pay_id;
        private String android_price;
        private String ios_price;
        private String desc;
        private String price_desc;
        private int status;
        private int num;
        private String name_info;

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

        public String getApply_pay_id() {
            return apply_pay_id;
        }

        public void setApply_pay_id(String apply_pay_id) {
            this.apply_pay_id = apply_pay_id;
        }

        public String getAndroid_price() {
            return android_price;
        }

        public void setAndroid_price(String android_price) {
            this.android_price = android_price;
        }

        public String getIos_price() {
            return ios_price;
        }

        public void setIos_price(String ios_price) {
            this.ios_price = ios_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice_desc() {
            return price_desc;
        }

        public void setPrice_desc(String price_desc) {
            this.price_desc = price_desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName_info() {
            return name_info;
        }

        public void setName_info(String name_info) {
            this.name_info = name_info;
        }
    }

}
