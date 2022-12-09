package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MyWalletBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetMyWalletCallback {
    void onSuccess(MyWalletBean item);
    void onFailed(int code, String msg);
}
