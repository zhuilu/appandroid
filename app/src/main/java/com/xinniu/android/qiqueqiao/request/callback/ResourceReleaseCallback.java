package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ResourceReleaseBean;

/**
 * Created by lzq on 2017/12/20.
 */

public interface ResourceReleaseCallback {
    void onSuccess(ResourceReleaseBean bean);
    void onFailed(int code,String msg);
}
