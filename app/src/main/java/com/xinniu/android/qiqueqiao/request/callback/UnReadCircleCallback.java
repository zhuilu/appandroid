package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.UnReadBean;

/**
 * Created by lzq on 2018/2/7.
 */

public interface UnReadCircleCallback {
    void onSuccess(UnReadBean item);
    void onFailue(int code,String msg);
}
