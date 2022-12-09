package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ActivityListBean;
import com.xinniu.android.qiqueqiao.bean.AddGroupBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetActivityListCallback {
    void onSuccess(ActivityListBean item);
    void onFailed(int code, String msg);
}
