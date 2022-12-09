package com.xinniu.android.qiqueqiao;

import android.text.TextUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzq on 2018/1/8.
 */

public class EditResouceInfoHelper {
    private static EditResouceInfoHelper mInstance;
    private final static String PROVIDER_DES = "provide_";
    private final static String NEED_DES = "need_";



    private Map<String,String> mEditResouceMap;
    private EditResouceInfoHelper(){
        mEditResouceMap = new HashMap<>();
    }
    public static EditResouceInfoHelper getInstance(){
        if (mInstance == null){
            mInstance = new EditResouceInfoHelper();
        }
        return mInstance;
    }

    public void setProvideItem(int id,String des){
        mEditResouceMap.put(PROVIDER_DES + id,des);
    }

    public String getProvideByKey(int id){
        if (!TextUtils.isEmpty(mEditResouceMap.get(PROVIDER_DES + id))){
            return mEditResouceMap.get(PROVIDER_DES + id);
        }else{
            return "";
        }
    }

    public void setNeedItem(int id,String des){
        mEditResouceMap.put(NEED_DES + id,des);
    }

    public String getNeedByKey(int id){
        if (!TextUtils.isEmpty(mEditResouceMap.get(NEED_DES + id))){
            return mEditResouceMap.get(NEED_DES + id);
        }else{
            return "";
        }
    }

    public void clearItem(int id){
        if (mEditResouceMap.containsKey(NEED_DES + id)){
            mEditResouceMap.remove(NEED_DES + id);
        }
        if (mEditResouceMap.containsKey(PROVIDER_DES + id)){
            mEditResouceMap.remove(PROVIDER_DES + id);
        }
    }

}
