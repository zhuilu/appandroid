package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CombingCardBean;

/**
 * Created by yuchance on 2019/2/18.
 */

public interface GetCombingCardCallback {
    void onSuccess(CombingCardBean list);
    void onFailed(int code, String msg);
}
