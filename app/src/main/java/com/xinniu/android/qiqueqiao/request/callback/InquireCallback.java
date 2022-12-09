package com.xinniu.android.qiqueqiao.request.callback;

import com.xinniu.android.qiqueqiao.bean.InquireBean;

/**
 * Created by yuchance on 2018/10/26.
 */

public interface InquireCallback {
    void onSuccess(InquireBean userInfoBean);
    void onFailed(int code,String msg);
}
