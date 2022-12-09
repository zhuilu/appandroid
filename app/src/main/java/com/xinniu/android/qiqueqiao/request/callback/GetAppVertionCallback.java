package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AppVertion;

/**
 * Created by lzq on 2018/1/9.
 */

public interface GetAppVertionCallback {
    void onSuccess(AppVertion vertion);
    void onFailed(int code,String msg);
}
