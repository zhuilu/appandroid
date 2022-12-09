package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.VipPopUpBean;

/**
 * Created by yuchance on 2019/1/18.
 */

public interface VipPopupCallback {
    void onSuccess(VipPopUpBean bean);
    void onFailed(int code,String msg);
}
