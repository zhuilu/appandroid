package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CommunicationRecordBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetCommunicationRecordListCallback {
    void onSuccess(CommunicationRecordBean.DataBean item);
    void onFailed(int code, String msg);
}
