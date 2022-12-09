package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RewardListBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface getRewardListCallback {
    void onSuccess(RewardListBean item);
    void onFailed(int code, String msg);
}
