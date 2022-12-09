package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.bean.GoToCollectBean;

/**
 * Created by yuchance on 2018/11/29.
 */

public interface GoFriendApplyCallback {
    void onSuccess(GoFriendApplyBean data,String msg);
    void onFailed(int code,String msg);
}
