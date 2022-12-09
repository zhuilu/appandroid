package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.OrderInfoBean;

/**
 * Created by lzq on 2017/12/18.
 */

public interface VipBugCallback {
    void onSuccess(OrderInfoBean bean);
    void onFailed(int code,String msg);
}
