package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.BillBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetMyBillListCallback {
    void onSuccess(BillBean item);
    void onFailed(int code, String msg);
}
