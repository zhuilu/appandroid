package com.xinniu.android.qiqueqiao.bean;

public class MyWalletBean {


    /**
     * total_amount : 561.00
     * recording : {"withdrawals_amount":"20.00","handling_fee":"0.20","actual_amount_achieved":"19.80","account_type":1,"alipay_account":"18605811452@163.com","alipay_name":"曾杨","bank_account":"","bank_account_name":"","bank":""}
     * rate : 0.5
     */

    private String total_amount;
    private RecordingBean recording=null;
    private String rate;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public RecordingBean getRecording() {
        return recording;
    }

    public void setRecording(RecordingBean recording) {
        this.recording = recording;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public static class RecordingBean {
        /**
         * withdrawals_amount : 20.00
         * handling_fee : 0.20
         * actual_amount_achieved : 19.80
         * account_type : 1
         * alipay_account : 18605811452@163.com
         * alipay_name : 曾杨
         * bank_account :
         * bank_account_name :
         * bank :
         */

        private String withdrawals_amount;
        private String handling_fee;
        private String actual_amount_achieved;
        private int account_type;
        private String alipay_account;
        private String alipay_name;
        private String bank_account;
        private String bank_account_name;
        private String bank;

        public String getWithdrawals_amount() {
            return withdrawals_amount;
        }

        public void setWithdrawals_amount(String withdrawals_amount) {
            this.withdrawals_amount = withdrawals_amount;
        }

        public String getHandling_fee() {
            return handling_fee;
        }

        public void setHandling_fee(String handling_fee) {
            this.handling_fee = handling_fee;
        }

        public String getActual_amount_achieved() {
            return actual_amount_achieved;
        }

        public void setActual_amount_achieved(String actual_amount_achieved) {
            this.actual_amount_achieved = actual_amount_achieved;
        }

        public int getAccount_type() {
            return account_type;
        }

        public void setAccount_type(int account_type) {
            this.account_type = account_type;
        }

        public String getAlipay_account() {
            return alipay_account;
        }

        public void setAlipay_account(String alipay_account) {
            this.alipay_account = alipay_account;
        }

        public String getAlipay_name() {
            return alipay_name;
        }

        public void setAlipay_name(String alipay_name) {
            this.alipay_name = alipay_name;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getBank_account_name() {
            return bank_account_name;
        }

        public void setBank_account_name(String bank_account_name) {
            this.bank_account_name = bank_account_name;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }
    }
}
