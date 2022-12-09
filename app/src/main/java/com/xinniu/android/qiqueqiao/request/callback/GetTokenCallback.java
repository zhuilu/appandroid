package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.TokenBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetTokenCallback {
    void onSuccess(TokenBean item);
    void onFailed(int code, String msg);
}
