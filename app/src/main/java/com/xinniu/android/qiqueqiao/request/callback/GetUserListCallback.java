package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GroupMemberManageBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by yuchance on 2018/10/9.
 */

public interface GetUserListCallback {
    void onSuccess(List<GroupMemberManageBean> list);
    void onFailed(int code,String msg);
}
