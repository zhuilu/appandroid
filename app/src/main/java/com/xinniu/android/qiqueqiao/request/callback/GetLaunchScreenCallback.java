package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.SplashBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetLaunchScreenCallback {
    void onSuccess(SplashBean item);
    void onFailed(int code, String msg);
}
