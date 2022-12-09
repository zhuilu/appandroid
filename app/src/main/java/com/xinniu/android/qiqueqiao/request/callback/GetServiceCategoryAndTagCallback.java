package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;

import java.util.List;

/**
 * Created by yuchance on 2018/9/27.
 */

public interface GetServiceCategoryAndTagCallback {
    void onSuccess(List<ServiceCategoryAndTag> item);
    void onFailed(int code, String msg);
}
