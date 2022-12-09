package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by lzq on 2017/12/20.
 */

public class ResourceReleaseBean {

    /**
     * share_url : q.qiqueqiao.com/share/resources/70
     */

    private String share_url;
    private String is_verify;
    private int resources_id;
    private String wechat_url;
    private String qrcode_url;
    private int p_id;
    private String p_name;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getQrcode_url() {
        return qrcode_url;
    }

    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url;
    }

    public String getWechat_url() {
        return wechat_url;
    }

    public void setWechat_url(String wechat_url) {
        this.wechat_url = wechat_url;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }

    public int getResources_id() {
        return resources_id;
    }

    public void setResources_id(int resources_id) {
        this.resources_id = resources_id;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }
}
