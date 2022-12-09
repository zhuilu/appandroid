package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RewardDetailBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetRewardDetailCallback {
    void onSuccess(RewardDetailBean item);
    void onFailed(int code, String msg);
}
