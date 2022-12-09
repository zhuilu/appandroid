package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.CaseBean;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetCaseDetailCallback {
    void onSuccess(CaseBean item);

    void onFailed(int code, String msg);
}
