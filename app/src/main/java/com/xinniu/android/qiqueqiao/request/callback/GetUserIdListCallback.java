package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.UserIdBean;

/**
 * Created by yuchance on 2019/2/14.
 */

public interface GetUserIdListCallback {
    void onSuccess(UserIdBean list);
    void onFailed(int code, String msg);
}
