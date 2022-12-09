package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GuaranteeDetailBean;

/**
 * Created by BDXK on 2017/12/12.
 * project : xiqueqiao--- android xs
 */

public interface GetGuaranteeDetailtCallback {
    void onSuccess(GuaranteeDetailBean guaranteeDetailBean);
    void onFailed(int code, String msg);
}
