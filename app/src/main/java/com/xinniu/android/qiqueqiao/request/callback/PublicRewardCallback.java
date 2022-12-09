package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.PublicRewardBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface PublicRewardCallback {
    void onSuccess(PublicRewardBean item);
    void onFailed(int code, String msg);
}
