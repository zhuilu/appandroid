package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GuaranteeServiceInfoBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetGuaranteeServiceinfoCallback {
    void onSuccess(GuaranteeServiceInfoBean item);
    void onFailed(int code, String msg);
}
