package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;

import java.util.List;

/**
 * Created by yuchance on 2018/10/10.
 */

public interface GetGroupInfoCallback {
    void onSuccess(GroupInfoBean bean);
    void onFailed(int code,String msg);
}
