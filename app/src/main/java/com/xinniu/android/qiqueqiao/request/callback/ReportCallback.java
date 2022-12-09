package com.xinniu.android.qiqueqiao.request.callback;

/**
 * Created by yuchance on 2018/5/31.
 */

public interface ReportCallback {
    void onSuccess(String msg);
    void onFailed(int code,String msg);
}
