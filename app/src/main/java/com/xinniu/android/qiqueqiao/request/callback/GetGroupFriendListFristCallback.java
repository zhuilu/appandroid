package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;

import java.util.List;

/**
 * Created by yuchance on 2018/11/29.
 */

public interface GetGroupFriendListFristCallback {
    void onSuccess(List<GroupFriendBean.UserListBean> bean);
    void onFailed(int code, String msg);
}
