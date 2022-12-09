package com.xinniu.android.qiqueqiao.bean;

public class RefundDetailBean {


    /**
     * id : 4
     * order_sn : H2019070404341897972571
     * status : 1
     * now_time : 1562750428
     * refund_obj : {"user_id":1376,"head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","guarantee_amount":"601.00","billing_amount":"0.00","refund_amount":"601.00","refund_status":2,"desc":"","images":"","thumb_img":"","opeartion_time":1562678175}
     * refuse_obj : {"user_id":1276,"head_pic":"https://qqq2017.oss-cn-hangzhou.aliyuncs.com/4827971.jpg","desc":"拒绝你","images":"","thumb_img":"","opeartion_time":1562744902}
     * serving_obj : {"user_id":"","head_pic":"","desc":"","images":"","thumb_img":"","opeartion_time":""}
     */

    private int id;
    private String order_sn;
    private int status;
    private long now_time;
    private RefundObjBean refund_obj;
    private RefuseObjBean refuse_obj;
    private ServingObjBean serving_obj=null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getNow_time() {
        return now_time;
    }

    public void setNow_time(long now_time) {
        this.now_time = now_time;
    }

    public RefundObjBean getRefund_obj() {
        return refund_obj;
    }

    public void setRefund_obj(RefundObjBean refund_obj) {
        this.refund_obj = refund_obj;
    }

    public RefuseObjBean getRefuse_obj() {
        return refuse_obj;
    }

    public void setRefuse_obj(RefuseObjBean refuse_obj) {
        this.refuse_obj = refuse_obj;
    }

    public ServingObjBean getServing_obj() {
        return serving_obj;
    }

    public void setServing_obj(ServingObjBean serving_obj) {
        this.serving_obj = serving_obj;
    }

    public static class RefundObjBean {
        /**
         * user_id : 1376
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * guarantee_amount : 601.00
         * billing_amount : 0.00
         * refund_amount : 601.00
         * refund_status : 2
         * desc :
         * images :
         * thumb_img :
         * opeartion_time : 1562678175
         */

        private int user_id;
        private String head_pic;
        private String guarantee_amount;
        private String billing_amount;
        private String refund_amount;
        private int refund_status;
        private String desc;
        private String images;
        private String thumb_img;
        private long opeartion_time;

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

        public String getGuarantee_amount() {
            return guarantee_amount;
        }

        public void setGuarantee_amount(String guarantee_amount) {
            this.guarantee_amount = guarantee_amount;
        }

        public String getBilling_amount() {
            return billing_amount;
        }

        public void setBilling_amount(String billing_amount) {
            this.billing_amount = billing_amount;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public long getOpeartion_time() {
            return opeartion_time;
        }

        public void setOpeartion_time(long opeartion_time) {
            this.opeartion_time = opeartion_time;
        }
    }

    public static class RefuseObjBean {
        /**
         * user_id : 1276
         * head_pic : https://qqq2017.oss-cn-hangzhou.aliyuncs.com/4827971.jpg
         * desc : 拒绝你
         * images :
         * thumb_img :
         * opeartion_time : 1562744902
         */

        private int user_id;
        private String head_pic;
        private String desc;
        private String images;
        private String thumb_img;
        private long opeartion_time;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public long getOpeartion_time() {
            return opeartion_time;
        }

        public void setOpeartion_time(long opeartion_time) {
            this.opeartion_time = opeartion_time;
        }
    }

    public static class ServingObjBean {
        /**
         * user_id :
         * head_pic :
         * desc :
         * images :
         * thumb_img :
         * opeartion_time :
         */

        private int user_id;
        private String head_pic;
        private String desc;
        private String images;
        private String thumb_img;
        private long opeartion_time;
        private String result_desc;

        public String getResult_desc() {
            return result_desc;
        }

        public void setResult_desc(String result_desc) {
            this.result_desc = result_desc;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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

        public long getOpeartion_time() {
            return opeartion_time;
        }

        public void setOpeartion_time(long opeartion_time) {
            this.opeartion_time = opeartion_time;
        }
    }
}
