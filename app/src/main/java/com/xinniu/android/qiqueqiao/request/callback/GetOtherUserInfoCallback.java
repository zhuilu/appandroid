package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;

/**
 * Created by lzq on 2017/12/19.
 */

public interface GetOtherUserInfoCallback {
    void onSuccess(OtherUserInfo bean);
    void onFailed(int code,String msg);
}
