package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ClassRoomListBean;


/**
 * Created by yuchance on 2018/5/10.
 */

public interface VideoListCallback {

    void onSuccess(ClassRoomListBean bean);
    void onFailue(int code, String msg);

}
