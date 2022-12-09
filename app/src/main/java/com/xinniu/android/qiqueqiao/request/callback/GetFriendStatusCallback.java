package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.FriendStatusBean;
import com.xinniu.android.qiqueqiao.bean.GetFriendListBean;

/**
 * Created by yuchance on 2018/12/4.
 */

public interface GetFriendStatusCallback {
    void onSuccess(FriendStatusBean bean);
    void onFailed(int code,String msg);
}
