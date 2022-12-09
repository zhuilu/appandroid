package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ServiceDetailBean;

/**
 * Created by yuchance on 2018/3/31.
 */

public interface GetServiceDetailCallback {
    void onSuccess(ServiceDetailBean bean);
    void onUndercarriage(String bean);
    void onFailed(int code, String msg);
}
