package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.UserFollowBean;

/**
 * Created by lzq on 2017/12/18.
 */

public interface UserFolloweCallback {
    void onSuccess(UserFollowBean bean);
    void onFailed(int code, String msg);
}
