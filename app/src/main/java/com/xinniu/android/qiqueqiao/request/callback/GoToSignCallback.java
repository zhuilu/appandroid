package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GoToCollectBean;

/**
 * Created by yuchance on 2018/9/12.
 */

public interface GoToSignCallback {
    void onSuccess(String msg);
    void onFailed(int code,String msg);
}
