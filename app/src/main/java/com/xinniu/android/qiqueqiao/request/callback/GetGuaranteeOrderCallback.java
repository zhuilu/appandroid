package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetGuaranteeOrderCallback {
    void onSuccess(GuaranteeOrderBean item);
    void onFailed(int code, String msg);
}
