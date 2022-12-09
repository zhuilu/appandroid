package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.bean.ResouceInfoBean;

/**
 * Created by yuchance on 2018/3/31.
 */

public interface GetCoopDetailCallback {
    void onSuccess(CoopDetailBean bean);
    void onUndercarriage(String bean);
    void onFailed(int code,String msg);
}
