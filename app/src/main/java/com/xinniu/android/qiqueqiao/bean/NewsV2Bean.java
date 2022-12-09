package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/11/23.
 */

public class NewsV2Bean {

    /**
     * interactive : {"num":7}
     * system : {"id":1187,"content":"小师叔 申请加入群组互联网商务合作圈子\r\n理由：帅","create_time":1539342024,"status":0,"user_id":114,"num":3}
     */

    private InteractiveBean interactive;
    private SystemBean system;

    public InteractiveBean getInteractive() {
        return interactive;
    }

    public void setInteractive(InteractiveBean interactive) {
        this.interactive = interactive;
    }

    public SystemBean getSystem() {
        return system;
    }

    public void setSystem(SystemBean system) {
        this.system = system;
    }

    public static class InteractiveBean {
        /**
         * num : 7
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class SystemBean {
        /**
         * id : 1187
         * content : 小师叔 申请加入群组互联网商务合作圈子
         理由：帅
         * create_time : 1539342024
         * status : 0
         * user_id : 114
         * num : 3
         */

        private int id;
        private String content;
        private int create_time;
        private int status;
        private int user_id;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
