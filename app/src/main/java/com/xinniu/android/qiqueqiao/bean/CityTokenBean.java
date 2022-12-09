package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/5/15.
 */

public class CityTokenBean {


    /**
     * status : 200
     * message : 查询成功
     * result : {"token":"f517c4f45bbbe92028aa34b088b0e9e7"}
     */

    private String status;
    private String message;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * token : f517c4f45bbbe92028aa34b088b0e9e7
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
