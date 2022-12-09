package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ServiceCaseBean;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetCaseListCallback {
    void onSuccess(List<ServiceCaseBean> item);

    void onFailed(int code, String msg);
}
