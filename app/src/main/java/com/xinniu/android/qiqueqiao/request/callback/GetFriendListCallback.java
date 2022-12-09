package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetFriendListBean;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;

/**
 * Created by yuchance on 2018/11/29.
 */

public interface GetFriendListCallback {
    void onSuccess(GetFriendListBean bean);
    void onFailed(int code,String msg);
}
