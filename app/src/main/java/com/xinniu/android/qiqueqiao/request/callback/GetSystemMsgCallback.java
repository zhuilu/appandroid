package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;

import java.util.List;

/**
 * Created by lzq on 2017/12/25.
 */

public interface GetSystemMsgCallback {
    void onSuccess(List<SystemMsgBean> list);
    void onFailed(int code, String msg);
}
