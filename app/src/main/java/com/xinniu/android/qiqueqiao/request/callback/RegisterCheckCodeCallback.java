package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RegisterNewBean;

/**
 * Created by lzq on 2017/12/18.
 */

public interface RegisterCheckCodeCallback {
    void onSuccess(RegisterNewBean bean);

    void onFailed(int code, String msg);
}
