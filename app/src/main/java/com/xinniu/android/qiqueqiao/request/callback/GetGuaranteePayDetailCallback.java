package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.PayDetailBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetGuaranteePayDetailCallback {
    void onSuccess(PayDetailBean item);

    void onFailed(int code, String msg);
}
