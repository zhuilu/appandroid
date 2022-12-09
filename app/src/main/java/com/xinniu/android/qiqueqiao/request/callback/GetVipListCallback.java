package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VipListBean;

/**
 * Created by lzq on 2017/12/15.
 */

public interface GetVipListCallback {
    void onSuccess(VipListBean bean);
    void onFailed(int code,String msg);
}
