package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.WalletDetailBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetWalletDetailCallback {
    void onSuccess(WalletDetailBean item);
    void onFailed(int code, String msg);
}
