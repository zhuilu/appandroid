package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class CombingCardBean {

    /**
     * combingCardList : [{"id":28,"name":"专业梳理卡","apply_pay_id":"com.xinniu.qiqueqiao.combing.45cny","android_price":"0.01","ios_price":"45","desc":"专业梳理卡","price_desc":"45元/张"}]
     * combing_card_num : 0
     */

    private int combing_card_num;
    private List<CombingCardListBean> combingCardList;

    public int getCombing_card_num() {
        return combing_card_num;
    }

    public void setCombing_card_num(int combing_card_num) {
        this.combing_card_num = combing_card_num;
    }

    public List<CombingCardListBean> getCombingCardList() {
        return combingCardList;
    }

    public void setCombingCardList(List<CombingCardListBean> combingCardList) {
        this.combingCardList = combingCardList;
    }

    public static class CombingCardListBean {
        /**
         * id : 28
         * name : 专业梳理卡
         * apply_pay_id : com.xinniu.qiqueqiao.combing.45cny
         * android_price : 0.01
         * ios_price : 45
         * desc : 专业梳理卡
         * price_desc : 45元/张
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
}
