package com.xinniu.android.qiqueqiao.request.callback;

/**
 * Created by lzq on 2017/12/22.
 */

public interface PayCallback{
    void onSuccess(String order);
    void onFailed(int code,String msg);
}
