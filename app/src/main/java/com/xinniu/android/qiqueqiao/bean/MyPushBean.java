package com.xinniu.android.qiqueqiao.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/19
 * Created description :
 */

public class MyPushBean {

    private String company;
    private int hasMore;
    private int card_num;
    private int refresh_num;
    private int fixed_top_card_num;

    public int getFixed_top_card_num() {
        return fixed_top_card_num;
    }

    public void setFixed_top_card_num(int fixed_top_card_num) {
        this.fixed_top_card_num = fixed_top_card_num;
    }

    public int getCard_num() {
        return card_num;
    }

    public void setCard_num(int card_num) {
        this.card_num = card_num;
    }

    public int getRefresh_num() {
        return refresh_num;
    }

    public void setRefresh_num(int refresh_num) {
        this.refresh_num = refresh_num;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }
    public int getHasMore() {
        return hasMore;
    }

    public void setHasMore(int hasMore) {
        this.hasMore = hasMore;
    }

    public static class ListBean implements MultiItemEntity{
        /**
         * id : 43
         * need_remark : 需求备注
         * provide_remark : 提供备注
         * cooperation_mode_cn : 联合营销,流量互换
         * view : 0
         * talk : 0
         * is_hot : 0
         * reservation_status
         */

        private int id;
        private String need_remark;
        private String provide_remark;
        private String cooperation_mode_cn;
        private int view;
        private int talk;
        private int is_hot;
        private String title;
        private int is_verify;
        private int sort_verify;
        private int p_id;
        private String p_name;
        private String p_img;
        private int comment_count;
        private int reservation_status;
        private List<Long> reservation_time;

        public int getSort_verify() {
            return sort_verify;
        }

        public void setSort_verify(int sort_verify) {
            this.sort_verify = sort_verify;
        }

        public List<Long> getReservation_time() {
            return reservation_time;
        }

        public void setReservation_time(List<Long> reservation_time) {
            this.reservation_time = reservation_time;
        }

        public int getReservation_status() {
            return reservation_status;
        }

        public void setReservation_status(int reservation_status) {
            this.reservation_status = reservation_status;
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

        public String getP_img() {
            return p_img;
        }

        public void setP_img(String p_img) {
            this.p_img = p_img;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        private String share_url;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private int status;

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

        public static final int COMMON = 1;
        public static final int THETOP = 2;
        private int itemType;

        public ListBean(int itemType) {
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
}
