package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VerifyInfo;

/**
 * Created by lzq on 2018/1/12.
 */

public interface GetVerifyInfoCallback {
    void onSuccess(VerifyInfo item);
    void onFailed(int code, String msg);
}
