package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.MainBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetMainCallback {
    void onSuccess(MainBean item);

    void onFailed(int code, String msg);
}
