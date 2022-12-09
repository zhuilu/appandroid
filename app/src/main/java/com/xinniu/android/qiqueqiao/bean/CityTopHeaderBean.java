package com.xinniu.android.qiqueqiao.bean;

/**
 * 介绍：美团最顶部Header
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * CSDN：http://blog.csdn.net/zxt0601
 * 时间： 16/11/28.
 */

public class CityTopHeaderBean {
    private String txt;

    public CityTopHeaderBean(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public CityTopHeaderBean setTxt(String txt) {
        this.txt = txt;
        return this;
    }

}
