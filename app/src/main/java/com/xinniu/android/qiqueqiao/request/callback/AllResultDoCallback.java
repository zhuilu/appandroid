package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;

/**
 * Created by yuchance on 2018/10/9.
 */

public interface AllResultDoCallback {
    void onSuccess(String msg);
    void onFailed(int code,String msg);
}
