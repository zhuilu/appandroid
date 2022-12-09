package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.GroupNoticeBean;

/**
 * Created by yuchance on 2018/10/15.
 */

public interface GroupNoticeCallback {
    void onSuccess(GroupNoticeBean bean);
    void onFailed(int code,String msg);
}
