package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;

/**
 * Created by yuchance on 2018/11/16.
 */

public interface GroupHandleCallback {
    void onSuccess(SystemMsgBean data,String msg);
    void onFailed(int code,String msg);
}
