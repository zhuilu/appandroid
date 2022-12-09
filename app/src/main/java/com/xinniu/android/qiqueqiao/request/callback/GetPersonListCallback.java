package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AcceptedOrdersPersonBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetPersonListCallback {
    void onSuccess(AcceptedOrdersPersonBean item);
    void onFailed(int code, String msg);
}
