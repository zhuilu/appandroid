package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ReleaseServiceSuccessBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface ReleaseServiceSuccessCallback {
    void onSuccess(ReleaseServiceSuccessBean item);
    void onFailed(int code, String msg);
}
