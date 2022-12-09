package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

/**
 * Created by yuchance on 2018/5/15.
 */

public class CompanyNameBean {


    /**
     * status : 200
     * message : 查询成功
     * result : [{"companyCode":"708461136","companyKey":"48cb7705bcd8c9d7429f792f60ebc998","companyName":"深圳市腾讯计算机系统有限公司"},{"companyCode":"765459905","companyKey":"54796e6c91d3ab222ea8ff8be0c400b7","companyName":"深圳市腾讯计算机系统有限公司杭州分公司"}]
     */

    private String status;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * companyCode : 708461136
         * companyKey : 48cb7705bcd8c9d7429f792f60ebc998
         * companyName : 深圳市腾讯计算机系统有限公司
         */

        private String companyCode;
        private String companyKey;
        private String companyName;

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyKey() {
            return companyKey;
        }

        public void setCompanyKey(String companyKey) {
            this.companyKey = companyKey;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
