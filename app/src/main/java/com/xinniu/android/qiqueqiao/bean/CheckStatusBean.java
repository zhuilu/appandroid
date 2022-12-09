package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2018/2/5.
 */

public class CheckStatusBean {


    /**
     * code : 208
     * msg : 该圈子需要名片认证,才能申请
     * data : {"checkStatus":1}
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
         * checkStatus : 1
         */

        private int checkStatus;

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }
    }
}
