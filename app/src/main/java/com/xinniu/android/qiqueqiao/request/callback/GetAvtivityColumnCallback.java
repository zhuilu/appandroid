package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ActivityColumnListBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetAvtivityColumnCallback {
    void onSuccess(ActivityColumnListBean item);

    void onFailed(int code, String msg);
}
