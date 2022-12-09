package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.InteractiveNewsBean;

/**
 * Created by yuchance on 2018/10/31.
 */

public interface GetInteractNewsCallback {
    void onSuccess(InteractiveNewsBean bean);
    void onFailed(int code,String msg);
}
