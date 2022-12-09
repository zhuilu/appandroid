package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.bean.GroupBean;

/**
 * Created by yuchance on 2018/6/12.
 */

public interface GroupInfoCallback {
    void onSuccess(GroupBean bean);
    void onFailed(int code,String msg);
}
