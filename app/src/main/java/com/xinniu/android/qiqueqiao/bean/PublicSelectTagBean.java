package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class PublicSelectTagBean {
    private String name;
    private List<Integer> mData = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getmData() {
        return mData;
    }

    public void setmData(List<Integer> mData) {
        this.mData = mData;
    }
}
