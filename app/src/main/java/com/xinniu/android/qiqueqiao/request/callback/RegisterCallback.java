package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.RegisterBean;

/**
 * Created by lzq on 2017/12/18.
 */

public interface RegisterCallback {
    void onSuccess(RegisterBean bean);
    void onFailed(int code,String msg);
}
