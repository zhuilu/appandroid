package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.GetUserResourcesBean;

/**
 * Created by lzq on 2017/12/19.
 */

public interface GetUserResourcesCallback {
    void onSuccess(GetUserResourcesBean bean);
    void onFailed(int code,String msg);
}
