package com.xinniu.android.qiqueqiao.request.callback;

/**
 * Created by yuchance on 2018/6/22.
 */

public interface GetReleaseCheckCallback {
    void onSuccess();
    void onFailed(int code, String msg,String getConfigBean);


}
