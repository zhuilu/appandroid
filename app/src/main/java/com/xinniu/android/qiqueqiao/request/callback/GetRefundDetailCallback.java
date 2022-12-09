package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RefundDetailBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetRefundDetailCallback {
    void onSuccess(RefundDetailBean item);
    void onFailed(int code, String msg);
}
