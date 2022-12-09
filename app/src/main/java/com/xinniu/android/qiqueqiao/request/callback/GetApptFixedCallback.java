package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.TimeBean;

/**
 * Created by yuchance on 2019/2/14.
 */

public interface GetApptFixedCallback {
    void onSuccess(TimeBean list);
    void onFailed(int code, String msg);
}
