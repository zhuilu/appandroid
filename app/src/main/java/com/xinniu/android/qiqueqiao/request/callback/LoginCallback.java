package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.UserInfoBean;

/**
 * Created by lzq on 2017/12/12.
 */

public interface LoginCallback {
    void onSuccess(UserInfoBean userInfoBean);
    void onFailed(int code,String msg);
}
