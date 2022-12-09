package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2018/12/13.
 */

public class GetReleaseTypeBean {

    /**
     * id : 3
     * name : 销售渠道
     * img : http://timg.qiqueqiao.com/category/2018/09-13/5b9a09784f20f.png
     * need_description : 1.月销量：
     2.保证金：
     3.一键代发：
     4.结算周期：
     5.合作模式：
     6.补充：
     * provide_description : 你好啊你好你好考试都能看见谁能看到你说可能的可是你都快三年的开始呢可是你都快九十年代可能是可是你111222333都快三年的开始呢可是你都看见你的开始呢可是你都快能看到你可是你都快睡觉呢可是你的开始呢可是你都快睡觉呢
     * introduction : 你好啊你好你好考试都能看见谁能看到你说可能的可是你都快三年的开始呢可是你都快九十年代可能是可是你
     */

    private int id;
    private String name;
    private String img;
    private String need_description;
    private String provide_description;
    private String introduction;
    private String category_name;

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNeed_description() {
        return need_description;
    }

    public void setNeed_description(String need_description) {
        this.need_description = need_description;
    }

    public String getProvide_description() {
        return provide_description;
    }

    public void setProvide_description(String provide_description) {
        this.provide_description = provide_description;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
