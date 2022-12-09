package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RewardOrderBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetRewardOrderCallback {
    void onSuccess(RewardOrderBean item);
    void onFailed(int code, String msg);
}
