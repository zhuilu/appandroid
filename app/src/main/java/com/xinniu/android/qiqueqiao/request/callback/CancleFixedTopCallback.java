package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.FixedTopCancleBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface CancleFixedTopCallback {
    void onSuccess(FixedTopCancleBean item,String message);
    void onFailed(int code, String msg);
}
