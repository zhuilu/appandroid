package com.xinniu.android.qiqueqiao.bean;

public class ReleaseCheckBean {


    /**
     * code : 311
     * msg : 超过可发布条数
     * data : {"prompt_msg":"您在此分类下发布的合作信息不可超过3条","solve_prompt":"您可删除已发布的合作信息后继续发布"}
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
         * prompt_msg : 您在此分类下发布的合作信息不可超过3条
         * solve_prompt : 您可删除已发布的合作信息后继续发布
         */

        private String prompt_msg;
        private String solve_prompt;

        public String getPrompt_msg() {
            return prompt_msg;
        }

        public void setPrompt_msg(String prompt_msg) {
            this.prompt_msg = prompt_msg;
        }

        public String getSolve_prompt() {
            return solve_prompt;
        }

        public void setSolve_prompt(String solve_prompt) {
            this.solve_prompt = solve_prompt;
        }
    }
}
