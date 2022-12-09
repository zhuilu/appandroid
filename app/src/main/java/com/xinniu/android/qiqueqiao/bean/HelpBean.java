package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by qinlei
 * Created on 2017/12/20
 * Created description :
 */

public class HelpBean {
    /**
     * title : 1.怎么快速解决自己的合作需求?
     * content : A.将自己的合作需求发布到企鹊桥平台,可以满足你需求的企业相关负责人会主动联系你
     * B.主动通过找企业、找合作板块的相关标签搜索,找到需求的合作渠道,主动联系对接合作
     */

    private String title;
    private String content;

    public HelpBean() {
    }

    public HelpBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
