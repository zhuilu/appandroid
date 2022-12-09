package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RewardTypeBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetRewardTypeCallback {
    void onSuccess(RewardTypeBean item);
    void onFailed(int code, String msg);
}
