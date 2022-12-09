package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2018/1/23.
 */

public class QrcodeBean {

    /**
     * type : resources
     * target_id : 6
     */

    private String type;
    private int target_id;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }
}
