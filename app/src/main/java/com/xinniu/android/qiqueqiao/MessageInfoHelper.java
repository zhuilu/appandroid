package com.xinniu.android.qiqueqiao;

import com.xinniu.android.qiqueqiao.bean.NewsBean;

/**
 * Created by lzq on 2017/12/25.
 * 信息帮助类
 */

public class MessageInfoHelper {
    private static MessageInfoHelper intance;

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    private NewsBean newsBean;

    public static MessageInfoHelper getIntance() {
        if (intance == null) {
            intance = new MessageInfoHelper();
        }
        return intance;
    }
}
