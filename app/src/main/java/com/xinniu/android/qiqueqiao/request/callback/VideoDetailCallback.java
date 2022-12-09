package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ClassRoomDetailBean;

public interface VideoDetailCallback {
    void onSuccess(ClassRoomDetailBean item);
    void onFailue(int code,String msg);
}

