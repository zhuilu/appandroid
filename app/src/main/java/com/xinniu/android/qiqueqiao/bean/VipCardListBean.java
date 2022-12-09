package com.xinniu.android.qiqueqiao.bean;

/**
 * Created by yuchance on 2019/2/25.
 */

public class VipCardListBean {

    private int mealId;
    private String timeDown;
    private String priceDesc;
    private String originalPriceDesc;
    private int isVip;
    private int vipCardId;
    private String androidPrice;
    private String nameInfo;
    private int bgBtnsrc;
    private int textBtn;

    public int getTextBtn() {
        return textBtn;
    }

    public void setTextBtn(int textBtn) {
        this.textBtn = textBtn;
    }

    public int getBgBtnsrc() {
        return bgBtnsrc;
    }

    public void setBgBtnsrc(int bgBtnsrc) {
        this.bgBtnsrc = bgBtnsrc;
    }

    public String getAndroidPrice() {
        return androidPrice;
    }

    public void setAndroidPrice(String androidPrice) {
        this.androidPrice = androidPrice;
    }

    public String getNameInfo() {
        return nameInfo;
    }

    public void setNameInfo(String nameInfo) {
        this.nameInfo = nameInfo;
    }

    public VipCardListBean(int mealId, String timeDown, String priceDesc, String originalPriceDesc, int isVip, int vipCardId,String androidPrice,String nameInfo,int bgBtnsrc,int textBtn) {
        this.mealId = mealId;
        this.timeDown = timeDown;
        this.priceDesc = priceDesc;
        this.originalPriceDesc = originalPriceDesc;
        this.isVip = isVip;
        this.vipCardId = vipCardId;
        this.androidPrice = androidPrice;
        this.nameInfo = nameInfo;
        this.bgBtnsrc = bgBtnsrc;
        this.textBtn = textBtn;
    }

    public int getVipCardId() {
        return vipCardId;
    }

    public void setVipCardId(int vipCardId) {
        this.vipCardId = vipCardId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getTimeDown() {
        return timeDown;
    }

    public void setTimeDown(String timeDown) {
        this.timeDown = timeDown;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public String getOriginalPriceDesc() {
        return originalPriceDesc;
    }

    public void setOriginalPriceDesc(String originalPriceDesc) {
        this.originalPriceDesc = originalPriceDesc;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }
}
