package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.ResourcesTitleBean;

/**
 * Created by lzq on 2018/1/11.
 */

public interface PreSearchCallback {
    void onSuccess(ResourcesTitleBean list);
    void onFailed(int code, String msg);
}
