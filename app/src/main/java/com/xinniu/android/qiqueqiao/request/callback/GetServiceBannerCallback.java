package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ServiceBannerBean;

import java.util.List;

/**
 * Created by yuchance on 2019/2/14.
 */

public interface GetServiceBannerCallback {
    void onSuccess(List<ServiceBannerBean> list);
    void onFailed(int code, String msg);
}
