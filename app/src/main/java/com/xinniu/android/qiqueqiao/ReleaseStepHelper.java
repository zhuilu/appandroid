package com.xinniu.android.qiqueqiao;

import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2018/1/15.
 */

public class ReleaseStepHelper {
    private static ReleaseStepHelper instance;

    private String offerRemark = "";
    private String offerAttr = "";
    private int offerTopId;
    private String needRemark = "";
    private String needAttr = "";
    private int needTopId;
    private String modeContent = "";
    private int modeId;

    private String offerDes = "";
    private String needDes ="";

    private int needSelectedLeftItem = 0;
    private int offerSelectedLeftItem = 0;
    private Map<String,List<SelectCategory>> leftCache = new HashMap<>();
    private Map<String,List<SelectCategory>> offerRightCache = new HashMap<>();
    private Map<String,List<SelectCategory>> neddRightCache = new HashMap<>();

    private ArrayList<String> needList = new ArrayList<>();
    private ArrayList<String> offerList = new ArrayList<>();

    public ArrayList<String> getNeedList() {
        for (int i = 0 ;i<needList.size();i++){
            if (needList.get(i).equals(EditResouceAdapter.ADD)){
                needList.remove(i);
            }
        }
        return needList;
    }

    public void setNeedList(ArrayList<String> needList) {
        this.needList = needList;
    }

    public ArrayList<String> getOfferList() {
        for (int i = 0 ;i<offerList.size();i++){
            if (offerList.get(i).equals(EditResouceAdapter.ADD)){
                offerList.remove(i);
            }
        }
        return offerList;
    }

    public void setOfferList(ArrayList<String> offerList) {
        this.offerList = offerList;
    }

    public int getNeedSelectedLeftItem() {
        return needSelectedLeftItem;
    }

    public void setNeedSelectedLeftItem(int needSelectedLeftItem) {
        this.needSelectedLeftItem = needSelectedLeftItem;
    }

    public int getOfferSelectedLeftItem() {
        return offerSelectedLeftItem;
    }

    public void setOfferSelectedLeftItem(int offerSelectedLeftItem) {
        this.offerSelectedLeftItem = offerSelectedLeftItem;
    }

    public Map<String, List<SelectCategory>> getLeftCache() {
        return leftCache;
    }

    public void setLeftCache(Map<String, List<SelectCategory>> leftCache) {
        this.leftCache = leftCache;
    }

    public Map<String, List<SelectCategory>> getOfferRightCache() {
        return offerRightCache;
    }

    public void setOfferRightCache(Map<String, List<SelectCategory>> offerRightCache) {
        this.offerRightCache = offerRightCache;
    }

    public Map<String, List<SelectCategory>> getNeddRightCache() {
        return neddRightCache;
    }

    public void setNeddRightCache(Map<String, List<SelectCategory>> neddRightCache) {
        this.neddRightCache = neddRightCache;
    }

    public String getOfferDes() {
        return offerDes;
    }

    public void setOfferDes(String offerDes) {
        this.offerDes = offerDes;
    }

    public String getNeedDes() {
        return needDes;
    }

    public void setNeedDes(String needDes) {
        this.needDes = needDes;
    }

    //    private
    private  ReleaseStepHelper(){

    }
    public static ReleaseStepHelper getInstance(){
        if (instance == null){
            instance = new ReleaseStepHelper();
        }
        return instance;
    }


    public static void setInstance(ReleaseStepHelper instance) {
        ReleaseStepHelper.instance = instance;
    }

    public String getOfferRemark() {
        return offerRemark;
    }

    public void setOfferRemark(String offerRemark) {
        this.offerRemark = offerRemark;
    }

    public String getOfferAttr() {
        return offerAttr;
    }

    public void setOfferAttr(String offerAttr) {
        this.offerAttr = offerAttr;
    }

    public int getOfferTopId() {
        return offerTopId;
    }

    public void setOfferTopId(int offerTopId) {
        this.offerTopId = offerTopId;
    }

    public String getNeedRemark() {
        return needRemark;
    }

    public void setNeedRemark(String needRemark) {
        this.needRemark = needRemark;
    }

    public String getNeedAttr() {
        return needAttr;
    }

    public void setNeedAttr(String needAttr) {
        this.needAttr = needAttr;
    }

    public int getNeedTopId() {
        return needTopId;
    }

    public void setNeedTopId(int needTopId) {
        this.needTopId = needTopId;
    }

    public String getModeContent() {
        return modeContent;
    }

    public void setModeContent(String modeContent) {
        this.modeContent = modeContent;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }




    public void clearDate(){
        offerRemark = "";
        offerAttr = "";
        offerTopId = 0;
        needRemark = "";
        needAttr = "";
        needTopId = 0;
        modeContent = "";
        modeId = 0;
        offerDes = "";
        needDes ="";
        offerRightCache.clear();
        neddRightCache.clear();
        leftCache.clear();
        needList.clear();
        offerList.clear();
    }
}
