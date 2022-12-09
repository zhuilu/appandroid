package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetCashWithdrawalCallback {
    void onSuccess(CashWithdrawalBean item);
    void onFailed(int code, String msg);
}
