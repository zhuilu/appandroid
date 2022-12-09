package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;

import java.util.List;

/**
 * Created by yuchance on 2018/11/29.
 */

public interface GetGroupFriendListCallback {
    void onSuccess(List<GroupFriendBean> bean);
    void onFailed(int code, String msg);
}
