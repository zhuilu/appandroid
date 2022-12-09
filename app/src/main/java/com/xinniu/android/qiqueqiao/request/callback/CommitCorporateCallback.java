package com.xinniu.android.qiqueqiao.request.callback;

/**
 * Created by yuchance on 2018/5/16.
 */

public interface CommitCorporateCallback {
    void onSuccess();
    void onFailed(int code,String msg);
}
